package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Speciality;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public interface ISpecialityService {
    public List<Speciality> getAllSpecialities();
    public int getTotalSpecialities();
    public boolean addSpeciality(Speciality speciality);
    public Speciality getSpeciality(int id);
    public Speciality getSpeciality(String name);
}
