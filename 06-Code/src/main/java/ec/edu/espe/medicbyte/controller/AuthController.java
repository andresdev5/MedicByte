package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.Application;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.view.AuthWindow;
import ec.edu.espe.medicbyte.view.FrmLogin;
import ec.edu.espe.medicbyte.view.FrmRegister;
import ec.edu.espe.medicbyte.service.IAuthService;
import ec.edu.espe.medicbyte.service.IPatientService;
import ec.edu.espe.medicbyte.service.IRoleService;
import ec.edu.espe.medicbyte.service.IUserService;
import javax.swing.JOptionPane;

/**
 *
 * @author Andres Jonathan J.
 */
public class AuthController extends Controller {
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
            
            if (!userService.userExists(username)) {
                loginView.emit("showError", "username", "User not exists");
                loginView.emit("setEnabledBtnLogin", true);
                return;
            } else if (!authService.login(username, String.valueOf(password))) {
                loginView.emit("showError", "password", "Invalid credentials");
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
            
            if (userService.userExists(username)) {
                signupView.emit("showError", FrmRegister.Field.USERNAME, "username already taken");
                return;
            }
            
            if (patientService.getPatient(identifyCard) != null) {
                signupView.emit("showError", FrmRegister.Field.IDENTIFY_CARD, "Identify card already registered");
                return;
            }
            
            boolean emailExists = userService.getAllUsers().stream()
                .anyMatch(user -> user.getEmail() != null 
                    && user.getEmail().equalsIgnoreCase(email.toLowerCase()));
            
            if (emailExists) {
                signupView.emit("showError", FrmRegister.Field.EMAIL, "email already taken");
                return;
            }
            
            Role patientRole = roleService.getRole("patient");
            
            if (patientRole == null) {
                JOptionPane.showMessageDialog(
                    signupView,
                    "Role 'patient' not found. please contact with administrator or reinstall application",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            User created = userService.createUser(username, email, String.valueOf(password), patientRole);
            
            if (created == null) {
                JOptionPane.showMessageDialog(
                    signupView,
                    "Error while trying to create new user, please try again.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            Patient patient = patientService.addPatient(created.getId(), identifyCard, false);
            
            window.display(loginView);
            loginView.emit(
                "showStatusMessage",
                FrmLogin.StatusMessage.SUCCESS,
                String.format("%s account created successfully", username)
            );
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
