package es.peluqueriaMarta.tfc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.peluqueriaMarta.tfc.model.WorkdaySchedule;

/**
 * Repositorio para la entidad WorkdaySchedule.
 * Permite realizar operaciones de persistencia en la base de datos para la entidad WorkdaySchedule.
 */
@Repository
public interface WorkdayScheduleRepository extends MongoRepository<WorkdaySchedule, String> {
}