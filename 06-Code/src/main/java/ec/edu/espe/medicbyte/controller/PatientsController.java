package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.impl.AuthService;
import ec.edu.espe.medicbyte.service.impl.PatientService;
import ec.edu.espe.medicbyte.view.FrmPatients;
import ec.edu.espe.medicbyte.view.MainWindow;

/**
 *
 * @author Junior Jurado
 */
public class PatientsController extends Controller {
    @Inject() private WindowsManager windowsManager;
    @Inject() private Router router;
    @Inject() private AuthService authService;
    @Inject() private PatientService patientService;
    
    @Override
    public void init() {
        MainWindow mainWindow = windowsManager.getAs(MainWindow.class);
        FrmPatients view = new FrmPatients();
        User user = authService.getCurrentUser();
        
        view.set("currentUser", authService.getCurrentUser());
        view.set("patients", patientService.getAllPatients());
        mainWindow.display(view);
    }
}
