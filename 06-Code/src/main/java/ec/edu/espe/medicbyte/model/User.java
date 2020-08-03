package ec.edu.espe.medicbyte.model;

/**
 * 
 * @author Andres Jonathan J.
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String salt;
    private Role role;
    
    public User() {}

    public User(int id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    
    public String getSalt() {
        return salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public boolean hasRole(String roleName) {
        return role.getName().equalsIgnoreCase(roleName);
    }
    
    public boolean hasRole(int id) {
        return role.getId() == id;
    }
}
