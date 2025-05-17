package es.peluqueriaMarta.tfc.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * Clase utilitaria para la gestión de tokens JWT (JSON Web Tokens).
 * Permite generar, extraer información y validar tokens JWT.
 */
@Component
public class JwtUtil {
    private final String SECRET_KEY = "1656a10f-47e7-4f4e-a371-0b55764107ef";

    /**
     * Genera un token JWT para el email especificado.
     * @param email El email del usuario para el cual se genera el token.
     * @return El token JWT generado.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Extrae el email del usuario del token JWT.
     * @param token El token JWT del cual se extrae el email.
     * @return El email del usuario.
     */
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Alias para el método extractEmail para mantener compatibilidad con versiones anteriores.
     * @param token El token JWT del cual se extrae el nombre de usuario.
     * @return El nombre de usuario (email) extraído del token.
     */
    public String extractUsername(String token) {
        return extractEmail(token);
    }

    /**
     * Valida el token JWT.
     * Verifica si el token es válido para el email especificado.
     * @param token El token JWT a validar.
     * @param email El email del usuario.
     * @return true si el token es válido para el email, false en caso contrario.
     */
    public boolean validateToken(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return extractedEmail.equals(email);
    }

    /**
     * Obtiene las "claims" (declaraciones) del token JWT.
     * Las "claims" son pares clave-valor que contienen información sobre el token.
     * @param token El token JWT del cual se obtienen las "claims".
     * @return Un objeto Claims que contiene las "claims" del token.
     */
    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
