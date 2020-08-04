package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.service.AuthService;
import ec.edu.espe.medicbyte.view.AuthWindow;
import ec.edu.espe.medicbyte.view.FrmLogin;
import ec.edu.espe.medicbyte.view.FrmRegister;

/**
 *
 * @author Andres Jonathan J.
 */
public class AuthController extends Controller {
    private final WindowsManager windowsManager;
    private final AuthService authService;
    
    @Inject()
    public AuthController(WindowsManager windowsManager, AuthService authService) {
        this.windowsManager = windowsManager;
        this.authService = authService;
    }
    
    @Override
    public void init() {
        View loginView = new FrmLogin();
        View signupView = new FrmRegister();
        AuthWindow window = windowsManager.getAs(AuthWindow.class);
        
        loginView.listen("submit", (args) -> {
            String username = args.get(0);
            String password = args.get(1);
        });
        
        window.reveal();
        window.display(loginView);
    }
}
