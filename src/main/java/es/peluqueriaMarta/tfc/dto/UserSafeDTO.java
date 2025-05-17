package es.peluqueriaMarta.tfc.dto;

import java.util.List;

import es.peluqueriaMarta.tfc.model.User;

/**
 * DTO para transferir información segura del usuario.
 * Este DTO excluye información sensible como la contraseña.
 */
public class UserSafeDTO {
    private final String id;
    private final String email;
    private final String phone;
    private final String fullName;
    private final boolean isActive;
    private final List<String> roles;
    private final String address_street;
    private final String address_city;
    private final String address_state;
    private final String address_postal_code;

    /**
     * Constructor para crear un UserSafeDTO a partir de un objeto User.
     * @param user El objeto User del cual se extraen los datos.
     */
    public UserSafeDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.fullName = user.getFullName();
        this.isActive = user.getIsActive();
        this.roles = user.getRoles();
        this.address_street = user.getAddress_street();
        this.address_city = user.getAddress_city();
        this.address_state = user.getAddress_state();
        this.address_postal_code = user.getAddress_postal_code();
    }

    /**
     * Obtiene el ID del usuario.
     * @return El ID del usuario.
     */
    public String getId() { return id; }
    /**
     * Obtiene el email del usuario.
     * @return El email del usuario.
     */
    public String getEmail() { return email; }
    /**
     * Obtiene el teléfono del usuario.
     * @return El teléfono del usuario.
     */
    public String getPhone() { return phone; }
    /**
     * Obtiene el nombre completo del usuario.
     * @return El nombre completo del usuario.
     */
    public String getFullName() { return fullName; }
    /**
     * Obtiene el estado de actividad del usuario.
     * @return El estado de actividad del usuario.
     */
    public boolean getIsActive() { return isActive; }
    /**
     * Obtiene la lista de roles del usuario.
     * @return La lista de roles del usuario.
     */
    public List<String> getRoles() { return roles; }
    /**
     * Obtiene la calle de la dirección del usuario.
     * @return La calle de la dirección del usuario.
     */
    public String getAddress_street() { return address_street; }
    /**
     * Obtiene la ciudad de la dirección del usuario.
     * @return La ciudad de la dirección del usuario.
     */
    public String getAddress_city() { return address_city; }
    /**
     * Obtiene el estado/provincia de la dirección del usuario.
     * @return El estado/provincia de la dirección del usuario.
     */
    public String getAddress_state() { return address_state; }
    /**
     * Obtiene el código postal de la dirección del usuario.
     * @return El código postal de la dirección del usuario.
     */
    public String getAddress_postal_code() { return address_postal_code; }
}
