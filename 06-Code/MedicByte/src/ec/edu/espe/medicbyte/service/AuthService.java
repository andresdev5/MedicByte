package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.User;

public interface AuthService {
    public boolean login(String username, String password);
    public void logout();
    public User getCurrentUser();
    public boolean isLoggedIn();
}