package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.service.AuthService;
import ec.edu.espe.medicbyte.view.AuthWindow;

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
        windowsManager.getAs(AuthWindow.class).reveal();
        System.out.println("auth::init");
    }
}
