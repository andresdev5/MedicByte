package ec.edu.espe.medicbyte.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 
 * @author Andres Jonathan J.
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private UserProfile profile;
    private LocalDateTime registeredAt;

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final String getUsername() {
        return username;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    public final String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    public final Role getRole() {
        return role;
    }

    public final void setRole(Role role) {
        this.role = role;
    }
    
    public final boolean hasRole(String roleName) {
        return role.getName().equalsIgnoreCase(roleName);
    }
    
    public final boolean hasRole(int id) {
        return role.getId() == id;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }
    
    public String getDisplayName() {
        if (getProfile() == null
            || getProfile().getFullName() == null
            || getProfile().getFullName().trim().isEmpty()) {
            return getUsername();
        }
        
        return getProfile().getFullName();
    }
}
