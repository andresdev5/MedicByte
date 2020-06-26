package ec.edu.espe.medicbyte.model;

/**
 *
 * @author Andres Jonathan J.
 */
public class Role {
    private String name;
    //private List<Permission> permissions;

    public Role(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}