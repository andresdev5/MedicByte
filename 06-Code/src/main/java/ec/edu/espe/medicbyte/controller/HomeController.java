package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.service.AuthService;
import ec.edu.espe.medicbyte.view.MainWindow;
import jiconfont.icons.font_awesome.FontAwesome;

/**
 *
 * @author Andres Jonathan J.
 */
public class HomeController extends Controller {
    private final Router router;
    private final AuthService authService;
    private final WindowsManager windowsManager;
    private final MainWindow mainWindow;
    
    @Inject()
    public HomeController(WindowsManager windowsManager, AuthService authService, Router router) {
        this.windowsManager = windowsManager;
        this.router = router;
        this.authService = authService;
        this.mainWindow = windowsManager.getAs(MainWindow.class);
    }
    
    @Override
    protected void init() {
        mainWindow.addMenuItem(new MainWindow.MenuItem(
            "Request new appointment",
            FontAwesome.CALENDAR_PLUS_O,
            () -> false
        ));
        
        mainWindow.addMenuItem(new MainWindow.MenuItem(
            "Appointments",
            FontAwesome.CALENDAR_CHECK_O,
            () -> {
                router.run("appointments", "appointmentsList");
                return true;
            }
        ));
        
        mainWindow.addMenuItem(new MainWindow.MenuItem(
            "Medics",
            FontAwesome.USER_MD,
            () -> false
        ));
        
        mainWindow.addMenuItem(new MainWindow.MenuItem(
            "Invoices",
            FontAwesome.MONEY,
            () -> false
        ));
    }
}
