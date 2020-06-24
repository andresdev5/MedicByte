package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Patient;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public interface PatientService {
    public void savePatient(Patient patient);
    public List<Patient> getAllPatients();
    public Patient getPatient(String identification);
    public int getTotalPatients();
}
