package ec.edu.espe.medicbyte.service.impl;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.DaoService;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.service.IMedicService;
import ec.edu.espe.medicbyte.service.IRoleService;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class MedicService extends DaoService<Medic> implements IMedicService {
    @Inject() private IRoleService roleService;
    
    @Override
    public List<Medic> getAll() {
        Role role = roleService.get("medic");
        
        if (role == null) {
            return Collections.emptyList();
        }
        
        return databaseManager.getDatastore()
            .createQuery(Medic.class)
            .field("role")
            .equal(role)
            .find()
            .toList();
    }
}
