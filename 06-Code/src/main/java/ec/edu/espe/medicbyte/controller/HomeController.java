package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.service.UserService;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.util.menu.ConsoleMenu;

/**
 *
 * @author Andres Jonathan J.
 */
public class HomeController extends Controller {
    @Inject private Router router;
    @Inject private ConsoleMenu menu;
    @Inject private UserService userService;
    
    @Override
    protected void init() {
        User user = userService.getCurrentUser();
        
        menu.addOption("Appointments menu", () -> {
            router.run("appointment");
        }, false).setEnabled(true);
        
        menu.addOption("Exit", menu::exit, false);
        menu.display(" MedicByte v1.0 ");
        menu.reset();
    }
}
