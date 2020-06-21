package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Console;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.common.core.ViewField;
import ec.edu.espe.medicbyte.common.service.AuthService;
import ec.edu.espe.medicbyte.view.AuthView;

/**
 *
 * @author Andres Jonathan J.
 */
public class AuthController extends Controller {
    private final AuthService authService;
    private final Console console;
    
    @Inject
    public AuthController(AuthService authService, Console console) {
        this.authService = authService;
        this.console = console;
    }
    
    @Override
    protected void init() {
        View view = createView(AuthView.class);
        view.listen("cancelLogin", () -> {
            System.exit(0);
        });
        
        ViewField<String> username = view.bindField("username", String.class);
        ViewField<String> password = view.bindField("password", String.class);
        
        view.display();
        
        boolean loggedIn = authService.login(username.getValue(), password.getValue());
        
        view.emit("onLogin", loggedIn);
        
        console
            .newLine()
            .echo("Press <enter> to continue...")
            .input();
        console.clear();
    }
}
