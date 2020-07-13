package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Routed;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.common.core.ViewField;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.MedicService;
import ec.edu.espe.medicbyte.service.RoleService;
import ec.edu.espe.medicbyte.service.SpecialityService;
import ec.edu.espe.medicbyte.service.UserService;
import ec.edu.espe.medicbyte.view.RegisterMedicView;
import ec.edu.espe.medicbyte.view.ListAllMedicsView;
import java.util.Arrays;

/**
 *
 * @author Andres Jonathan J.
 */
public class MedicsController extends Controller {
    @Inject() private UserService userService;
    @Inject() private RoleService roleService;
    @Inject() private MedicService medicService;
    @Inject() private SpecialityService specialityService;
    
    @Override
    public void init() {}
    
    @Routed("listAll")
    public void listAllMedics() {
        View view = createView(ListAllMedicsView.class);
        view.set("medics", medicService.getAllMedics());
        view.display();
    }
    
    @Routed("create")
    public void createMedic() {
        View view = createView(RegisterMedicView.class);
        
        ViewField<String> username = view.bindField("username", String.class);
        ViewField<String> password = view.bindField("password", String.class);
        ViewField<String> fullName = view.bindField("fullName", String.class);
        ViewField<Speciality> speciality = view.bindField("speciality", Speciality.class);
        
        view.set("specialities", specialityService.getAllSpecialities());
        view.display();
        
        if (userService.userExists(username.getValue())) {
            view.emit("onRegister", Arrays.asList(false, "Username already taken"));
            return;
        }
        
        if (username.getValue().isEmpty() || password.getValue().isEmpty()) {
            view.emit("onRegister", Arrays.asList(false, "password and username is required"));
            return;
        }
        
        Role medicRole = roleService.getRole("medic");
        boolean created = userService.createUser(username.getValue(), password.getValue(), medicRole);
        
        if (created) {
            User user = userService.getUser(username.getValue());
            medicService.addMedic(user, speciality.getValue(), fullName.getValue());
            view.emit("onRegister", Arrays.asList(true));
        } else {
            view.emit("onRegister", Arrays.asList(false, "Error registering user, please try again."));
        }
    }
}
