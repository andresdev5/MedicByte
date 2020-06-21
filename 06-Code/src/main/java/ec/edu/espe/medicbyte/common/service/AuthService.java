package ec.edu.espe.medicbyte.common.service;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.model.User;

/**
 *
 * @author Andres Jonathan J.
 */
public class AuthService {
    private boolean loggedIn = false;
    private final UserService userService;
    
    @Inject
    public AuthService(UserService userService) {
        this.userService = userService;
    }
    
    public boolean login(String username, String password) {
        if (username.equals("white") && password.equals("1501")) {
            loggedIn = true;
            userService.setCurrentUser(new User());
        }
        
        return loggedIn;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }
}
