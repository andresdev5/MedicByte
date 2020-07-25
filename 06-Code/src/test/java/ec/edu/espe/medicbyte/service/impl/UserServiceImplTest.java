package ec.edu.espe.medicbyte.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.RoleService;
import ec.edu.espe.medicbyte.service.UserService;
import ec.edu.espe.medicbyte.util.IOUtils;
import ec.edu.espe.medicbyte.util.PathUtils;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andres Jonathan J.
 */
public class UserServiceImplTest {
    @Test
    public void testUsersJsonExists() {
        assertTrue(PathUtils.currentPath("data/users.json").toFile().exists());
    }
    
    @Test
    public void testUsersValidJson() {
        File rolesJsonFile = PathUtils.currentPath("data/users.json").toFile();
        JsonElement parser = JsonParser.parseString(IOUtils.readFile(rolesJsonFile));
        
        assertTrue(parser.isJsonArray());
    }
    
    @Test
    public void testUsersListNotEmpty() {
        UserService instance = new UserServiceImpl(new RoleServiceImpl());
        List<User> actual = instance.getAllUsers();
        
        assertFalse(actual.isEmpty());
    }
    
    @Test
    public void testCreateUser() {
        RoleService roleService = new RoleServiceImpl();
        UserService instance = new UserServiceImpl(roleService);
        
        String username = "dummy_user";
        String password = "dummy_password";
        String salt = BCrypt.gensalt();
        Role role = roleService.getRole("admin");
        
        password = BCrypt.hashpw(password, salt);
        
        assertTrue(instance.createUser(username, password, role));
        assertTrue(instance.userExists("dummy_user"));
    }
}
