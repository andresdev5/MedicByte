package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.service.AuthService;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.util.menu.ConsoleMenu;

/**
 *
 * @author Andres Jonathan J.
 */
public class HomeController extends Controller {
    @Inject private Router router;
    @Inject private AuthService authService;
    
    @Override
    protected void init() {
        ConsoleMenu menu = new ConsoleMenu();
        
        menu.addOption("create new user", () -> {
            router.run("auth", "register");
        }, false).setEnabled(() -> {
            User user = authService.getCurrentUser();
            
            if (!authService.isLoggedIn()) {
                return false;
            }
            
            return user.hasRole("admin");
        });
        
        menu.addOption("Logout", () -> {
            authService.logout();
            router.run("auth");
        }, false).setEnabled(authService::isLoggedIn);
        
        menu.addOption("Exit", menu::exit, false);
        menu.display(" MedicByte ");
    }
}
