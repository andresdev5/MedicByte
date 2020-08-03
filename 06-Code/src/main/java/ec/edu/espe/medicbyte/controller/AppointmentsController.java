package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Routed;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.view.FrmAppointments;
import ec.edu.espe.medicbyte.view.MainWindow;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentsController extends Controller {
    private final WindowsManager windowsManager;
    private final Router router;
    
    @Inject()
    public AppointmentsController(WindowsManager windowsManager, Router router) {
        this.windowsManager = windowsManager;
        this.router = router;
        registerView(FrmAppointments.class);
    }
    
    @Override
    public void init() {}
    
    @Routed("appointmentsList")
    public void showAppointmentsList() {
        View view = getView(FrmAppointments.class);
        windowsManager.getAs(MainWindow.class).display(view);
    }
}
