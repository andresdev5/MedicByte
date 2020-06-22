package ec.edu.espe.medicbyte.model;

/**
 *
 * @author Michael Cobacango -ESPE -DCCO
 */
public class Medic {    
    private int id;
    private String name;
    private Speciality speciality;
    
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

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return id + "," + speciality.getId() + ",\"" + name + "\"";
    }

}
