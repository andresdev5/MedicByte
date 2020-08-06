package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Location;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public interface ILocationService {
    List<Location> getAllLocations();
    Location getLocation(int id);
}
