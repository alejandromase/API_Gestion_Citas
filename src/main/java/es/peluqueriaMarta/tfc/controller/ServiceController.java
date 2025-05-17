package es.peluqueriaMarta.tfc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import es.peluqueriaMarta.tfc.model.Service;
import es.peluqueriaMarta.tfc.repository.ServiceRepository;

/**
 * Controlador para la gestión de servicios.
 * Permite obtener, crear y eliminar servicios.
 */
@RestController
@RequestMapping("/API")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    /**
     * Obtiene la lista de todos los servicios.
     * @return ResponseEntity con la lista de servicios.
     */
    @GetMapping("/services")
    public ResponseEntity<List<Map<String, Object>>> getAllServices() {
        List<Service> services = serviceRepository.findAll();
        List<Map<String, Object>> response = new ArrayList<>();
        
        for (Service service : services) {
            Map<String, Object> serviceMap = new HashMap<>();
            serviceMap.put("id", service.getId().toString());
            serviceMap.put("description", service.getDescription());
            serviceMap.put("duration", service.getDuration());
            response.add(serviceMap);
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * Crea un nuevo servicio.
     * @param service El servicio a crear.
     * @return ResponseEntity con estado 200 OK si la operación se realiza correctamente.
     */
    @PostMapping("/services")
    public ResponseEntity<Void> createService(@RequestBody Service service) {
        serviceRepository.save(service);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina un servicio por su ID.
     * @param id El ID del servicio a eliminar.
     * @return ResponseEntity con estado 200 OK si la operación se realiza correctamente.
     */
    @DeleteMapping("/services/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable String id) {
        serviceRepository.deleteById(new ObjectId(id));
        return ResponseEntity.ok().build();
    }
}