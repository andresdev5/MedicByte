package ec.edu.espe.medicbyte.model;

/**
 *
 * @author Andres Jonathan J.
 */
public class Speciality {
    private int id;
    private String name;
    
    public Speciality() {}
    
    public Speciality(String name) {
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
}
