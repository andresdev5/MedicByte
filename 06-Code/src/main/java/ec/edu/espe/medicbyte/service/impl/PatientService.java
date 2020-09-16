package ec.edu.espe.medicbyte.service.impl;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.DaoService;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.IPatientService;
import ec.edu.espe.medicbyte.service.IRoleService;
import ec.edu.espe.medicbyte.service.IUserService;
import java.util.Collections;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Andres Jonathan J.
 */
public class PatientService extends DaoService<Patient> implements IPatientService {
    @Inject() private IRoleService roleService;
    @Inject() private IUserService userService;
    
    @Override
    public List<Patient> getAll() {
        Role role = roleService.get("patient");
        
        if (role == null) {
            return Collections.emptyList();
        }
        
        List<Patient> patients = databaseManager.getDatastore()
            .createQuery(Patient.class)
            .field("role")
            .equal(role)
            .find()
            .toList();
        
        List<User> users = userService.getAll();
        
        for (Patient patient : patients) {
            User jsonUser = users.stream()
                .filter(u -> u.getId().equals(patient.getId()))
                .findFirst()
                .orElse(null);
            
            if (jsonUser == null) continue;
            
            patient.setUsername(jsonUser.getUsername());
            patient.setEmail(jsonUser.getEmail());
            patient.setPassword(jsonUser.getPassword());
        }
        
        return patients;
    }
    
    @Override
    public Patient get(String idCard) {
        Patient patient = field("idCard").equal(idCard).first();
        User jsonUser = userService.get(patient.getId());
        
        if (jsonUser != null) {
            patient.setUsername(jsonUser.getUsername());
            patient.setEmail(jsonUser.getEmail());
            patient.setPassword(jsonUser.getPassword());
        }
        
        return patient;
    }
    
    @Override
    public Patient get(ObjectId id) {
        Patient patient = super.get(id);
        User jsonUser = userService.get(patient.getId());
        
        if (jsonUser != null) {
            patient.setUsername(jsonUser.getUsername());
            patient.setEmail(jsonUser.getEmail());
            patient.setPassword(jsonUser.getPassword());
        }
        
        return patient;
    }
}
