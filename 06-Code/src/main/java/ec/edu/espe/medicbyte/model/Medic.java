package ec.edu.espe.medicbyte.model;

/**
 *
 * @author Michael Cobacango -ESPE -DCCO
 */
public class Medic extends User {
    private Speciality speciality;
    private String fullName;
    
    public Medic() {}
    
    public Medic(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setSalt(user.getSalt());
        this.setRole(user.getRole());
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullname(String fullName) {
        this.fullName = fullName;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
}
