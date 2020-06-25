package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.AuthService;
import ec.edu.espe.medicbyte.service.UserService;
import ec.edu.espe.medicbyte.util.StringUtils;

/**
 *
 * @author Andres Jonathan J.
 */
public class AuthServiceImpl implements AuthService {
    private static User currentUser;
    private final UserService userService;

    public AuthServiceImpl() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public boolean login(String username, String password) {
        String hash = StringUtils.sha512(password);

        User found = userService.findUser(user -> {
            return user.getUsername().trim().equalsIgnoreCase(username)
                && user.getPassword().trim().equals(hash);
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