package es.peluqueriaMarta.tfc.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.peluqueriaMarta.tfc.model.CalendarDay;

/**
 * Repositorio para la entidad CalendarDay.
 * Permite realizar operaciones de persistencia en la base de datos para la entidad CalendarDay.
 */
public interface CalendarDayRepository extends MongoRepository<CalendarDay, String> {
    
    /**
     * Busca un día del calendario por su fecha.
     * @param date La fecha del día a buscar.
     * @return Un Optional que contiene el día del calendario si se encuentra, o un Optional vacío si no.
     */
    Optional<CalendarDay> findByDate(String date);
}