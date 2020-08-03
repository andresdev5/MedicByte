package ec.edu.espe.medicbyte.model;

import ec.edu.espe.medicbyte.common.core.Model;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Andres Jonathan J.
 */
public class Role implements Model {
    private int id;
    private String name;
    private List<Permission> permissions = new ArrayList<>();
    
    public Role() {}
    
    public Role(String name) {
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
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
