package ec.edu.espe.medicbyte.common.service;

import ec.edu.espe.medicbyte.model.User;

/**
 *
 * @author Andres Jonathan J.
 */
public class UserService {
    private User currentUser;
    
    public void setCurrentUser(User user) {
        currentUser = user;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
}
