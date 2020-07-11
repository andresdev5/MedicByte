package ec.edu.espe.medicbyte.common;

import ec.edu.espe.medicbyte.common.core.Console;
import ec.edu.espe.medicbyte.common.core.Container;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.service.AuthService;
import ec.edu.espe.medicbyte.controller.HomeController;

/**
 *
 * @author Andres Jonathan J
 */
public class Application extends Container {
    private Application(MainModule module) {
        super(module);
        module.setApplication(this);
    }
    
    public void initialize() {
        Router router = resolve(Router.class);
        Console console = resolve(Console.class);
        AuthService auth = resolve(AuthService.class);
        
        // add all controllers to the router
        router.add(HomeController.class, "home");
        
        // route to home controller
        router.run(HomeController.class);
        
        // that's all folks
        console.close();
    }
    
    public static void main(String[] args) {
        Application app = new Application(new MainModule());
        app.initialize();
    }
}
