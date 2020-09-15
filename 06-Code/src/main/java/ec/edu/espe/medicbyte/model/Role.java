package ec.edu.espe.medicbyte.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Field;
import dev.morphia.annotations.Index;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexes;
import dev.morphia.annotations.Reference;
import ec.edu.espe.medicbyte.common.core.Model;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Adrian Iza
 */
@Entity("roles")
@Indexes(

    @Index(fields = @Field("name"), options = @IndexOptions(unique = true))
)
public class Role extends Model {
    private String name;
    @Reference private List<Permission> permissions = new ArrayList<>();
    
    public Role() {}
    
    public Role(String name) {
        this.name = name;
    }
    
    public Role(String name, List<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

     public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

       
    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
    
    public boolean hasPermission(String name) {
        return permissions.stream()
            .filter(p -> p.getName().equalsIgnoreCase(name))
            .count() > 0;
    }
    
    public void addPermission(Permission permission) {
        permissions.add(permission);
    }
}
