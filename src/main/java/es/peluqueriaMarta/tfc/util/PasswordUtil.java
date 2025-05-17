package es.peluqueriaMarta.tfc.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Clase utilitaria para el manejo seguro de contraseñas.
 * Proporciona métodos para generar y verificar hashes SHA-256 de contraseñas.
 */
public class PasswordUtil {
    
    /**
     * Genera un hash SHA-256 de la contraseña proporcionada.
     * @param password La contraseña a hashear
     * @return El hash de la contraseña en formato Base64
     * @throws RuntimeException si ocurre un error al generar el hash
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar el hash de la contraseña", e);
        }
    }

    /**
     * Verifica si una contraseña coincide con su hash.
     * @param password La contraseña a verificar
     * @param hashedPassword El hash de la contraseña almacenado
     * @return true si la contraseña coincide con el hash, false en caso contrario
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        String hashedInput = hashPassword(password);
        return hashedInput.equals(hashedPassword);
    }
} 