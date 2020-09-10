package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Routed;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.model.UserProfile;
import ec.edu.espe.medicbyte.service.IAuthService;
import ec.edu.espe.medicbyte.service.IMedicService;
import ec.edu.espe.medicbyte.service.IRoleService;
import ec.edu.espe.medicbyte.service.ISpecialityService;
import ec.edu.espe.medicbyte.service.IUserService;
import ec.edu.espe.medicbyte.view.FrmAddMedic;
import ec.edu.espe.medicbyte.view.FrmMedics;
import ec.edu.espe.medicbyte.view.MainWindow;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Michael Cobacango
 */
public class MedicsController extends Controller {
    ResourceBundle lang = ResourceBundle.getBundle("ec/edu/espe/medicbyte/view/Bundle");
    private final WindowsManager windowsManager;
    private final IMedicService medicService;
    private final IUserService userService;
    private final IRoleService roleService;
    private final ISpecialityService specialityService;
    private final IAuthService authService;
    
    @Inject()
    public MedicsController(
        WindowsManager windowsManager,
        IMedicService medicService,
        IUserService userService,
        IRoleService roleService,
        ISpecialityService specialityService,
        IAuthService authService
    ) {
        this.windowsManager = windowsManager;
        this.medicService = medicService;
        this.userService = userService;
        this.roleService = roleService;
        this.specialityService = specialityService;
        this.authService = authService;
    }
    
    @Override
    public void init() {}
    
    @Routed("add")
    public void addMedic() {
        View view = new FrmAddMedic();
        view.set("specialities", specialityService.getAll());
        
        view.listen("submit", (args) -> {
            String username = args.get(0);
            String fullName = args.get(1);
            String email = args.get(2);
            char[] password = args.get(3);
            Speciality speciality = args.get(4);
            
            if (userService.exists(username)) {
                view.emit("showError", FrmAddMedic.Field.USERNAME, lang.getString("username_already_taken"));
                return;
            }
            
            boolean emailExists = userService.getAll().stream()
                .anyMatch(user -> user.getEmail() != null 
                    && user.getEmail().equalsIgnoreCase(email.toLowerCase()));
            
            if (emailExists) {
                view.emit("showError", FrmAddMedic.Field.EMAIL, lang.getString("email_already_taken"));
                return;
            }
            
            Role medicRole = roleService.get("medic");
            
            if (medicRole == null) {
                JOptionPane.showMessageDialog(
                    view,
                    String.format(lang.getString("role_not_found_fatal_error"), "medic"),
                    lang.getString("database_error_title"),
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            UserProfile profile = userService.createProfile();
            profile.setFullName(fullName);
            
            Medic medic = new Medic();
            medic.setUsername(username);
            medic.setPassword(BCrypt.hashpw(String.valueOf(password), BCrypt.gensalt()));
            medic.setEmail(email);
            medic.setRole(medicRole);
            medic.setProfile(profile);
            medic.setSpeciality(speciality);
            
            boolean created = medicService.save(medic);
                        
            if (!created) {
                JOptionPane.showMessageDialog(
                    view,
                    lang.getString("error_creating_new_user"),
                    lang.getString("database_error_title"),
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            view.emit(
                "submitComplete",
                FrmAddMedic.MessageStatus.SUCCESS,
                String.format("medic successfully registered", username)
            );
        });
        
        windowsManager.getAs(MainWindow.class).display(view);
    }
    
    @Routed("showAll")
    public void showAllMedics() {
        View view = new FrmMedics();
        
        view.set("currentUser", authService.getCurrentUser());
        view.set("specialities", specialityService.getAll());
        view.set("medics", medicService.getAll());
        view.listen("updateMedic", (args) -> {
            Medic medic = args.get(0);
            medicService.update(medic);
            view.emit("MedicUpdated", medic);
        });
        
        windowsManager.getAs(MainWindow.class).display(view);
    }
}
