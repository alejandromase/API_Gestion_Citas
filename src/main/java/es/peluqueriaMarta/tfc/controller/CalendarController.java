package es.peluqueriaMarta.tfc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.peluqueriaMarta.tfc.model.Appointment;
import es.peluqueriaMarta.tfc.model.CalendarDay;
import es.peluqueriaMarta.tfc.model.Service;
import es.peluqueriaMarta.tfc.repository.AppointmentRepository;
import es.peluqueriaMarta.tfc.repository.CalendarDayRepository;
import es.peluqueriaMarta.tfc.repository.ServiceRepository;

/**
 * Controlador para la gestión del calendario.
 * Permite obtener detalles de un día específico, obtener días bloqueados,
 * guardar días bloqueados y desbloquear días.
 */
@RestController
@RequestMapping("/API")
public class CalendarController {

    @Autowired
    private CalendarDayRepository calendarDayRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    /**
     * Obtiene los detalles de un día específico del calendario.
     * @param date La fecha del día a obtener (formato: DD-MM-YYYY).
     * @return ResponseEntity con un mapa que contiene los detalles del día,
     * incluyendo las citas de la mañana y de la tarde, y si está bloqueado.
     */
    @GetMapping("/calendar/{date}")
    public ResponseEntity<Map<String, Object>> getDayDetails(@PathVariable String date) {
        Optional<CalendarDay> calendarDayOpt = calendarDayRepository.findByDate(date);
        
        Map<String, Object> response = new HashMap<>();
        response.put("morningAppointments", new ArrayList<>());
        response.put("afternoonAppointments", new ArrayList<>());
        response.put("blockedMorning", false);
        response.put("blockedAfternoon", false);
        
        if (!calendarDayOpt.isPresent()) {
            return ResponseEntity.ok(response);
        }

        CalendarDay calendarDay = calendarDayOpt.get();
        response.put("id", calendarDay.getId());
        response.put("blockedMorning", calendarDay.isBlockedMorning());
        response.put("blockedAfternoon", calendarDay.isBlockedAfternoon());

        // Procesar citas de la mañana
        List<Map<String, Object>> morningAppointments = new ArrayList<>();
        for (String appointmentId : calendarDay.getAppointmentsMorning()) {
            Optional<Appointment> appointmentOpt = appointmentRepository.findById(new ObjectId(appointmentId));
            if (appointmentOpt.isPresent()) {
                Appointment appointment = appointmentOpt.get();
                Optional<Service> serviceOpt = serviceRepository.findById(appointment.getServiceId());
                if (serviceOpt.isPresent()) {
                    Map<String, Object> appointmentInfo = new HashMap<>();
                    appointmentInfo.put("time", appointment.getTime());
                    appointmentInfo.put("duration", serviceOpt.get().getDuration());
                    morningAppointments.add(appointmentInfo);
                }
            }
        }
        response.put("morningAppointments", morningAppointments);

        // Procesar citas de la tarde
        List<Map<String, Object>> afternoonAppointments = new ArrayList<>();
        for (String appointmentId : calendarDay.getAppointmentsAfternoon()) {
            Optional<Appointment> appointmentOpt = appointmentRepository.findById(new ObjectId(appointmentId));
            if (appointmentOpt.isPresent()) {
                Appointment appointment = appointmentOpt.get();
                Optional<Service> serviceOpt = serviceRepository.findById(appointment.getServiceId());
                if (serviceOpt.isPresent()) {
                    Map<String, Object> appointmentInfo = new HashMap<>();
                    appointmentInfo.put("time", appointment.getTime());
                    appointmentInfo.put("duration", serviceOpt.get().getDuration());
                    afternoonAppointments.add(appointmentInfo);
                }
            }
        }
        response.put("afternoonAppointments", afternoonAppointments);

        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene la lista de días bloqueados del calendario.
     * @return ResponseEntity con una lista de mapas, donde cada mapa representa un día bloqueado
     * y contiene su ID, fecha y tipo de bloqueo (full, morning, afternoon).
     */
    @GetMapping("/calendar/blocked")
    public ResponseEntity<List<Map<String, String>>> getBlockedDays() {
        List<CalendarDay> allDays = calendarDayRepository.findAll();
        List<Map<String, String>> blockedDays = new ArrayList<>();
        
        for (CalendarDay day : allDays) {
            if (day.isBlocked() || day.isBlockedMorning() || day.isBlockedAfternoon()) {
                Map<String, String> dayMap = new HashMap<>();
                dayMap.put("id", day.getId());
                dayMap.put("date", day.getDate());
                
                // Determinar el tipo de bloqueo
                if (day.isBlocked()) {
                    dayMap.put("blockType", "full");
                } else if (day.isBlockedMorning()) {
                    dayMap.put("blockType", "morning");
                } else if (day.isBlockedAfternoon()) {
                    dayMap.put("blockType", "afternoon");
                }
                
                blockedDays.add(dayMap);
            }
        }
        
        return ResponseEntity.ok(blockedDays);
    }

    /**
     * Guarda la configuración de bloqueo de una lista de días en el calendario.
     * @param blockedDays Lista de mapas, donde cada mapa contiene la fecha y el tipo de bloqueo
     * (full, morning, afternoon) a aplicar.
     * @return ResponseEntity con estado 200 OK si la operación se realiza correctamente.
     */
    @PostMapping("/calendar/blocked")
    public ResponseEntity<Void> saveBlockedDays(@RequestBody List<Map<String, String>> blockedDays) {
        for (Map<String, String> blockedDay : blockedDays) {
            String date = blockedDay.get("date");
            String blockType = blockedDay.get("blockType");
            
            Optional<CalendarDay> existingDay = calendarDayRepository.findByDate(date);
            CalendarDay calendarDay;
            
            if (existingDay.isPresent()) {
                calendarDay = existingDay.get();
            } else {
                calendarDay = new CalendarDay();
                calendarDay.setDate(date);
                calendarDay.setAppointmentsMorning(new ArrayList<>());
                calendarDay.setAppointmentsAfternoon(new ArrayList<>());
            }
            
            // Primero poner todo a false
            calendarDay.setBlocked(false);
            calendarDay.setBlockedMorning(false);
            calendarDay.setBlockedAfternoon(false);
            
            // Luego activar solo el tipo de bloqueo especificado
            switch (blockType) {
                case "full":
                    calendarDay.setBlocked(true);
                    break;
                case "morning":
                    calendarDay.setBlockedMorning(true);
                    break;
                case "afternoon":
                    calendarDay.setBlockedAfternoon(true);
                    break;
            }
            
            calendarDayRepository.save(calendarDay);
        }
        
        return ResponseEntity.ok().build();
    }

    /**
     * Desbloquea una lista de días en el calendario.
     * @param blockedDays Lista de mapas, donde cada mapa contiene la fecha y el tipo de bloqueo
     * a eliminar (full, morning, afternoon).
     * @return ResponseEntity con estado 200 OK si la operación se realiza correctamente.
     */
    @DeleteMapping("/calendar/blocked")
    public ResponseEntity<Void> unblockDays(@RequestBody List<Map<String, String>> blockedDays) {
        for (Map<String, String> blockedDay : blockedDays) {
            String date = blockedDay.get("date");
            String blockType = blockedDay.get("blockType");
            
            Optional<CalendarDay> existingDay = calendarDayRepository.findByDate(date);
            
            if (existingDay.isPresent()) {
                CalendarDay calendarDay = existingDay.get();
                
                // Desbloquear según el tipo
                switch (blockType) {
                    case "full":
                        calendarDay.setBlocked(false);
                        break;
                    case "morning":
                        calendarDay.setBlockedMorning(false);
                        break;
                    case "afternoon":
                        calendarDay.setBlockedAfternoon(false);
                        break;
                }
                
                calendarDayRepository.save(calendarDay);
            }
        }
        
        return ResponseEntity.ok().build();
    }
}