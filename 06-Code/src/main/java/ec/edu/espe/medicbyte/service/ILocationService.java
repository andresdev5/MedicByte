package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Location;
import java.util.List;

/**
 *
 * @author Michael Cobacango
 */
public interface ILocationService {
    List<Location> getAllLocations();
    Location getLocation(int id);
}
