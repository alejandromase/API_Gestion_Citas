package es.peluqueriaMarta.tfc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.peluqueriaMarta.tfc.model.Appointment;
import es.peluqueriaMarta.tfc.model.CalendarDay;
import es.peluqueriaMarta.tfc.model.Service;
import es.peluqueriaMarta.tfc.model.User;
import es.peluqueriaMarta.tfc.model.WorkdaySchedule;
import es.peluqueriaMarta.tfc.repository.AppointmentRepository;
import es.peluqueriaMarta.tfc.repository.CalendarDayRepository;
import es.peluqueriaMarta.tfc.repository.ServiceRepository;
import es.peluqueriaMarta.tfc.repository.UserRepository;
import es.peluqueriaMarta.tfc.repository.WorkdayScheduleRepository;

/**
 * Controlador para la gestión de citas.
 * Permite crear, obtener y listar citas.
 */
@RestController
@RequestMapping("/API")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CalendarDayRepository calendarDayRepository;

    @Autowired
    private WorkdayScheduleRepository workdayScheduleRepository;

    /**
     * Crea una nueva cita. Controla que no se pueda crear una cita en el mismo día y a la misma hora para el mismo usuario.
     * @param appointment La cita a crear.
     * @return ResponseEntity con estado 200 OK si la cita se crea correctamente, o 409 si ya existe.
     */
    @PostMapping("/appointments")
    public ResponseEntity<Void> createAppointment(@RequestBody Appointment appointment) {
        // Comprobar si ya existe una cita para el mismo usuario, fecha y hora
        List<Appointment> existing = appointmentRepository.findByDateAndTime(
            appointment.getDate(), appointment.getTime()
        );
        if (!existing.isEmpty()) {
            return ResponseEntity.status(409).build();
        }

        // Guardar la cita
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Obtener o crear el día en el calendario
        Optional<CalendarDay> existingDay = calendarDayRepository.findByDate(appointment.getDate());
        CalendarDay calendarDay;

        if (existingDay.isPresent()) {
            calendarDay = existingDay.get();
        } else {
            calendarDay = new CalendarDay();
            calendarDay.setDate(appointment.getDate());
            calendarDay.setBlocked(false);
            calendarDay.setBlockedMorning(false);
            calendarDay.setBlockedAfternoon(false);
            calendarDay.setAppointmentsMorning(new ArrayList<>());
            calendarDay.setAppointmentsAfternoon(new ArrayList<>());
        }

        // Obtener los horarios laborales
        WorkdaySchedule schedule = workdayScheduleRepository.findAll().stream().findFirst().orElse(null);
        if (schedule == null) {
            return ResponseEntity.badRequest().build();
        }

        // Determinar si es mañana o tarde según los horarios
        String time = appointment.getTime();
        if (time.compareTo(schedule.getMorningEnd()) <= 0) {
            calendarDay.getAppointmentsMorning().add(savedAppointment.getId().toString());
        } else {
            calendarDay.getAppointmentsAfternoon().add(savedAppointment.getId().toString());
        }

        // Guardar el día actualizado
        calendarDayRepository.save(calendarDay);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/appointments/user/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getAppointmentsByUser(@PathVariable String userId) {
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);
        List<Map<String, Object>> response = new ArrayList<>();

        for (Appointment appointment : appointments) {
            Service service = serviceRepository.findById(appointment.getServiceId()).orElse(null);
            if (service != null) {
                Map<String, Object> appointmentMap = new HashMap<>();
                appointmentMap.put("id", appointment.getId().toString());
                appointmentMap.put("serviceName", service.getDescription());
                appointmentMap.put("date", appointment.getDate());
                appointmentMap.put("time", appointment.getTime());
                appointmentMap.put("status", appointment.getStatus());
                response.add(appointmentMap);
            }
        }

        return ResponseEntity.ok(response);
    }


    @GetMapping("/appointments")
    public ResponseEntity<List<Map<String, Object>>> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Appointment appointment : appointments) {
            Service service = serviceRepository.findById(appointment.getServiceId()).orElse(null);
            User user = userRepository.findById(appointment.getUserId()).orElse(null);

            Map<String, Object> appointmentMap = new HashMap<>();
            appointmentMap.put("id", appointment.getId().toString());
            appointmentMap.put("serviceName", service != null ? service.getDescription() : "Servicio no disponible");
            appointmentMap.put("date", appointment.getDate());
            appointmentMap.put("time", appointment.getTime());
            appointmentMap.put("status", appointment.getStatus());

            if (user != null) {
                appointmentMap.put("userName", user.getFullName());
                appointmentMap.put("userEmail", user.getEmail());
                appointmentMap.put("userPhone", user.getPhone());
            } else {
                appointmentMap.put("userName", "Usuario no disponible");
                appointmentMap.put("userEmail", "Usuario no disponible");
                appointmentMap.put("userPhone", "Usuario no disponible");
            }
            response.add(appointmentMap);
        }

        return ResponseEntity.ok(response);
    }
}