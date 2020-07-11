package ec.edu.espe.medicbyte.util;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Andres Jonathan J.
 */
public class PathUtils {
    public static String currentPath() {
        try {
        String path = new File(
            PathUtils.class.getProtectionDomain()
            .getCodeSource()
            .getLocation()
            .toURI()
        ).getPath();
        
        return URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException | URISyntaxException exception) {
            return Paths.get("")
                .toAbsolutePath()
                .toString();
        }
    }
    
    public static String currentPath(String first, String... more) {
        return join(first, more).toString();
    }
    
    public static Path join(String first, String... more) {
        return Paths.get(first, more);
    }
    
    public static void ensureFiles(File... files) {
        Arrays.asList(files).stream()
            .filter(file -> (!file.exists()))
            .forEachOrdered(file -> {
                try {
                    Files.touch(file);
                } catch (IOException exception) {}
            });
    }
}
