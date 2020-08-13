package ec.edu.espe.medicbyte.common;

import com.formdev.flatlaf.FlatLightLaf;
import ec.edu.espe.medicbyte.common.core.Container;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.controller.AppointmentsController;
import ec.edu.espe.medicbyte.controller.AuthController;
import ec.edu.espe.medicbyte.controller.MainController;
import ec.edu.espe.medicbyte.controller.MedicsController;
import ec.edu.espe.medicbyte.controller.PatientsController;
import ec.edu.espe.medicbyte.controller.UserController;
import ec.edu.espe.medicbyte.service.impl.AuthService;
import ec.edu.espe.medicbyte.view.AuthWindow;
import ec.edu.espe.medicbyte.view.MainWindow;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.icons.font_awesome.FontAwesomeSolid;
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
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch(Exception exception) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, exception);
        }
        
        IconFontSwing.register(FontAwesome.getIconFont());
        IconFontSwing.register(FontAwesomeSolid.getIconFont());
        
        // register all controllers
        router.add(AuthController.class, "auth");
        router.add(MainController.class, "main");
        router.add(AppointmentsController.class, "appointments");
        router.add(MedicsController.class, "medics");
        router.add(UserController.class, "user");
        router.add(PatientsController.class, "patients");
        
        // register all windows
        windows.register(MainWindow.class);
        windows.register(AuthWindow.class);
        
        router.run("main");
    }
    
    public static void main(String[] args) {
        Application app = new Application(new MainModule());
        app.initialize();
    }
}
