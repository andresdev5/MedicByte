package ec.edu.espe.medicbyte.service.impl;

import com.google.common.io.Files;
import com.google.gson.Gson;
import ec.edu.espe.medicbyte.model.Permission;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.util.IOUtils;
import ec.edu.espe.medicbyte.util.PathUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import ec.edu.espe.medicbyte.service.IRoleService;
import ec.edu.espe.medicbyte.service.IRoleService;

/**
 *
 * @author Andres Jonathan J.
 */
public class RoleService implements IRoleService {
    private Gson gson = new Gson();
    
    public RoleService() {
        PathUtils.ensureFiles(
            PathUtils.currentPath("data/roles.json")
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
        
        String content = gson.toJson(roles.toArray());
        File jsonFile = PathUtils.currentPath("data/roles.json").toFile();
        
        try {
            Files.write(content.getBytes(), jsonFile);
        } catch (IOException exception) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean createRole(String name) {
        return createRole(name, new ArrayList<>());
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
        File jsonFile = PathUtils.currentPath("data/roles.json").toFile();
        String json = IOUtils.readFile(jsonFile);
        Role[] roles = gson.fromJson(json, Role[].class);
        
        if (roles == null) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(Arrays.asList(roles));
    }
    
    @Override
    public int getTotalRoles() {
        return getAllRoles().size();
    }
}
