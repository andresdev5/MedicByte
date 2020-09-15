package ec.edu.espe.medicbyte.util;

import java.io.File;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andres Jonathan J.
 */
public class PathUtilsTest {
    /**
     * Test of ensureFiles method, of class PathUtils.
     */
    @Test
    public void testEnsureFiles() {
        File file = PathUtils.currentPath("test.txt").toFile();
        PathUtils.ensureFiles(file);
        assertTrue(file.exists());
    }
}
