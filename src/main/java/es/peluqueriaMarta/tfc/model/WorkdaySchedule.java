package es.peluqueriaMarta.tfc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa el horario laboral de la peluquería.
 * Define las horas de inicio y fin de la jornada laboral, tanto por la mañana como por la tarde.
 */
@Document(collection = "workday_schedules")
public class WorkdaySchedule {
    
    @Id
    private String id;
    private String morningStart;
    private String morningEnd;
    private String afternoonStart;
    private String afternoonEnd;

    public WorkdaySchedule() {
        this.morningStart = "09:00";
        this.morningEnd = "13:30";
        this.afternoonStart = "15:30";
        this.afternoonEnd = "19:30";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMorningStart() {
        return morningStart;
    }

    public void setMorningStart(String morningStart) {
        this.morningStart = morningStart;
    }

    public String getMorningEnd() {
        return morningEnd;
    }

    public void setMorningEnd(String morningEnd) {
        this.morningEnd = morningEnd;
    }

    public String getAfternoonStart() {
        return afternoonStart;
    }

    public void setAfternoonStart(String afternoonStart) {
        this.afternoonStart = afternoonStart;
    }

    public String getAfternoonEnd() {
        return afternoonEnd;
    }

    public void setAfternoonEnd(String afternoonEnd) {
        this.afternoonEnd = afternoonEnd;
    }
}