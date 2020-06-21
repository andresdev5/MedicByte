package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Console;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.util.menu.ConsoleMenu;
import ec.edu.espe.medicbyte.view.CreateAppointmentView;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentController extends Controller {
    @Inject private Console console;
    @Inject private ConsoleMenu menu;
    
    @Override
    protected void init() {
        menu.addOption("create new appointment", this::createAppointment);
        menu.addOption("reschedule appointment", this::rescheduleAppointment);
        menu.addOption("view appointments list", this::viewAppointments);
        menu.addOption("Go to main menu", menu::exit, false);
        menu.display(" Appointments ");
        menu.reset();
    }
    
    public void createAppointment() {
        View view = createView(CreateAppointmentView.class);
        view.display();
    }
    
    public void rescheduleAppointment() {
        console.echoln("reschedule");
    }
    
    public void viewAppointments() {
        console.echoln("view appointments");
    }
}