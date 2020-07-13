package ec.edu.espe.medicbyte.controller;

import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Routed;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.view.RegisterMedicView;

/**
 *
 * @author Andres Jonathan J.
 */
public class UsersController extends Controller {
    @Override
    public void init() {}
    
    @Routed("create")
    public void createUser() {
        View view = createView(RegisterMedicView.class);
        view.display();
    }
}
