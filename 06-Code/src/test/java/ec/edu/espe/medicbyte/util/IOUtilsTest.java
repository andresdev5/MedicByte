package ec.edu.espe.medicbyte.util;

import java.io.File;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andres Jonathan J.
 */
public class IOUtilsTest {
    /**
     * Test of readFile method, of class IOUtils.
     */
    @Test
    public void testReadFile() {
        File file = new File("test-data/empty.txt");
        assertTrue(IOUtils.readFile(file) != null);
    }
}
