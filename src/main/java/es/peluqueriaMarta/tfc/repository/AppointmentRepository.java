package es.peluqueriaMarta.tfc.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.peluqueriaMarta.tfc.model.Appointment;

/**
 * Repositorio para la entidad Appointment.
 * Permite realizar operaciones de persistencia en la base de datos para la entidad Appointment.
 */
@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, ObjectId> {

    /**
     * Busca citas por userId.
     * @param userId El ID del usuario.
     * @return Una lista de citas que coinciden con el userId.
     */
    List<Appointment> findByUserId(String userId);

    /**
     * Busca citas por fecha y hora.
     * @param date La fecha de la cita.
     * @param time La hora de la cita.
     * @return Una lista de citas que coinciden con los criterios de búsqueda.
     */
    List<Appointment> findByDateAndTime(String date, String time);
}