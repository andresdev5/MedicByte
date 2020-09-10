package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.Application;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.view.AuthWindow;
import ec.edu.espe.medicbyte.view.FrmLogin;
import ec.edu.espe.medicbyte.view.FrmRegister;
import ec.edu.espe.medicbyte.service.IAuthService;
import ec.edu.espe.medicbyte.service.IPatientService;
import ec.edu.espe.medicbyte.service.IRoleService;
import ec.edu.espe.medicbyte.service.IUserService;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Andres Jonathan J.
 */
public class AuthController extends Controller {
    ResourceBundle lang = ResourceBundle.getBundle("ec/edu/espe/medicbyte/view/Bundle");
    private final Application application;
    private final WindowsManager windowsManager;
    private final IAuthService authService;
    private final IUserService userService;
    private final IRoleService roleService;
    private final IPatientService patientService;
    private final Router router;
    
    @Inject()
    public AuthController(
        Application application,
        WindowsManager windowsManager,
        IAuthService authService,
        IUserService userService,
        IRoleService roleService,
        IPatientService patientService,
        Router router
    ) {
        this.application = application;
        this.windowsManager = windowsManager;
        this.authService = authService;
        this.userService = userService;
        this.roleService = roleService;
        this.patientService = patientService;
        this.router = router;
    }
    
    @Override
    public void init() {
        View loginView = new FrmLogin();
        View signupView = new FrmRegister();
        AuthWindow window = windowsManager.getAs(AuthWindow.class);
        
        loginView.listen("submit", (args) -> {
            String username = args.get(0);
            char[] password = args.get(1);
            
            if (!userService.exists(username)) {
                loginView.emit("showError", "username", lang.getString("user_not_exists"));
                loginView.emit("setEnabledBtnLogin", true);
                return;
            } else if (!authService.login(username, String.valueOf(password))) {
                loginView.emit("showError", "password", lang.getString("invalid_credentials"));
                loginView.emit("setEnabledBtnLogin", true);
                return;
            }
            
            window.close();
            router.run("main");
        });
        
        signupView.listen("submit", (args) -> {
            String username = args.get(0);
            String email = args.get(1);
            char[] password = args.get(2);
            String identifyCard = args.get(3);
            
            if (userService.exists(username)) {
                signupView.emit("showError", FrmRegister.Field.USERNAME, lang.getString("username_already_taken"));
                return;
            }
            
            if (patientService.get(identifyCard) != null) {
                signupView.emit("showError", FrmRegister.Field.IDENTIFY_CARD, lang.getString("id_card_already_registered"));
                return;
            }
            
            boolean emailExists = userService.getAll().stream()
                .anyMatch(user -> user.getEmail() != null 
                    && user.getEmail().equalsIgnoreCase(email.toLowerCase()));
            
            if (emailExists) {
                signupView.emit("showError", FrmRegister.Field.EMAIL, lang.getString("email_already_taken"));
                return;
            }
            
            Role patientRole = roleService.get("patient");
            
            if (patientRole == null) {
                JOptionPane.showMessageDialog(
                    signupView,
                    String.format(lang.getString("role_not_found_fatal_error"), "patient"),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            Patient patient = new Patient();
            patient.setIdCard(identifyCard);
            patient.setUsername(username);
            patient.setEmail(email);
            patient.setPassword(BCrypt.hashpw(String.valueOf(password), BCrypt.gensalt()));
            patient.setEmail(email);
            patient.setRole(patientRole);
            patient.setProfile(userService.createProfile());
            
            boolean created = patientService.save(patient);
            
            if (!created) {
                JOptionPane.showMessageDialog(
                    signupView,
                    lang.getString("error_creating_new_user"),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            window.display(loginView);
            loginView.emit(
                "showStatusMessage",
                FrmLogin.StatusMessage.SUCCESS,
                String.format(lang.getString("account_created_succesfully"), username)
            );
            signupView.emit("success");
        });
        
        loginView.listen("swapToSignup", (args) -> {
            loginView.emit("hideErrors");
            loginView.emit("hideStatusMessage");
            window.display(signupView);
        });
        
        signupView.listen("swapToLogin", (args) -> {
            signupView.emit("hideErrors");
            window.display(loginView);
        });
        
        window.reveal();
        window.display(loginView);
    }
}
