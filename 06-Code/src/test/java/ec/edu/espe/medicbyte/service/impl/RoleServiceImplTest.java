package ec.edu.espe.medicbyte.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.service.RoleService;
import ec.edu.espe.medicbyte.util.IOUtils;
import ec.edu.espe.medicbyte.util.PathUtils;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andres Jonathan J.
 */
public class RoleServiceImplTest {
    @Test
    public void testRolesJsonExists() {
        assertTrue(PathUtils.currentPath("data/roles.json").toFile().exists());
    }
    
    @Test
    public void testRolesValidJson() {
        File rolesJsonFile = PathUtils.currentPath("data/roles.json").toFile();
        JsonElement parser = JsonParser.parseString(IOUtils.readFile(rolesJsonFile));
        
        assertTrue(parser.isJsonArray());
    }
    
    @Test
    public void testRolesListNotEmpty() {
        RoleService instance = new RoleServiceImpl();
        List<Role> actual = instance.getAllRoles();
        
        assertFalse(actual.isEmpty());
    }
    
    @Test
    public void testaddRole() {
        RoleService instance = new RoleServiceImpl();
        
        assertTrue(instance.createRole("Moderator"));
        assertTrue(instance.roleExists("Moderator"));
    }
    
    @Test
    public void testRoleAttributes() {
        RoleService instance = new RoleServiceImpl();
        Role actual = instance.getRole(1);
        
        assertNotNull(actual.getId());
        assertNotNull(actual.getName());
    }
    
    @Test void testTotalRoles() {
        RoleService instance = new RoleServiceImpl();
        File rolesJsonFile = PathUtils.currentPath("data/roles.json").toFile();
        JsonElement parser = JsonParser.parseString(IOUtils.readFile(rolesJsonFile));
        
        int expected = parser.getAsJsonArray().size();
        int actual = instance.getTotalRoles();
        
        assertEquals(expected, actual);
    }
}
