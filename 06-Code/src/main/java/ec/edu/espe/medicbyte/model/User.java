package ec.edu.espe.medicbyte.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Field;
import dev.morphia.annotations.Index;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexes;
import dev.morphia.annotations.Reference;
import ec.edu.espe.medicbyte.common.core.Model;
import org.bson.types.ObjectId;

/**
 * 
 * @author Andres Jonathan J.
 */
@Entity("users")
@Indexes(
    @Index(fields = @Field("username"), options = @IndexOptions(unique = true))
)
public class User extends Model {
    private String username;
    private String email;
    private String password;
    @Reference private Role role;
    @Reference private UserProfile profile;

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
    
    public final boolean hasRole(ObjectId roleId) {
        return role.getId() == roleId;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
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
