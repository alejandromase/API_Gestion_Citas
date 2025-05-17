package es.peluqueriaMarta.tfc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.peluqueriaMarta.tfc.dto.UserSafeDTO;
import es.peluqueriaMarta.tfc.model.User;
import es.peluqueriaMarta.tfc.repository.UserRepository;
import es.peluqueriaMarta.tfc.util.JwtUtil;
import es.peluqueriaMarta.tfc.util.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controlador para la gestión de usuarios y autenticación.
 * Permite el login, logout, registro y verificación del estado de autenticación.
 */
@RestController
@RequestMapping("/API/auth")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * Realiza el login de un usuario.
     * @param payload Mapa con el email y la contraseña del usuario.
     * @return ResponseEntity con el usuario y el token JWT si las credenciales son correctas,
     *         o un error con estado 401 Unauthorized si las credenciales son incorrectas.
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, Object> payload) {
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");
        Optional<User> auth = userRepository.findByEmail(email);
        if (auth.isPresent() && PasswordUtil.verifyPassword(password, auth.get().getPassword())) {
            // Generar token JWT y guardar en usuario
            String token = jwtUtil.generateToken(email);
            User user = auth.get();
            user.setToken(token);
            userRepository.save(user);
            // Devolver usuario y token como en el frontend
            Map<String, Object> response = new HashMap<>();
            response.put("user", new UserSafeDTO(user));
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("ok", false);
            error.put("message", "Credenciales incorrectas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    /**
     * Realiza el logout de un usuario.
     * Elimina el token JWT del usuario.
     * @param request La solicitud HTTP con la cabecera de autorización.
     * @return ResponseEntity con estado 200 OK si el logout se realiza correctamente,
     *         o un error con estado 401 Unauthorized si no se encuentra el usuario.
     */
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Optional<User> userOpt = userRepository.findByToken(token);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setToken(null);
                userRepository.save(user);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Registra un nuevo usuario.
     * @param payload Mapa con los datos del usuario a registrar.
     * @return ResponseEntity con estado 200 OK si el registro se realiza correctamente,
     *         o un error con estado 409 Conflict si el email ya está registrado.
     */
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Map<String, Object> payload) {
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");
        String fullName = (String) payload.get("fullName");
        String phone = (String) payload.get("phone");
        String address_street = (String) payload.getOrDefault("address_street", null);
        String address_city = (String) payload.getOrDefault("address_city", null);
        String address_state = (String) payload.getOrDefault("address_state", null);
        String address_postal_code = (String) payload.getOrDefault("address_postal_code", null);

        Optional<User> existing = userRepository.findByEmail(email);
        if (existing.isPresent()) {
            Map<String, Object> error = new HashMap<>();
            error.put("ok", false);
            error.put("message", "El usuario ya existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setIsActive(true);
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(PasswordUtil.hashPassword(password));
        user.setRoles(List.of("client"));
        user.setAddress_street(address_street);
        user.setAddress_city(address_city);
        user.setAddress_state(address_state);
        user.setAddress_postal_code(address_postal_code);
        userRepository.save(user);

        // Generar token JWT
        String token = jwtUtil.generateToken(email);
        user.setToken(token);
        userRepository.save(user);

        // Crear UserSafeDTO
        UserSafeDTO userSafeDTO = new UserSafeDTO(user);

        // Crear response
        Map<String, Object> response = new HashMap<>();
        response.put("user", userSafeDTO);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    /**
     * Verifica el estado de autenticación de un usuario.
     * @param request La solicitud HTTP con la cabecera de autorización.
     * @return ResponseEntity con el usuario y el token JWT si el usuario está autenticado,
     *         o un error con estado 401 Unauthorized si el usuario no está autenticado.
     */
    @GetMapping("/check-status")
    public ResponseEntity<Object> checkStatus(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Optional<User> userOpt = userRepository.findByToken(token);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (token.equals(user.getToken())) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("user", new UserSafeDTO(user));
                    response.put("token", token);
                    return ResponseEntity.ok(response);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}