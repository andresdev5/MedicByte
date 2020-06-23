package ec.edu.espe.medicbyte.controller;

import ec.edu.espe.medicbyte.utils.ConsoleMenu;

/**
 *
 * @author Andres Jonathan J.
 */
public class HomeController {
    private AppointmentsController appointmentsController;
    private UsersController usersController;
    
    public HomeController() {
        appointmentsController = new AppointmentsController();
        usersController = new UsersController();
    }
    
    public void init() {
        ConsoleMenu menu = new ConsoleMenu();
        
        menu.prepend(" MedicByte v1.0 \n");
        menu.addOption("Admin menu", this::showAdminMenu);
        menu.addOption("Users menu", this::showUsersMenu);
        menu.addOption("Salir", menu::exit);
        menu.display();
    }
    
    public void showUsersMenu() {
        ConsoleMenu menu = new ConsoleMenu();
        
        menu.prepend(" MedicByte v1.0 \n");
        menu.addOption("solicitar cita", appointmentsController::takeAppointment);
        menu.addOption("Regresar al menu principal", menu::exit);
        menu.display();
    }
    
    public void showAdminMenu() {
        ConsoleMenu menu = new ConsoleMenu();
        
        menu.prepend(" MedicByte v1.0 \n");
        menu.addOption("crear cita", appointmentsController::createAppointment);
        menu.addOption("Eliminar cita", appointmentsController::deleteAppointment);
        menu.addOption("Mostrar Citas Medicas", appointmentsController::showAppointments);
        menu.addOption("Agregar medico", usersController::createMedic);
        menu.addOption("Mostrar Medicos", usersController::showMedics);
        //menu.addOption("Agregar especialidad", () -> {});
        menu.addOption("Regresar al menu principal", menu::exit);
        menu.display();
    }
}
