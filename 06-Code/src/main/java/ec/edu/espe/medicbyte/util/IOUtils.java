package ec.edu.espe.medicbyte.util;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Andres Jonathan J.
 */
public class IOUtils {
    public static String readFile(File file) {
        String content = new String();
        
        try {
            content = Files.asCharSource(file, StandardCharsets.UTF_8).read();
        } catch (IOException exception) {}
        
        return content;
    }
}
