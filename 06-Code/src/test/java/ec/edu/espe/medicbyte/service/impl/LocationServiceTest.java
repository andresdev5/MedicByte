package ec.edu.espe.medicbyte.service.impl;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import ec.edu.espe.medicbyte.common.core.Config;
import ec.edu.espe.medicbyte.common.core.DatabaseManager;
import ec.edu.espe.medicbyte.model.Location;
import ec.edu.espe.medicbyte.service.ILocationService;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andres Jonathan J.
 */
public class LocationServiceTest {
    private final Injector injector;
    private Config config = Config.getInstance();
    private ILocationService service;
    
    public LocationServiceTest() {
        try {
            DatabaseManager.DatabaseContext dbcontext = new DatabaseManager.DatabaseContext(
                "ec.edu.espe.medicbyte.model",
                config.get("dbname", String.class),
                config.get("dbhost", String.class),
                config.get("dbport", Integer.class)
            );
            injector = Guice.createInjector((Module) (Binder binder) -> {
                binder.bind(DatabaseManager.class).toInstance(new DatabaseManager(dbcontext));
                binder.bind(ILocationService.class).to(LocationService.class).in(Singleton.class);
            });

            service = injector.getInstance(ILocationService.class);
        } catch (Exception ex) {
            throw new RuntimeException("Error initializing SpecialityServiceTest", ex);
        }
    }

    @Test
    public void testNotEmptyLocations() {
        List<Location> locations = service.getAll();
        assertFalse(locations.isEmpty());
    }
}
