package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Routed;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.service.IMedicService;
import ec.edu.espe.medicbyte.view.FrmMedics;
import ec.edu.espe.medicbyte.view.MainWindow;

/**
 *
 * @author Michael Cobacango
 */
public class MedicsController extends Controller {
    private final WindowsManager windowsManager;
    private final IMedicService medicService;
    private final MainWindow window;
    
    @Inject()
    public MedicsController(WindowsManager windowsManager, IMedicService medicService) {
        this.windowsManager = windowsManager;
        this.window = windowsManager.getAs(MainWindow.class);
        this.medicService = medicService;
        registerView(FrmMedics.class);
    }
    
    @Override
    public void init() {}
    
    @Routed("showAll")
    public void showAllMedics() {
        View view = getView(FrmMedics.class);
        view.set("medics", medicService.getAllMedics());
        window.display(view);
    }
}
