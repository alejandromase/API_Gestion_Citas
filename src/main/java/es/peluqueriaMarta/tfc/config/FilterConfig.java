package es.peluqueriaMarta.tfc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración para registrar el filtro JWT.
 * Este filtro intercepta las peticiones a la API y verifica el token JWT.
 */
@Configuration
public class FilterConfig {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Crea y registra el filtro JWT.
     * @return Un FilterRegistrationBean que contiene el filtro JWT.
     */
    @Bean
    public FilterRegistrationBean<JwtRequestFilter> jwtFilter() {
        FilterRegistrationBean<JwtRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtRequestFilter);
        registrationBean.addUrlPatterns("/API/*"); // cada vez que alguien accede a /API/*, Spring pasará la petición por JwtRequestFilter antes de llegar al controlador
        return registrationBean;
    }
}
