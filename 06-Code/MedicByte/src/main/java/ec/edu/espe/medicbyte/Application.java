package ec.edu.espe.medicbyte;

import ec.edu.espe.medicbyte.common.core.Console;
import ec.edu.espe.medicbyte.common.core.Container;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.controller.AuthController;
import ec.edu.espe.medicbyte.util.menu.ConsoleMenu;
import ec.edu.espe.medicbyte.util.menu.ConsoleMenuOption;
import ec.edu.espe.medicbyte.common.service.AuthService;
import java.util.function.Consumer;

/**
 *
 * @author Andres Jonathan J.
 */
public class Application extends Container {
    public Application() {
        super(new MainModule());
    }
    
    public void initialize() {
        Router router = new Router(this);
        Console console = resolve(Console.class);
        ConsoleMenu menu = resolve(ConsoleMenu.class);
        AuthService auth = resolve(AuthService.class);
        
        // add all controllers to the router
        router.add(AuthController.class, "auth");
        
        /*/ ok, you are not allowed to view menu, so... login or register now
        while (!auth.isLoggedIn()) {
            router.run(AuthController.class);
            console
                .newLine(3)
                .echo("Press <enter> to continue...")
                .input();
        }*/
        
        // define menu options callback
        Consumer<ConsoleMenuOption> optionCallback = (option) -> {
            if (option.hasKey()) {
                router.run(option.getKey());
            }
        };
        
        // prepare menu options
        menu.addOption("login page", optionCallback).withKey("auth");
        menu.addOption("Exit", menu::exit, false);
        
        // show whole menu
        menu.display(" MedicByte v1.0 ");
    }
    
    public static void main(String[] args) {
        Application app = new Application();
        app.initialize();
    }
}
