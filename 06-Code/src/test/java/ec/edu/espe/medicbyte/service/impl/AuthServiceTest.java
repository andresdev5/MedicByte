package ec.edu.espe.medicbyte.service.impl;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import ec.edu.espe.medicbyte.common.core.Config;
import ec.edu.espe.medicbyte.common.core.DatabaseManager;
import ec.edu.espe.medicbyte.service.IAuthService;
import ec.edu.espe.medicbyte.service.IUserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 *
 * @author Andres Jonathan J.
 */
public class AuthServiceTest {
    private final Injector injector;
    private Config config = Config.getInstance();
    private IAuthService service;
    
    public AuthServiceTest() {
        try {
            DatabaseManager.DatabaseContext dbcontext = new DatabaseManager.DatabaseContext(
                "ec.edu.espe.medicbyte.model",
                config.get("dbname", String.class),
                config.get("dbhost", String.class),
                config.get("dbport", Integer.class)
            );
            injector = Guice.createInjector((Module) (Binder binder) -> {
                binder.bind(DatabaseManager.class).toInstance(new DatabaseManager(dbcontext));
                binder.bind(IUserService.class).to(UserService.class).in(Singleton.class);
                binder.bind(IAuthService.class).to(AuthService.class).in(Singleton.class);
            });

            service = injector.getInstance(IAuthService.class);
        } catch (Exception ex) {
            throw new RuntimeException("Error initializing SpecialityServiceTest", ex);
        }
    }

    /**
     * Test of login method, of class AuthService.
     */
    @Test
    public void testLogin() {
        assertTrue(service.login("admin", "admin"));
    }

    /**
     * Test of getCurrentUser method, of class AuthService.
     */
    @Test
    public void testGetCurrentUser() {
        service.login("admin", "admin");
        assertNotNull(service.getCurrentUser());
    }

    /**
     * Test of isLoggedIn method, of class AuthService.
     */
    @Test
    public void testIsLoggedIn() {
        service.login("admin", "admin");
        assertTrue(service.isLoggedIn());
    }
}
