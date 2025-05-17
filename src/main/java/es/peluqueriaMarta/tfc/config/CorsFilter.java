package es.peluqueriaMarta.tfc.config;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro CORS para permitir solicitudes desde diferentes dominios.
 * Este filtro se ejecuta una vez por cada solicitud y añade las cabeceras
 * necesarias para habilitar CORS.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter extends OncePerRequestFilter {

    /**
     * Método que intercepta cada solicitud y añade las cabeceras CORS necesarias.
     * @param request  La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param filterChain La cadena de filtros.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de E/S.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Permite solicitudes desde cualquier origen
        response.setHeader("Access-Control-Allow-Origin", "*");
        // Permite los métodos HTTP especificados
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // Permite las cabeceras especificadas
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        // Permite el envío de credenciales (cookies, cabeceras de autorización)
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // Establece el tiempo máximo que el navegador puede cachear la configuración CORS
        response.setHeader("Access-Control-Max-Age", "3600");

        // Si la solicitud es una solicitud OPTIONS, responde con un estado 200 OK
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            // Continúa con la cadena de filtros
            filterChain.doFilter(request, response);
        }
    }
}