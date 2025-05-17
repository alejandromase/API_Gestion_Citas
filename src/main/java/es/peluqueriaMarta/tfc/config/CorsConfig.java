package es.peluqueriaMarta.tfc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase de configuración para habilitar CORS (Cross-Origin Resource Sharing)
 * en la aplicación. Permite que el frontend acceda a los recursos del backend
 * desde diferentes dominios.
 */
@Configuration
public class CorsConfig {

    /**
     * Define un bean para configurar CORS.
     * @return Un objeto WebMvcConfigurer que define la configuración de CORS.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Configura los mapeos de CORS.
             * @param registry El registro de CORS al que se añaden los mapeos.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica la configuración a todas las rutas
                        .allowedOrigins("*") // Permite solicitudes desde cualquier origen
                        .allowedMethods("*") // Permite todos los métodos HTTP (GET, POST, PUT, DELETE, etc.)
                        .allowedHeaders("*") // Permite todas las cabeceras en las solicitudes
                        .maxAge(3600); // Establece el tiempo máximo de vida de la configuración en segundos (1 hora)
            }
        };
    }
}
