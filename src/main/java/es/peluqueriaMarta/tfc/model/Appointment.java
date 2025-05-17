package es.peluqueriaMarta.tfc.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa un una cita.
 */
@Document(collection = "appointments")
public class Appointment {
    
    @Id
    private ObjectId id;
    private String userId;
    private String date;
    private String time;
    private ObjectId serviceId;
    private String status; // confirmed o cancelled
    
    public Appointment() {}
    
    public Appointment(String userId, String date, String time, ObjectId serviceId, String status) {
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.serviceId = serviceId;
        this.status = status;
    }
    
    public ObjectId getId() {
        return id;
    }
    
    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getTime() {
        return time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    
    public ObjectId getServiceId() {
        return serviceId;
    }
    
    public void setServiceId(ObjectId serviceId) {
        this.serviceId = serviceId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
} 