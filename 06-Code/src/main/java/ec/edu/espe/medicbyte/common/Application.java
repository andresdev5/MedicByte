package ec.edu.espe.medicbyte.common;

import com.formdev.flatlaf.FlatLightLaf;
import ec.edu.espe.medicbyte.common.core.Container;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.controller.AppointmentsController;
import ec.edu.espe.medicbyte.controller.AuthController;
import ec.edu.espe.medicbyte.controller.HomeController;
import ec.edu.espe.medicbyte.controller.MedicsController;
import ec.edu.espe.medicbyte.service.impl.AuthService;
import ec.edu.espe.medicbyte.view.AuthWindow;
import ec.edu.espe.medicbyte.view.MainWindow;
import javax.swing.UIManager;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

/**
 *
 * @author Andres Jonathan J
 */
public class Application extends Container {
    private Application(MainModule module) {
        super(module);
        module.setApplication(this);
    }
    
    public void initialize() {
        Router router = resolve(Router.class);
        AuthService authService = resolve(AuthService.class);
        WindowsManager windows = resolve(WindowsManager.class);
        
        FlatLightLaf.install();
        
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch(Exception ex) {
            System.err.println( "Failed to initialize LaF" );
        }
        
        IconFontSwing.register(FontAwesome.getIconFont());
        
        // register all controllers
        router.add(AuthController.class, "auth");
        router.add(AppointmentsController.class, "appointments");
        router.add(HomeController.class, "home");
        router.add(MedicsController.class, "medics");
        
        // register all windows
        windows.register(MainWindow.class);
        windows.register(AuthWindow.class);
        
        router.run("auth");
    }
    
    public void setMainWindowContext() {
        Router router = resolve(Router.class);
        AuthService authService = resolve(AuthService.class);
        MainWindow mainWindow = resolve(WindowsManager.class).getAs(MainWindow.class);
        
        mainWindow.listen("logout", (args) -> {
            authService.logout();
            mainWindow.clearMenuItems();
            mainWindow.dispose();
            router.run("auth");
        });
        
        if (authService.getCurrentUser().hasRole("admin")) {
            mainWindow.addMenuItem(new MainWindow.MenuItem(
                "Manage appointments",
                FontAwesome.CALENDAR_CHECK_O,
                () -> {
                    router.run("appointments", "manage");
                    return true;
                }
            ).withKey("manageAppointments"));
        }
        
        if (!authService.getCurrentUser().hasRole("admin")) {
            mainWindow.addMenuItem(new MainWindow.MenuItem(
                "Appointments",
                FontAwesome.CALENDAR_CHECK_O,
                () -> {
                    router.run("appointments", "showAll");
                    return true;
                }
            ).withKey("appointments"));
        
            mainWindow.addMenuItem(new MainWindow.MenuItem(
                "Request new appointment",
                FontAwesome.CALENDAR_PLUS_O,
                () -> {
                    router.run("appointments", "requestAppointment");
                    return true;
                }
            ).withKey("requestAppointment"));
        }
        
        mainWindow.addMenuItem(new MainWindow.MenuItem(
            "Medics",
            FontAwesome.USER_MD,
            () -> {
                router.run("medics", "showAll");
                return true;
            }
        ).withKey("medics"));
        
        mainWindow.reveal();
    }
    
    public static void main(String[] args) {
        Application app = new Application(new MainModule());
        app.initialize();
    }
}
