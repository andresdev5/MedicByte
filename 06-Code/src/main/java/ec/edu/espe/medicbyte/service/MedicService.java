package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.model.User;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public interface MedicService {
    public List<Medic> getAllMedics();
    public int getTotalMedics();
    public boolean addMedic(User user, Speciality speciality, String fullName);
    public boolean addMedic(Medic medic);
    public Medic getMedic(int id);
}
