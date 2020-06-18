package ec.edu.espe.medicbyte.common.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ec.edu.espe.medicbyte.common.core.Console;

/**
 *
 * @author Andres Jonathan J.
 */
public class AuthService {
    private final Console console;
    
    @Inject
    public AuthService(Console console) {
        console.echoln("AuthService instanced");
        this.console = console;    
    }
    
    public void login() {
        console.echoln("AuthService::login");
    }
    
    public boolean isLoggedIn() {
        return false;
    }
}
