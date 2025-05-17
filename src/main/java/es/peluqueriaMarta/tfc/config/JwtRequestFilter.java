package es.peluqueriaMarta.tfc.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import es.peluqueriaMarta.tfc.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro para interceptar las peticiones y validar el token JWT.
 * Este filtro se ejecuta una vez por cada petición y verifica si el token
 * JWT es válido antes de permitir el acceso al recurso.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    // Lista de URLs públicas que no requieren autenticación
    private static final List<String> PUBLIC_URLS = Arrays.asList(
        "/API/auth/login",
        "/API/auth/register",
        "/API/calendar/blocked",
        "/API/calendar/{date}",
        "/API/services",
        "/API/weekly-config",
        "/API/workday-schedules",
        "/check-status"
    );

    /**
     * Método que intercepta cada petición y realiza la validación del token JWT.
     * @param request  La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param filterChain La cadena de filtros.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de E/S.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String requestPath = request.getRequestURI();
        
        // Verificar si la URL es pública
        if (PUBLIC_URLS.stream().anyMatch(url -> {
            if (url.contains("{date}")) {
                return requestPath.matches("/API/calendar/\\d{2}-\\d{2}-\\d{4}");
            }
            return requestPath.equals(url);
        })) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authHeader.substring(7);
        try {
            String email = jwtUtil.extractEmail(token);
            if (email != null && jwtUtil.validateToken(token, email)) {
                filterChain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
