package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.common.service.AuthService;
import ec.edu.espe.medicbyte.view.AuthView;

/**
 *
 * @author Andres Jonathan J.
 */
public class AuthController extends Controller {
    private final AuthService authService;
    
    @Inject
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @Override
    protected void init() {
        View view = createView(AuthView.class);
        view.set("title", "Authorization");
        view.display();
    }
}
