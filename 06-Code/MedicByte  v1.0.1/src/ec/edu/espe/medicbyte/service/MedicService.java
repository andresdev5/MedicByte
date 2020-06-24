package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Medic;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public interface MedicService {    
    public int getTotalMedics();    
    public void saveMedic(Medic medic);
    public Medic getMedic(int id);
    public List<Medic> getAllMedics();
}
