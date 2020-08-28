package ec.edu.espe.medicbyte.service.impl;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import ec.edu.espe.medicbyte.service.IAuthService;
import ec.edu.espe.medicbyte.service.IUserService;

/**
 *
 * @author Andres Jonathan J.
 */
public class AuthService implements IAuthService {
    private static User currentUser;
    private final IUserService userService;
    
    @Inject()
    public AuthService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean login(String username, String password) {
        User found = userService.get(username);
        
        if (found == null) {
            return false;
        } else if (!BCrypt.checkpw(password, found.getPassword())) {
            return false;
        }
        
        currentUser = found;
        return true;
    }

    @Override
    public void logout() {
        currentUser = null;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}