package ec.edu.espe.medicbyte.util;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 *
 * @author Andres Jonathan J.
 */
public class PathUtils {
    public static Path currentPath() {
        try {
            return Paths.get(
                PathUtils.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
            ).getParent();
        } catch (URISyntaxException exception) {
            return Paths.get("").toAbsolutePath();
        }
    }
    
    public static Path currentPath(String... paths) {
        return Paths.get(currentPath().toString(), paths);
    }
    
    public static Path join(String path, String... paths) {
        return Paths.get(path, paths);
    }
    
    public static void ensureFiles(File... files) {
        Arrays.asList(files).stream()
            .filter(file -> (!file.exists()))
            .forEachOrdered(file -> {
                try {
                    Files.createParentDirs(file);
                    Files.touch(file);
                } catch (IOException exception) {}
            });
    }
    
    public static void ensureFiles(Path... paths) {
        File files[] = Arrays.asList(paths).stream()
            .map(path -> path.toFile())
            .toArray(File[]::new);
        
        ensureFiles(files);
    }
}
