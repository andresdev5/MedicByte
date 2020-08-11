package ec.edu.espe.medicbyte.service.impl;

import com.google.gson.Gson;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.util.IOUtils;
import ec.edu.espe.medicbyte.util.PathUtils;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andres Jonathan J.
 */
public class SpecialityServiceTest {
    private final Gson gson = new Gson();
    
    @Test
    public void testDatabaseJsonExists() {
        assertTrue(Files.exists(PathUtils.currentPath("test-data/specialities.json")));
    }

    @Test
    public void testGetAllSpecialities() {
        File jsonFile = PathUtils.currentPath("test-data/specialities.json").toFile();
        String json = IOUtils.readFile(jsonFile);
        
        Speciality[] specialities = gson.fromJson(json, Speciality[].class);
        
        assertNotNull(specialities);
    }
    
    @Test
    public void testGetSpeciality() {
        File jsonFile = PathUtils.currentPath("test-data/specialities.json").toFile();
        String json = IOUtils.readFile(jsonFile);
        int expectedId = 1;
        
        Speciality[] specialities = gson.fromJson(json, Speciality[].class);
        
        boolean exists = Arrays.asList(specialities).stream().anyMatch(speciality -> {
            return speciality.getId() == expectedId;
        });
        
        assertTrue(exists);
    }
}
