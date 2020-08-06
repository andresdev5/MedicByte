package ec.edu.espe.medicbyte.model;

/**
 *
 * @author Michael Cobacango -ESPE -DCCO
 */
public class Medic extends User {
    private Speciality speciality;
    
    public Medic() {}
    
    public Medic(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
        this.setRole(user.getRole());
        this.setProfile(user.getProfile());
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
}
