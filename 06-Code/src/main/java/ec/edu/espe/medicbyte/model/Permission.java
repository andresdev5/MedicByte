package ec.edu.espe.medicbyte.model;

/**
 *
 * @author Andres Jonathan J.
 */
public class Permission {
    private String name;
    private boolean allowed;
    
    public Permission() {}
    
    public Permission(String name, boolean allowed) {
        this.name = name;
        this.allowed = allowed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }
}
