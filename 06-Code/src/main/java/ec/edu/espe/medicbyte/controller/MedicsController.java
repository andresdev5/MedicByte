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
import ec.edu.espe.medicbyte.service.IMedicService;
import ec.edu.espe.medicbyte.service.IRoleService;
import ec.edu.espe.medicbyte.service.ISpecialityService;
import ec.edu.espe.medicbyte.service.IUserService;
import ec.edu.espe.medicbyte.view.FrmAddMedic;
import ec.edu.espe.medicbyte.view.FrmMedics;
import ec.edu.espe.medicbyte.view.MainWindow;
import javax.swing.JOptionPane;

/**
 *
 * @author Michael Cobacango
 */
public class MedicsController extends Controller {
    private final WindowsManager windowsManager;
    private final IMedicService medicService;
    private final IUserService userService;
    private final IRoleService roleService;
    private final ISpecialityService specialityService;
    
    @Inject()
    public MedicsController(
        WindowsManager windowsManager,
        IMedicService medicService,
        IUserService userService,
        IRoleService roleService,
        ISpecialityService specialityService
    ) {
        this.windowsManager = windowsManager;
        this.medicService = medicService;
        this.userService = userService;
        this.roleService = roleService;
        this.specialityService = specialityService;
    }
    
    @Override
    public void init() {}
    
    @Routed("add")
    public void addMedic() {
        View view = new FrmAddMedic();
        view.set("specialities", specialityService.getAllSpecialities());
        
        view.listen("submit", (args) -> {
            String username = args.get(0);
            String fullName = args.get(1);
            String email = args.get(2);
            char[] password = args.get(3);
            Speciality speciality = args.get(4);
            
            if (userService.userExists(username)) {
                view.emit("showError", FrmAddMedic.Field.USERNAME, "username already taken");
                return;
            }
            
            boolean emailExists = userService.getAllUsers().stream()
                .anyMatch(user -> user.getEmail() != null 
                    && user.getEmail().equalsIgnoreCase(email.toLowerCase()));
            
            if (emailExists) {
                view.emit("showError", FrmAddMedic.Field.EMAIL, "email already taken");
                return;
            }
            
            Role medicRole = roleService.getRole("medic");
            
            if (medicRole == null) {
                JOptionPane.showMessageDialog(
                    view,
                    "Role 'medic' not found. please try again or reinstall application",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            User created = userService.createUser(username, email, String.valueOf(password), medicRole);
                        
            if (created == null) {
                JOptionPane.showMessageDialog(
                    view,
                    "Error while trying to create new user, please try again.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            UserProfile profile = created.getProfile();
            profile.setFullName(fullName);
            
            userService.updateUserProfile(created.getId(), profile);
            Medic medic = medicService.addMedic(created.getId(), speciality);
            
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
        view.set("medics", medicService.getAllMedics());
        windowsManager.getAs(MainWindow.class).display(view);
    }
}
