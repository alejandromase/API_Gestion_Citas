package es.peluqueriaMarta.tfc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import es.peluqueriaMarta.tfc.model.WeeklyConfig;
import es.peluqueriaMarta.tfc.model.WorkdaySchedule;
import es.peluqueriaMarta.tfc.repository.WeeklyConfigRepository;
import es.peluqueriaMarta.tfc.repository.WorkdayScheduleRepository;

/**
 * Clase que inicializa la configuración de la aplicación al arrancar.
 * Implementa la interfaz CommandLineRunner para ejecutar código
 * después de que la aplicación Spring Boot se haya iniciado.
 */
@Component
public class AppInitializer implements CommandLineRunner {

    @Autowired
    private WeeklyConfigRepository weeklyConfigRepository;

    @Autowired
    private WorkdayScheduleRepository workdayScheduleRepository;

    /**
     * Método que se ejecuta al iniciar la aplicación.
     * Inicializa la configuración semanal y el horario laboral si no existen.
     * @param args Argumentos de la línea de comandos.
     * @throws Exception Si ocurre algún error durante la inicialización.
     */
    @Override
    public void run(String... args) throws Exception {
        // Inicializar configuración semanal
        if (weeklyConfigRepository.count() == 0) {
            WeeklyConfig config = new WeeklyConfig();
            weeklyConfigRepository.save(config);
            System.out.println("Configuración semanal inicializada");
        }

        // Inicializar horario laboral
        if (workdayScheduleRepository.count() == 0) {
            WorkdaySchedule schedule = new WorkdaySchedule();
            workdayScheduleRepository.save(schedule);
            System.out.println("Horario laboral inicializado");
        }
    }
}