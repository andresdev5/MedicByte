package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Patient;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public interface PatientService {
    public boolean addPatient(Patient patient);
    public Patient getPatient(int userId);
    public Patient getPatient(String idCard);
    public List<Patient> getAllPatients();
    public int getTotalPatients();
}
