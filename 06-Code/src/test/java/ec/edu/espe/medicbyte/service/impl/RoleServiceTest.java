package ec.edu.espe.medicbyte.service.impl;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import ec.edu.espe.medicbyte.common.core.Config;
import ec.edu.espe.medicbyte.common.core.DatabaseManager;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.service.IRoleService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 *
 * @author Andres Jonathan J.
 */
public class RoleServiceTest {
    private final Injector injector;
    private Config config = Config.getInstance();
    private IRoleService service;
    
    public RoleServiceTest() {
        try {
            DatabaseManager.DatabaseContext dbcontext = new DatabaseManager.DatabaseContext(
                "ec.edu.espe.medicbyte.model",
                config.get("dbname", String.class),
                config.get("dbhost", String.class),
                config.get("dbport", Integer.class)
            );
            injector = Guice.createInjector((Module) (Binder binder) -> {
                binder.bind(DatabaseManager.class).toInstance(new DatabaseManager(dbcontext));
                binder.bind(IRoleService.class).to(RoleService.class).in(Singleton.class);
            });

            service = injector.getInstance(IRoleService.class);
        } catch (Exception ex) {
            throw new RuntimeException("Error initializing RoleServiceTest", ex);
        }
    }

    /**
     * Test of get method, of class RoleService.
     */
    @Test
    public void testGet() {
        Role role = service.get("admin");
        assertNotNull(role);
    }

    /**
     * Test of exists method, of class RoleService.
     */
    @Test
    public void testExists() {
        boolean exists = service.exists("admin");
        assertTrue(exists);
    }
}
