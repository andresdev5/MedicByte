package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.service.AuthService;
import ec.edu.espe.medicbyte.common.tui.ConsoleMenu;
import java.util.concurrent.Callable;

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
        Callable<Boolean> isAdmin = () -> {
            return authService.isLoggedIn()
                && authService.getCurrentUser() != null
                && authService.getCurrentUser().hasRole("admin");
        };
        
        menu.addOption("Admin", this::showAdminMenu).setEnabled(true);
        
        menu.addOption("Appointments", () -> {
            router.run("appointments");
        }, false);
        
        menu.addOption("Show medics list", () -> {
            router.run("medics", "listAll");
        }, false);
        
        menu.addOption("Login", () -> {
            authService.logout();
            router.run("auth");
        }, false).setEnabled(() -> !authService.isLoggedIn());
        
        menu.addOption("Logout", () -> {
            authService.logout();
            router.run("auth");
        }, false).setEnabled(authService::isLoggedIn);
        menu.addOption("Exit", menu::exit, false);
        menu.display(" MedicByte ");
    }
    
    public void showAdminMenu() {
        ConsoleMenu menu = new ConsoleMenu();
        menu.addOption("Register an appointment", () -> {
            router.run("appointments", "create");
        }, false);
        menu.addOption("Register a new medic", () -> {
            router.run("medics", "create");
        }, false);
        menu.addOption("Back to main menu", menu::exit, false);
        menu.display(" MedicByte / Admin ");
    }
}
