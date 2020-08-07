package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Speciality;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public interface IMedicService {
    List<Medic> getAllMedics();
    int getTotalMedics();
    Medic addMedic(Medic medic);
    Medic addMedic(int userId, Speciality speciality);
    Medic getMedic(int id);
}
