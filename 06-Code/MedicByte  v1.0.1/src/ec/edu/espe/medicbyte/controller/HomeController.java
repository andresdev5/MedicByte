package ec.edu.espe.medicbyte.controller;

import ec.edu.espe.medicbyte.utils.ConsoleMenu;

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
        
        menu.prepend(" MedicByte v1.0.1 \n");
        menu.addOption("Admin Menu", this::showAdminMenu);
        menu.addOption("Users Menu", this::showUsersMenu);
        menu.addOption("Exit", menu::exit);
        menu.display();
    }
    
    public void showUsersMenu() {
        ConsoleMenu menu = new ConsoleMenu();
        
        menu.prepend(" MedicByte v1.0 \n");
        menu.addOption("Solicitar Cita", appointmentsController::takeAppointment);
        menu.addOption("Volver al menu principal", menu::exit);
        menu.display();
    }
    
    public void showAdminMenu() {
        ConsoleMenu menu = new ConsoleMenu();
        
        menu.prepend(" MedicByte v1.0 \n");
        menu.addOption("Create Appointment", appointmentsController::createAppointment);
        menu.addOption("Show Medical Appointments", appointmentsController::showAppointments);
        menu.addOption("Add Medic", usersController::createMedic);
        menu.addOption("Show Medics", usersController::showMedics);
        menu.addOption("Back To Main Menu", menu::exit);
        menu.display();
    }
}
