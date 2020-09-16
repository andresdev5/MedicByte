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
import ec.edu.espe.medicbyte.view.DlgApproveAppointment.ApproveContext;
import ec.edu.espe.medicbyte.view.FrmAppointments;
import ec.edu.espe.medicbyte.view.FrmAppointmentsManager;
import ec.edu.espe.medicbyte.view.FrmRequestAppointment;
import ec.edu.espe.medicbyte.view.MainWindow;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentsController extends Controller {
    ResourceBundle lang = ResourceBundle.getBundle("ec/edu/espe/medicbyte/view/Bundle");
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
    }
    
    @Override
    public void init() {}
    
    @Routed("showAll")
    public void showAllAppointments() {
        MainWindow window = windowsManager.getAs(MainWindow.class);
        View view = new FrmAppointments();
        List<Appointment> appointments = appointmentService
            .getPatientAppointments(authService.getCurrentUser().getId());
        
        view.listen("cancelAppointment", (args) -> {
            Appointment appointment = args.get(0);
            appointment.setStatus(Appointment.Status.CANCELLED);
            
            window.setStatusBarContent("updating appointment...");
            appointmentService.save(appointment);
            window.setStatusBarContent("Done!");
            
            view.set(
                "appointments", 
                appointmentService.getAll().stream()
                    .filter(a -> a.getPatient().getId() == authService.getCurrentUser().getId())
                    .collect(Collectors.toList())
            );
        });
        
        view.set("appointments", appointments);
        view.set("specialities", specialityService.getAll());
        
        window.display(view);
    }
    
    @Routed("manage")
    public void manageAppointments() {
        MainWindow mainWindow = windowsManager.getAs(MainWindow.class);
        View view = new FrmAppointmentsManager();
        List<Appointment> requestedAppointments = appointmentService.getAll().stream()
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
            appointmentService.update(appointment);
            mainWindow.setStatusBarContent("Done!");
            
            view.emit("updatedAppointment");
            view.set("requestedAppointments", appointmentService.getAll().stream()
                .filter(a -> a.getStatus() == Appointment.Status.PENDENT)
                .collect(Collectors.toList()));
        });
        
        view.listen("rejectedAppointment", (args) -> {
            Appointment appointment = args.get(0);
            appointment.setStatus(Appointment.Status.REJECTED);
            
            mainWindow.setStatusBarContent("Updating appointment...");
            appointmentService.update(appointment);
            mainWindow.setStatusBarContent("Done!");
            
            view.emit("updatedAppointment");
            view.set("requestedAppointments", appointmentService.getAll().stream()
                .filter(a -> a.getStatus() == Appointment.Status.PENDENT)
                .collect(Collectors.toList()));
        });
        
        view.set("locations", locationService.getAll());
        view.set("medics", medicService.getAll());
        view.set("requestedAppointments", requestedAppointments);
        mainWindow.display(view);
    }
    
    @Routed("requestAppointment")
    public void requestAppointment() {
        View view = new FrmRequestAppointment();
        
        view.set("medics", medicService.getAll());
        view.set("specialities", specialityService.getAll());
        
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
            appointment.setPatient(patientService.get(authService.getCurrentUser().getId()));
            appointment.setSpeciality(speciality);
            
            boolean created = appointmentService.save(appointment);
            
            if (!created) {
                view.emit("showStatusMessage",
                    FrmRequestAppointment.StatusMessage.ERROR,
                    lang.getString("error_requesting_appointment")
                );
            } else {
                view.emit("showStatusMessage",
                    FrmRequestAppointment.StatusMessage.SUCCESS,
                    lang.getString("apppointment_successfully_created")
                );
                view.emit("success");
            }
            
            view.emit("submitComplete");
            addingAppointment.set(false);
        });
        
        windowsManager.getAs(MainWindow.class).display(view);
    }
}
