package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.common.core.DaoService;
import ec.edu.espe.medicbyte.model.Permission;
import ec.edu.espe.medicbyte.model.Role;
import java.util.ArrayList;
import java.util.List;
import ec.edu.espe.medicbyte.service.IRoleService;

/**
 *
 * @author Andres Jonathan J.
 */
public class RoleService extends DaoService<Role> implements IRoleService {
    @Override
    public boolean create(String name, List<Permission> permissions) {
        return super.save(new Role(name, permissions));
    }
    
    @Override
    public boolean create(String name) {
        return create(name, new ArrayList<>());
    }

    @Override
    public Role get(String name) {
        return field("name").equal(name).first();
    }
    
    @Override
    public boolean exists(String name) {
        return get(name) != null;
    }
}
