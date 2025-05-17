package es.peluqueriaMarta.tfc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.peluqueriaMarta.tfc.model.WeeklyConfig;

/**
 * Repositorio para la entidad WeeklyConfig.
 * Permite realizar operaciones de persistencia en la base de datos para la entidad WeeklyConfig.
 */
public interface WeeklyConfigRepository extends MongoRepository<WeeklyConfig, String> {
}