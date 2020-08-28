package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.common.core.IDaoService;
import ec.edu.espe.medicbyte.model.Speciality;

/**
 *
 * @author Andres Jonathan J.
 */
public interface ISpecialityService extends IDaoService<Speciality> {
    Speciality get(String name);
}
