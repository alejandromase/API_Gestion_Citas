package es.peluqueriaMarta.tfc.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa un día en el calendario de la peluquería.
 */
@Document(collection = "calendar")
public class CalendarDay {
    @Id
    private String id;
    private String date;
    private boolean blocked;
    private boolean blockedMorning;
    private boolean blockedAfternoon;
    private List<String> appointmentsMorning;
    private List<String> appointmentsAfternoon;

    public CalendarDay() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlockedMorning() {
        return blockedMorning;
    }

    public void setBlockedMorning(boolean blockedMorning) {
        this.blockedMorning = blockedMorning;
    }

    public boolean isBlockedAfternoon() {
        return blockedAfternoon;
    }

    public void setBlockedAfternoon(boolean blockedAfternoon) {
        this.blockedAfternoon = blockedAfternoon;
    }

    public List<String> getAppointmentsMorning() {
        return appointmentsMorning;
    }

    public void setAppointmentsMorning(List<String> appointmentsMorning) {
        this.appointmentsMorning = appointmentsMorning;
    }

    public List<String> getAppointmentsAfternoon() {
        return appointmentsAfternoon;
    }

    public void setAppointmentsAfternoon(List<String> appointmentsAfternoon) {
        this.appointmentsAfternoon = appointmentsAfternoon;
    }
}