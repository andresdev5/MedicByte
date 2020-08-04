package ec.edu.espe.medicbyte.common;

import ec.edu.espe.medicbyte.common.core.Container;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.controller.AppointmentsController;
import ec.edu.espe.medicbyte.controller.AuthController;
import ec.edu.espe.medicbyte.controller.HomeController;
import ec.edu.espe.medicbyte.view.AuthWindow;
import ec.edu.espe.medicbyte.view.MainWindow;
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
        WindowsManager windows = resolve(WindowsManager.class);
        
        IconFontSwing.register(FontAwesome.getIconFont());
        
        // register all controllers
        router.add(AuthController.class, "auth");
        router.add(AppointmentsController.class, "appointments");
        router.add(HomeController.class, "home");
        
        // register all windows
        windows.register(MainWindow.class);
        windows.register(AuthWindow.class);
        
        router.run("auth");
    }
    
    public static void main(String[] args) {
        Application app = new Application(new MainModule());
        app.initialize();
    }
}
