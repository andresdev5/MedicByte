package ec.edu.espe.medicbyte.controller;

import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.AuthService;
import ec.edu.espe.medicbyte.service.impl.AuthServiceImpl;
import ec.edu.espe.medicbyte.util.Console;
import ec.edu.espe.medicbyte.util.ConsoleMenu;

/**
 *
 * @author Andres Jonathan J.
 */
public class HomeController {
    private final AppointmentsController appointmentsController;
    private final MedicsController usersController;
    private final Console console;
    private final AuthService authService;
    
    public HomeController() {
        appointmentsController = new AppointmentsController();
        usersController = new MedicsController();
        this.console = Console.getInstance();
        this.authService = new AuthServiceImpl();
    }
    
    public void init() {
        ConsoleMenu menu = new ConsoleMenu();
        
        menu.addOption("Admin Menu", this::showAdminMenu, false);
        menu.addOption("Users Menu", this::showUsersMenu, false);
        menu.addOption("Logout", authService::logout, false)
            .setEnabled(authService::isLoggedIn);
        menu.addOption("Exit", menu::exit, false);
        menu.display(" MedicByte ");
    }
    
    public void showUsersMenu() {
        ConsoleMenu menu = new ConsoleMenu();
        
        menu.addOption("Solicitar cita", appointmentsController::takeAppointment);
        menu.addOption("ver citas", appointmentsController::showScheduledAppointments);
        menu.addOption("Volver al menu principal", menu::exit, false);
        menu.display(" MedicByte / users ");
    }
    
    public void showAdminMenu() {
        ConsoleMenu menu = new ConsoleMenu();

        while (!authService.isLoggedIn()) {
            String username = console.input("Enter username: ");
            String password = console.mask().input("Enter password: ");
            boolean loggedIn = authService.login(username, password);

            if (!loggedIn) {
                console.newLine().echoln("invalid credentials");

                if (!Console.confirm("retry again?")) {
                    return;
                }
            }
        }

        User user = authService.getCurrentUser();
        
        if (user == null || !user.hasRole("admin")) {
            console.newLine().echoln("You are not allowed to access");
            console.pause();
            return;
        }
        
        menu.addOption("Show appointments list", appointmentsController::showAppointments);
        menu.addOption("Create an appointment", appointmentsController::createAppointment);
        menu.addOption("Delete an appointment", appointmentsController::deleteAppointment);
        menu.addOption("Add new medic", usersController::createMedic);
        menu.addOption("Show medics list", usersController::showMedics);
        menu.addOption("Back to main menu", menu::exit, false);
        menu.display(" MedicByte / admin ");
    }
}
