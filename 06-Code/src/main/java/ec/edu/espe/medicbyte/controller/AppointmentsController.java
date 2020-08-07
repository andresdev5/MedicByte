package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Routed;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.service.IAppointmentService;
import ec.edu.espe.medicbyte.service.IAuthService;
import ec.edu.espe.medicbyte.service.ILocationService;
import ec.edu.espe.medicbyte.service.IMedicService;
import ec.edu.espe.medicbyte.service.IPatientService;
import ec.edu.espe.medicbyte.service.ISpecialityService;
import ec.edu.espe.medicbyte.view.ApproveAppointmentDialog.ApproveContext;
import ec.edu.espe.medicbyte.view.FrmAppointments;
import ec.edu.espe.medicbyte.view.FrmAppointmentsManager;
import ec.edu.espe.medicbyte.view.FrmRequestAppointment;
import ec.edu.espe.medicbyte.view.MainWindow;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentsController extends Controller {
    private final WindowsManager windowsManager;
    private final Router router;
    private final IMedicService medicService;
    private final ISpecialityService specialityService;
    private final IAppointmentService appointmentService;
    private final IAuthService authService;
    private final IPatientService patientService;
    private final ILocationService locationService;
    private AtomicBoolean addingAppointment = new AtomicBoolean(false);
    
    @Inject()
    public AppointmentsController(
        WindowsManager windowsManager, 
        Router router,
        IAuthService authService,
        IMedicService medicService,
        ISpecialityService specialityService,
        IAppointmentService appointmentService,
        IPatientService patientService,
        ILocationService locationService
    ) {
        this.windowsManager = windowsManager;
        this.router = router;
        this.authService = authService;
        this.medicService = medicService;
        this.specialityService = specialityService;
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.locationService = locationService;
        
        registerView(FrmAppointments.class);
        registerView(FrmRequestAppointment.class);
        registerView(FrmAppointmentsManager.class);
    }
    
    @Override
    public void init() {}
    
    @Routed("showAll")
    public void showAllAppointments() {
        MainWindow window = windowsManager.getAs(MainWindow.class);
        View view = getView(FrmAppointments.class);
        List<Appointment> appointments = appointmentService.getAllAppointments().stream()
            .filter(appointment -> appointment.getPatient().getId() == authService.getCurrentUser().getId())
            .collect(Collectors.toList());
        
        view.listen("cancelAppointment", (args) -> {
            Appointment appointment = args.get(0);
            appointment.setStatus(Appointment.Status.CANCELLED);
            
            System.out.println("cancelling...");
            
            window.setStatusBarContent("updating appointment...");
            appointmentService.updateAppointment(appointment);
            window.setStatusBarContent("Done!");
            
            view.set(
                "appointments", 
                appointmentService.getAllAppointments().stream()
                    .filter(a -> a.getPatient().getId() == authService.getCurrentUser().getId())
                    .collect(Collectors.toList())
            );
        });
        
        view.set("appointments", appointments);
        view.set("specialities", specialityService.getAllSpecialities());
        
        window.display(view);
    }
    
    @Routed("manage")
    public void manageAppointments() {
        MainWindow mainWindow = windowsManager.getAs(MainWindow.class);
        View view = getView(FrmAppointmentsManager.class);
        List<Appointment> requestedAppointments = appointmentService.getAllAppointments().stream()
            .filter(appointment -> appointment.getStatus() == Appointment.Status.PENDENT)
            .collect(Collectors.toList());
        
        view.listen("approvedAppointment", (args) -> {
            Appointment appointment = args.get(0);
            ApproveContext context = args.get(1);
            
            appointment.setDate(context.date);
            appointment.setHour(context.hour);
            appointment.setMedic(context.medic);
            appointment.setLocation(context.location);
            appointment.setStatus(Appointment.Status.APPROVED);
            
            mainWindow.setStatusBarContent("Updating appointment...");
            appointmentService.updateAppointment(appointment);
            mainWindow.setStatusBarContent("Done!");
            
            view.emit("updatedAppointment");
            view.set("requestedAppointments", appointmentService.getAllAppointments().stream()
                .filter(a -> a.getStatus() == Appointment.Status.PENDENT)
                .collect(Collectors.toList()));
        });
        
        view.listen("rejectedAppointment", (args) -> {
            Appointment appointment = args.get(0);
            appointment.setStatus(Appointment.Status.REJECTED);
            
            mainWindow.setStatusBarContent("Updating appointment...");
            appointmentService.updateAppointment(appointment);
            mainWindow.setStatusBarContent("Done!");
            
            view.emit("updatedAppointment");
            view.set("requestedAppointments", appointmentService.getAllAppointments().stream()
                .filter(a -> a.getStatus() == Appointment.Status.PENDENT)
                .collect(Collectors.toList()));
        });
        
        view.set("locations", locationService.getAllLocations());
        view.set("medics", medicService.getAllMedics());
        view.set("requestedAppointments", requestedAppointments);
        mainWindow.display(view);
    }
    
    @Routed("requestAppointment")
    public void requestAppointment() {
        View view = getView(FrmRequestAppointment.class);
        
        view.set("medics", medicService.getAllMedics());
        view.set("specialities", specialityService.getAllSpecialities());
        
        view.listen("submit", (args) -> {
            Appointment appointment = new Appointment();
            Speciality speciality = args.get(0);
            LocalDate date = args.get(1);
            String description = args.get(2);
            
            if (addingAppointment.get()) {
                return;
            }
            
            addingAppointment.set(true);
            
            appointment.setStatus(Appointment.Status.PENDENT);
            appointment.setDate(date);
            appointment.setDescription(description);
            appointment.setPatient(patientService.getPatient(authService.getCurrentUser().getId()));
            appointment.setSpeciality(speciality);
            
            boolean created = appointmentService.addAppointment(appointment);
            
            if (!created) {
                view.emit("showStatusMessage",
                    FrmRequestAppointment.StatusMessage.ERROR,
                    "Error while trying to request an appointment, please try again"
                );
            } else {
                view.emit("showStatusMessage",
                    FrmRequestAppointment.StatusMessage.SUCCESS,
                    "Appointment request successfully created!"
                );
                view.emit("success");
            }
            
            view.emit("submitComplete");
            addingAppointment.set(false);
        });
        
        windowsManager.getAs(MainWindow.class).display(view);
    }
}
