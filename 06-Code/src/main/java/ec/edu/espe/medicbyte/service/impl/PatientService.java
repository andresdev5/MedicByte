package ec.edu.espe.medicbyte.service.impl;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.DaoService;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.service.IPatientService;
import ec.edu.espe.medicbyte.service.IRoleService;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class PatientService extends DaoService<Patient> implements IPatientService {
    @Inject() private IRoleService roleService;
    
    @Override
    public List<Patient> getAll() {
        Role role = roleService.get("patient");
        
        if (role == null) {
            return Collections.emptyList();
        }
        
        return databaseManager.getDatastore()
            .createQuery(Patient.class)
            .field("role")
            .equal(role)
            .find()
            .toList();
    }
    
    @Override
    public Patient get(String idCard) {
        return field("idCard").equal(idCard).first();
    }
}
