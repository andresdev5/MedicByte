package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.common.core.IDaoService;
import ec.edu.espe.medicbyte.model.Permission;
import ec.edu.espe.medicbyte.model.Role;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public interface IRoleService extends IDaoService<Role> {
    boolean create(String name);
    boolean create(String name, List<Permission> permissions);
    Role get(String name);
    boolean exists(String name);
}
