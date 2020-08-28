package ec.edu.espe.medicbyte.model;

import dev.morphia.annotations.Reference;

/**
 *
 * @author Michael Cobacango -ESPE -DCCO
 */
public class Medic extends User {
    @Reference private Speciality speciality;
    
    public Medic() {}

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
}
