package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.common.core.DaoService;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.service.ISpecialityService;

/**
 *
 * @author Andres Jonathan J.
 */
public class SpecialityService extends DaoService<Speciality> implements ISpecialityService {
    @Override
    public Speciality get(String name) {
        return field("name").equal(name.toLowerCase()).first();
    }
}
