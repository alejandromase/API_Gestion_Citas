package es.peluqueriaMarta.tfc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.peluqueriaMarta.tfc.model.WorkdaySchedule;
import es.peluqueriaMarta.tfc.repository.WorkdayScheduleRepository;

/**
 * Controlador para la gestión del horario laboral.
 * Permite crear, actualizar y obtener el horario laboral.
 */
@RestController
@RequestMapping("/API")
public class WorkdayScheduleController {

    @Autowired
    private WorkdayScheduleRepository workdayScheduleRepository;

    /**
     * Crea o actualiza el horario laboral.
     * Si ya existe un horario, actualiza sus valores. Si no existe, crea uno nuevo.
     * @param schedule El horario laboral a crear o actualizar.
     * @return ResponseEntity con estado 200 OK si la operación se realiza correctamente.
     */
    @PostMapping("/workday-schedules")
    public ResponseEntity<Void> createOrUpdateWorkdaySchedule(@RequestBody WorkdaySchedule schedule) {
        // Buscar si ya existe un horario
        WorkdaySchedule existingSchedule = workdayScheduleRepository.findAll().stream().findFirst().orElse(null);
        
        if (existingSchedule != null) {
            // Si existe, actualizamos sus valores
            existingSchedule.setMorningStart(schedule.getMorningStart());
            existingSchedule.setMorningEnd(schedule.getMorningEnd());
            existingSchedule.setAfternoonStart(schedule.getAfternoonStart());
            existingSchedule.setAfternoonEnd(schedule.getAfternoonEnd());
            workdayScheduleRepository.save(existingSchedule);
        } else {
            // Si no existe, creamos uno nuevo
            workdayScheduleRepository.save(schedule);
        }
        
        return ResponseEntity.ok().build();
    }

    /**
     * Obtiene el horario laboral.
     * @return ResponseEntity con un mapa que contiene el horario laboral.
     */
    @GetMapping("/workday-schedules")
    public ResponseEntity<Map<String, String>> getWorkdaySchedule() {
        WorkdaySchedule schedule = workdayScheduleRepository.findAll().stream().findFirst().orElse(null);
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("morningStart", schedule.getMorningStart());
        response.put("morningEnd", schedule.getMorningEnd());
        response.put("afternoonStart", schedule.getAfternoonStart());
        response.put("afternoonEnd", schedule.getAfternoonEnd());

        return ResponseEntity.ok(response);
    }
}