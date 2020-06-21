package ec.edu.espe.medicbyte;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import ec.edu.espe.medicbyte.common.core.Console;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.service.AuthService;
import ec.edu.espe.medicbyte.common.service.UserService;
import ec.edu.espe.medicbyte.util.menu.ConsoleMenu;

/**
 *
 * @author Andres Jonathan J.
 */
public class MainModule extends AbstractModule {
    private Application application;
    
    public void setApplication(Application application) {
        this.application = application;
    }
    
    @Provides
    Application provideApplication() {
        return application;
    }
    
    @Override
    protected void configure() {
        this.bind(Router.class).in(Singleton.class);
        this.bind(Console.class).in(Singleton.class);
        this.bind(UserService.class).in(Singleton.class);
        this.bind(AuthService.class).in(Singleton.class);
    }
}
