package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.User;

public interface IAuthService {
    /**
     * login a user given the credentials
     * 
     * @param username
     * @param password
     * @return true if login was successfully, false if fail
     */
    public boolean login(String username, String password);
    
    /**
     * logout the current user
     */
    public void logout();
    
    /**
     * get the current logged in user
     * @return 
     */
    public User getCurrentUser();
    
    /**
     * check if exists a user logged in
     * @return 
     */
    public boolean isLoggedIn();
}