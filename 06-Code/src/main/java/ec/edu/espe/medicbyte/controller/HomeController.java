package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.view.MainWindow;
import ec.edu.espe.medicbyte.service.IAuthService;

/**
 *
 * @author Andres Jonathan J.
 */
public class HomeController extends Controller {
    private final Router router;
    private final IAuthService authService;
    private final WindowsManager windowsManager;
    private final MainWindow mainWindow;
    
    @Inject()
    public HomeController(WindowsManager windowsManager, IAuthService authService, Router router) {
        this.windowsManager = windowsManager;
        this.router = router;
        this.authService = authService;
        this.mainWindow = windowsManager.getAs(MainWindow.class);
    }
    
    @Override
    protected void init() {
        if (!authService.isLoggedIn()) {
            mainWindow.dispose();
            router.run("auth");
            return;
        }
        
        mainWindow.selectMenuItem("appointments");
    }
}
