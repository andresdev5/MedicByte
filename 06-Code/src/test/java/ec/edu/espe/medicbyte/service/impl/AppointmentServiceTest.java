package ec.edu.espe.medicbyte.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import ec.edu.espe.medicbyte.util.IOUtils;
import ec.edu.espe.medicbyte.util.PathUtils;
import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentServiceTest {
    
    @Test
    public void testDatabaseJsonExists() {
        assertTrue(Files.exists(PathUtils.currentPath("test-data/appointments.json")));
    }
    
    @Test
    public void testAppointmentsJsonData() {
        File jsonFile = PathUtils.currentPath("test-data/appointments.json").toFile();
        String json = IOUtils.readFile(jsonFile);
        JsonElement jsonElement = JsonParser.parseString(json);
        
        assertTrue(jsonElement.isJsonArray());
    }
}
