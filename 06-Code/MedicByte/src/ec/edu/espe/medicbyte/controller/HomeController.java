package ec.edu.espe.medicbyte.controller;

import ec.edu.espe.medicbyte.util.ConsoleMenu;

/**
 *
 * @author Andres Jonathan J.
 */
public class HomeController {
    private final AppointmentsController appointmentsController;
    private final MedicsController usersController;
    
    public HomeController() {
        appointmentsController = new AppointmentsController();
        usersController = new MedicsController();
    }
    
    public void init() {
        ConsoleMenu menu = new ConsoleMenu();
        
        menu.addOption("Admin Menu", this::showAdminMenu, false);
        menu.addOption("Users Menu", this::showUsersMenu, false);
        menu.addOption("Exit", menu::exit, false);
        menu.display(" MedicByte v1.0.1 ");
    }
    
    public void showUsersMenu() {
        ConsoleMenu menu = new ConsoleMenu();
        
        menu.addOption("Solicitar Cita", appointmentsController::takeAppointment);
        menu.addOption("Volver al menu principal", menu::exit, false);
        menu.display(" MedicByte ");
    }
    
    public void showAdminMenu() {
        ConsoleMenu menu = new ConsoleMenu();
        
        menu.addOption("Show appointments list", appointmentsController::showAppointments);
        menu.addOption("Create an appointment", appointmentsController::createAppointment);
        menu.addOption("Delete an appointment", appointmentsController::deleteAppointment);
        menu.addOption("Add new medic", usersController::createMedic);
        menu.addOption("Show medics list", usersController::showMedics);
        menu.addOption("Back to main menu", menu::exit, false);
        menu.display(" MedicByte - Admin ");
    }
}
