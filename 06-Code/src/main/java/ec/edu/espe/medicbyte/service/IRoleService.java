package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Permission;
import ec.edu.espe.medicbyte.model.Role;
import java.util.List;


/**
 *
 * @author Andres Jonathan J.
 */
public interface IRoleService {
    public boolean createRole(String name, List<Permission> permissions);
    public Role getRole(int id);
    public Role getRole(String name);
    public boolean createRole(String name);
    public boolean roleExists(String name);
    public List<Role> getAllRoles();
    public int getTotalRoles();
}
