package es.peluqueriaMarta.tfc.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.peluqueriaMarta.tfc.model.User;

/**
 * Repositorio para la entidad Usuario.
 * Permite realizar operaciones de persistencia en la base de datos para la entidad Usuario.
 */
public interface UserRepository extends MongoRepository<User, String> {
	/**
	 * Busca un usuario por su email y contraseña.
	 * @param email El email del usuario.
	 * @param pass La contraseña del usuario.
	 * @return Un Optional que contiene el usuario si se encuentra, o un Optional vacío si no.
	 */
	@Query("{ 'email': ?0, 'pass': ?1 }")
	Optional<User> findByEmailAndPassword(String email, String pass);

	/**
	 * Busca un usuario por su email.
	 * @param email El email del usuario.
	 * @return Un Optional que contiene el usuario si se encuentra, o un Optional vacío si no.
	 */
	Optional<User> findByEmail(String email);

	/**
	 * Busca un usuario por su token JWT.
	 * @param token El token JWT del usuario.
	 * @return Un Optional que contiene el usuario si se encuentra, o un Optional vacío si no.
	 */
	Optional<User> findByToken(String token);
}
