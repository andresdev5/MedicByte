package ec.edu.espe.medicbyte.common;

import com.formdev.flatlaf.FlatLightLaf;
import ec.edu.espe.medicbyte.common.core.Config;
import ec.edu.espe.medicbyte.common.core.Container;
import ec.edu.espe.medicbyte.common.core.DatabaseManager;
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
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
    
    /**
     * initialize the application
     */
    public void initialize() {
        Config config = resolve(Config.class);
        
        try {
            Locale.setDefault(new Locale(config.get("language", String.class)));
        } catch (Exception ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            Locale.setDefault(new Locale("en"));
        }
        
        Router router = resolve(Router.class);
        DatabaseManager database = resolve(DatabaseManager.class);
        AuthService authService = resolve(AuthService.class);
        WindowsManager windows = resolve(WindowsManager.class);
        
        // install FlatLightLaf swing look and feel
        FlatLightLaf.install();
        
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch(UnsupportedLookAndFeelException exception) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, exception);
        }
        
        // register UI icon fonts
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
        
        // run MainController
        router.run("main");
    }
    
    public static void main(String[] args) {
        Application app = new Application(new MainModule());
        app.initialize();
    }
    
    public static void restart(String[] args) {
        List<String> jvmArgs = ManagementFactory.getRuntimeMXBean().getInputArguments();
        ArrayList<String> commands = new ArrayList<>(4 + jvmArgs.size() + args.length);

        commands.add(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java");

        for (String jvmArg : jvmArgs) {
            commands.add(jvmArg);
        }

        commands.add("-cp");
        commands.add(ManagementFactory.getRuntimeMXBean().getClassPath());
        commands.add(Application.class.getName());

        for (String arg : args) {
            commands.add(arg);
        }

        File workingDir = null;
        String[] env = null;
        String[] commandArray = new String[commands.size()];
        commandArray = commands.toArray(commandArray);

        try {
            Runtime.getRuntime().exec(commandArray, env, workingDir);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
