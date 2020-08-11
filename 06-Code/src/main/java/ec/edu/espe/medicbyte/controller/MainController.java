package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.model.UserProfile;
import ec.edu.espe.medicbyte.view.MainWindow;
import ec.edu.espe.medicbyte.service.IAuthService;
import ec.edu.espe.medicbyte.service.IUserService;
import javax.swing.JOptionPane;
import jiconfont.icons.font_awesome.FontAwesome;

/**
 *
 * @author Andres Jonathan J.
 */
public class MainController extends Controller {
    private final Router router;
    private final IAuthService authService;
    private final IUserService userService;
    private final WindowsManager windowsManager;
    private MainWindow mainWindow;
    
    @Inject()
    public MainController(
        WindowsManager windowsManager,
        IAuthService authService,
        IUserService userService,
        Router router
    ) {
        this.windowsManager = windowsManager;
        this.userService = userService;
        this.router = router;
        this.authService = authService;
    }
    
    @Override
    protected void init() {
        if (!authService.isLoggedIn()) {
            if (mainWindow != null) {
                mainWindow.close();
            }
            router.run("auth");
            return;
        }
        //authService.login("admin", "admin");
        
        if (mainWindow == null) {
            mainWindow = windowsManager.getAs(MainWindow.class);
        } else {
            windowsManager.rebind(MainWindow.class);
            mainWindow = windowsManager.getAs(MainWindow.class);
        }
        
        setupMainWindow();
        
        if (!authService.getCurrentUser().hasRole("admin")) {
            mainWindow.selectMenuItem("appointments");
        } else {
            mainWindow.display(null);
            mainWindow.selectMenuItem("manageAppointments");
        }
        
        mainWindow.reveal();
    }
    
    public void setupMainWindow() {
        User user = authService.getCurrentUser();
        
        if (user.getProfile() == null) {
            UserProfile profile = userService.createEmptyUserProfile(user);
            user.setProfile(profile);
        }
        
        mainWindow.set("userContext", user);
        
        mainWindow.listen("logout", (args) -> {
            int confirm = JOptionPane.showConfirmDialog(
                mainWindow,
                "Estas seguro que deseas salir?",
                "Salir",
                JOptionPane.YES_NO_OPTION
            );
            
            mainWindow.emit("logoutResponse", confirm);
            
            if (confirm == 0) {
                mainWindow.clearMenuItems();
                mainWindow.display(null);
                mainWindow.close();
                router.run("auth");
            }
        });
        
        mainWindow.listen("editProfile", (args) -> {
            router.run("user", "editProfile").subscribe((e) -> {
                mainWindow.emit("finishLoadingEditProfileView");
            }).dispose();
        });
        
        if (user.hasRole("admin")) {
            mainWindow.addMenuItem(new MainWindow.MenuItem(
                "Manage appointments",
                FontAwesome.CALENDAR_CHECK_O,
                () -> {
                    router.run("appointments", "manage");
                    return true;
                }
            ).withKey("manageAppointments"));
            
            mainWindow.addMenuItem(new MainWindow.MenuItem(
                "Add medic",
                FontAwesome.USER_PLUS,
                () -> {
                    router.run("medics", "add");
                    return true;
                }
            ).withKey("addMedic"));
        } else {
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
    }
}
