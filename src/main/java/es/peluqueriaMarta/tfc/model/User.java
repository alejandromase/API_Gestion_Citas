package es.peluqueriaMarta.tfc.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa un usuario.
 */
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private boolean isActive;
    private List<String> roles;
    private String token;
    private String address_street;
    private String address_city;
    private String address_state;
    private String address_postal_code;

    public User() {}

    public User(String id, String fullName, String email, String password, String phone, boolean isActive,
            List<String> roles, String token, String address_street, String address_city, String address_state,
            String address_postal_code) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.isActive = isActive;
        this.roles = roles;
        this.token = token;
        this.address_street = address_street;
        this.address_city = address_city;
        this.address_state = address_state;
        this.address_postal_code = address_postal_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress_street() {
        return address_street;
    }

    public void setAddress_street(String address_street) {
        this.address_street = address_street;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_state() {
        return address_state;
    }

    public void setAddress_state(String address_state) {
        this.address_state = address_state;
    }

    public String getAddress_postal_code() {
        return address_postal_code;
    }

    public void setAddress_postal_code(String address_postal_code) {
        this.address_postal_code = address_postal_code;
    }
}
