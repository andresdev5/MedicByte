package ec.edu.espe.medicbyte.service.impl;

import com.google.gson.Gson;
import ec.edu.espe.medicbyte.model.Location;
import ec.edu.espe.medicbyte.service.ILocationService;
import ec.edu.espe.medicbyte.util.IOUtils;
import ec.edu.espe.medicbyte.util.PathUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class LocationService implements ILocationService {
    private final Gson gson = new Gson();
    
    public LocationService() {
        PathUtils.ensureFiles(PathUtils.currentPath("data/locations.json"));
    }
    
    @Override
    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        File jsonFile = PathUtils.currentPath("data/locations.json").toFile();
        String json = IOUtils.readFile(jsonFile);
        
        if (json == null) {
            return locations;
        }
        
        Location[] locationsArray = gson.fromJson(json, Location[].class);
        locations.addAll(Arrays.asList(locationsArray));
        
        return locations;
    }
    
    @Override
    public Location getLocation(int id) {
        return getAllLocations().stream()
            .filter(location -> location.getId() == id)
            .findFirst()
            .orElse(null);
    }
}
