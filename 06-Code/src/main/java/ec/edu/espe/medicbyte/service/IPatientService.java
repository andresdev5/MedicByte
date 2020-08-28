package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.common.core.IDaoService;
import ec.edu.espe.medicbyte.model.Patient;

/**
 *
 * @author Andres Jonathan J.
 */
public interface IPatientService extends IDaoService<Patient> {
    Patient get(String idCard);
}
