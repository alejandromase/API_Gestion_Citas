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

import es.peluqueriaMarta.tfc.model.WeeklyConfig;
import es.peluqueriaMarta.tfc.repository.WeeklyConfigRepository;

/**
 * Controlador para la gestión de la configuración semanal.
 * Permite obtener y actualizar la configuración de los días de la semana.
 */
@RestController
@RequestMapping("/API")
public class WeeklyConfigController {

    @Autowired
    private WeeklyConfigRepository weeklyConfigRepository;

    /**
     * Obtiene la configuración semanal.
     * @return ResponseEntity con un mapa que contiene la configuración de cada día de la semana.
     */
    @GetMapping("/weekly-config")
    public ResponseEntity<Map<String, Boolean>> getWeeklyConfig() {
        WeeklyConfig config = weeklyConfigRepository.findAll().stream().findFirst().orElse(null);
        if (config == null) {
            config = new WeeklyConfig();
            weeklyConfigRepository.save(config);
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("monday", config.isMonday());
        response.put("tuesday", config.isTuesday());
        response.put("wednesday", config.isWednesday());
        response.put("thursday", config.isThursday());
        response.put("friday", config.isFriday());
        response.put("saturday", config.isSaturday());
        response.put("sunday", config.isSunday());

        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza la configuración semanal.
     * @param requestBody Mapa con la configuración de cada día de la semana a actualizar.
     * @return ResponseEntity con estado 200 OK si la operación se realiza correctamente.
     */
    @PostMapping("/weekly-config")
    public ResponseEntity<Void> updateWeeklyConfig(@RequestBody Map<String, Boolean> requestBody) {
        WeeklyConfig existingConfig = weeklyConfigRepository.findAll().stream().findFirst().orElse(null);
        WeeklyConfig config = new WeeklyConfig();
        
        config.setMonday(requestBody.get("monday"));
        config.setTuesday(requestBody.get("tuesday"));
        config.setWednesday(requestBody.get("wednesday"));
        config.setThursday(requestBody.get("thursday"));
        config.setFriday(requestBody.get("friday"));
        config.setSaturday(requestBody.get("saturday"));
        config.setSunday(requestBody.get("sunday"));
        
        if (existingConfig != null) {
            config.setId(existingConfig.getId());
        }
        
        weeklyConfigRepository.save(config);
        return ResponseEntity.ok().build();
    }
}