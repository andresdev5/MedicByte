package ec.edu.espe.medicbyte.service.impl;

import com.google.gson.Gson;
import ec.edu.espe.medicbyte.model.Permission;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.RoleService;
import ec.edu.espe.medicbyte.util.PathUtils;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class RoleServiceImpl implements RoleService {
    private Gson gson = new Gson();
    
    public RoleServiceImpl() {
        PathUtils.ensureFiles(
            new File(PathUtils.currentPath("data/roles.json"))
        );
    }
    
    @Override
    public boolean createRole(String name, List<Permission> permissions) {
        List<Role> roles = getAllRoles();
        Role role = new Role(name);
        
        if (roleExists(name)) {
            return false;
        }
        
        role.setId(getTotalRoles() + 1);
        role.setPermissions(permissions);
        roles.add(role);
        
        return true;
    }
    
    @Override
    public boolean createRole(String name) {
        return createRole(name, Collections.emptyList());
    }

    @Override
    public Role getRole(int id) {
        return getAllRoles()
            .stream()
            .filter(role -> role.getId() == id)
            .findFirst()
            .orElse(null);
    }

    @Override
    public Role getRole(String name) {
        return getAllRoles()
            .stream()
            .filter(role -> role.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public boolean roleExists(String name) {
        return getAllRoles().stream()
            .filter(role -> role.getName().equalsIgnoreCase(name))
            .count() > 0;
    }
    
    @Override
    public List<Role> getAllRoles() {
        String json = PathUtils.currentPath("data/roles.json");
        Role[] roles = gson.fromJson(json, Role[].class);
        
        return Arrays.asList(roles);
    }
    
    @Override
    public int getTotalRoles() {
        return getAllRoles().size();
    }
}
