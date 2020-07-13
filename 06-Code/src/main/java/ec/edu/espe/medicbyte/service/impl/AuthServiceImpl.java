package ec.edu.espe.medicbyte.service.impl;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.AuthService;
import ec.edu.espe.medicbyte.service.UserService;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Andres Jonathan J.
 */
public class AuthServiceImpl implements AuthService {
    private static User currentUser;
    private final UserService userService;
    
    @Inject()
    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean login(String username, String password) {
        User found = userService.findUser(user -> {
            return user.getUsername().trim().equalsIgnoreCase(username)
                && BCrypt.checkpw(password, user.getPassword());
        });
        
        if (found != null) {
            currentUser = found;
            return true;
        }

        return false;
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