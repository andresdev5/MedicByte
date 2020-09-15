package ec.edu.espe.medicbyte.service.impl;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import ec.edu.espe.medicbyte.common.core.Config;
import ec.edu.espe.medicbyte.common.core.DatabaseManager;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.service.ISpecialityService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andres Jonathan J.
 */
public class SpecialityServiceTest {
    private final Injector injector;
    private Config config = Config.getInstance();
    private ISpecialityService service;
    
    public SpecialityServiceTest() {
        try {
            DatabaseManager.DatabaseContext dbcontext = new DatabaseManager.DatabaseContext(
                "ec.edu.espe.medicbyte.model",
                config.get("dbname", String.class),
                config.get("dbhost", String.class),
                config.get("dbport", Integer.class)
            );
            injector = Guice.createInjector((Module) (Binder binder) -> {
                binder.bind(DatabaseManager.class).toInstance(new DatabaseManager(dbcontext));
                binder.bind(ISpecialityService.class).to(SpecialityService.class).in(Singleton.class);
            });

            service = injector.getInstance(ISpecialityService.class);
        } catch (Exception ex) {
            throw new RuntimeException("Error initializing SpecialityServiceTest", ex);
        }
    }

    /**
     * Test of get method, of class SpecialityService.
     */
    @Test
    public void testGet() {
        Speciality speciality = service.get("pediatric");
        assertNotNull(speciality);
    }
}
