package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Console;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.common.tui.ConsoleMenu;
import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.service.AppointmentService;
import ec.edu.espe.medicbyte.service.AuthService;
import ec.edu.espe.medicbyte.service.SpecialityService;
import ec.edu.espe.medicbyte.view.RequestAppointmentView;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import ec.edu.espe.medicbyte.common.core.Routed;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentsController extends Controller {
    @Inject private Console console;
    @Inject private ConsoleMenu menu;
    @Inject private AuthService authService;
    @Inject private AppointmentService appointmentService;
    @Inject private SpecialityService specialityService;
    
    @Override
    protected void init() {
        Callable<Boolean> isAdmin = () -> {
            return authService.isLoggedIn()
                && authService.getCurrentUser() != null
                && authService.getCurrentUser().hasRole("admin");
        };
        
        menu.addOption("create new appointment", this::createAppointment)
            .setEnabled(isAdmin);
        
        menu.addOption("reschedule appointment", this::rescheduleAppointment)
            .setEnabled(isAdmin);
        
        menu.addOption("view appointments list", this::viewAppointments)
            .setEnabled(isAdmin);
        
        menu.addOption("request an appointment", this::requestAppointment);
        menu.addOption("view scheduled appointments", this::viewScheduledAppointments);
        
        menu.addOption("Go to main menu", menu::exit, false);
        menu.display(" Appointments ");
        menu.reset();
    }
    
    public void requestAppointment() {
        View view = createView(RequestAppointmentView.class);
        
        view.set("specialities", specialityService.getAllSpecialities());
        
        view.listen("getAppointmentsBySpeciality", (arg) -> {
            Speciality speciality = (Speciality) arg;
            
            List<Appointment> appointments = appointmentService
                .getAllAppointments()
                .stream()
                .filter(appointment -> {
                    int specialityId = appointment.getMedic().getSpeciality().getId();
                    return specialityId == speciality.getId()
                        && !appointment.isTaken();
                }).collect(Collectors.toList());
            
            view.set("appointmentsBySpeciality", appointments);
        });
        
        view.display();
    }
    
    public void viewScheduledAppointments() {
        console.echoln("viewScheduledAppointments");
    }
    
    @Routed("create")
    public void createAppointment() {
        //View view = createView(CreateAppointmentView.class);
        //view.display();
        console.echoln("creating appointment");
    }
    
    public void rescheduleAppointment() {
        console.echoln("reschedule");
    }
    
    public void viewAppointments() {
        console.echoln("view appointments");
    }
}