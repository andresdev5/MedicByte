package ec.edu.espe.medicbyte.service.impl;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.DaoService;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.IMedicService;
import ec.edu.espe.medicbyte.service.IRoleService;
import ec.edu.espe.medicbyte.service.IUserService;
import java.util.Collections;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Andres Jonathan J.
 */
public class MedicService extends DaoService<Medic> implements IMedicService {
    @Inject() private IRoleService roleService;
    @Inject() private IUserService userService;
    
    @Override
    public List<Medic> getAll() {
        Role role = roleService.get("medic");
        
        if (role == null) {
            return Collections.emptyList();
        }
        
        List<Medic> medics = databaseManager.getDatastore()
            .createQuery(Medic.class)
            .field("role")
            .equal(role)
            .find()
            .toList();
        
        List<User> users = userService.getAll();
        
        for (Medic medic : medics) {
            User jsonUser = users.stream()
                .filter(u -> u.getId().equals(medic.getId()))
                .findFirst()
                .orElse(null);
            
            if (jsonUser == null) continue;
            
            medic.setUsername(jsonUser.getUsername());
            medic.setEmail(jsonUser.getEmail());
            medic.setPassword(jsonUser.getPassword());
        }
        
        return medics;
    }
    
    @Override
    public Medic get(ObjectId id) {
        Medic medic = super.get(id);
        
        if (medic == null) return null;
        
        User jsonUser = userService.get(id);
        
        if (jsonUser != null) {
            medic.setUsername(jsonUser.getUsername());
            medic.setEmail(jsonUser.getEmail());
            medic.setPassword(jsonUser.getPassword());
        }
        
        return medic;
    }
}
