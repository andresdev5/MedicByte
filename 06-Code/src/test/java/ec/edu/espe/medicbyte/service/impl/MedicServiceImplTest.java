package ec.edu.espe.medicbyte.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.MedicService;
import ec.edu.espe.medicbyte.service.RoleService;
import ec.edu.espe.medicbyte.service.SpecialityService;
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
public class MedicServiceImplTest {
    @Test
    public void testMedicsJsonExists() {
        assertTrue(PathUtils.currentPath("data/medics.json").toFile().exists());
    }
    
    @Test
    public void testMedicsValidJson() {
        File rolesJsonFile = PathUtils.currentPath("data/users.json").toFile();
        JsonElement parser = JsonParser.parseString(IOUtils.readFile(rolesJsonFile));
        
        assertTrue(parser.isJsonArray());
    }
    
    @Test
    public void testMedicsListNotEmpty() {
        SpecialityService specialityService = new SpecialityServiceImpl();
        RoleService roleService = new RoleServiceImpl();
        UserService userService = new UserServiceImpl(roleService);
        MedicService instance = new MedicServiceImpl(userService, specialityService);
        List<Medic> actual = instance.getAllMedics();
        
        assertFalse(actual.isEmpty());
    }
    
    @Test
    public void testAddMedic() {
        SpecialityService specialityService = new SpecialityServiceImpl();
        RoleService roleService = new RoleServiceImpl();
        UserService userService = new UserServiceImpl(roleService);
        MedicService instance = new MedicServiceImpl(userService, specialityService);
        
        String username = "mockup_medic_username";
        String fullName = "mockup_medic_fullName";
        String salt = BCrypt.gensalt();
        String password = BCrypt.hashpw("mockup_medic_password", salt);
        
        userService.createUser(username, password, roleService.getRole("medic"));
        User user = userService.getUser("mockup_medic_username");
        
        instance.addMedic(user, specialityService.getSpeciality(1), fullName);
        
        User foundUser = userService.getUser("mockup_medic_username");
        Medic medic = instance.getMedic(foundUser.getId());
        String actual = medic.getUsername();
        String expected = username;
        
        assertEquals(actual, expected);
    }
}
