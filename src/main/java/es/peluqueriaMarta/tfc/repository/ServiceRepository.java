package es.peluqueriaMarta.tfc.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.peluqueriaMarta.tfc.model.Service;

/**
 * Repositorio para la entidad Service.
 * Permite realizar operaciones de persistencia en la base de datos para la entidad Service.
 */
@Repository
public interface ServiceRepository extends MongoRepository<Service, ObjectId> {
}
