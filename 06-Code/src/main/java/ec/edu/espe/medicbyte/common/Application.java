package ec.edu.espe.medicbyte.common;

import ec.edu.espe.medicbyte.common.core.Container;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.controller.AppointmentsController;
import ec.edu.espe.medicbyte.controller.HomeController;
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
        IconFontSwing.register(FontAwesome.getIconFont());
        
        WindowsManager windows = resolve(WindowsManager.class);
        windows.register(MainWindow.class);

        Router router = resolve(Router.class);
        router.add(AppointmentsController.class, "appointments");
        router.add(HomeController.class, "home");
        
        windows.get(MainWindow.class).reveal();
        router.run(HomeController.class);
    }
    
    public static void main(String[] args) {
        Application app = new Application(new MainModule());
        app.initialize();
    }
}
