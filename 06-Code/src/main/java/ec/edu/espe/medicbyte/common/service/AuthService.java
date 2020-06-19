package ec.edu.espe.medicbyte.common.service;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Console;

/**
 *
 * @author Andres Jonathan J.
 */
public class AuthService {
    private final Console console;
    
    @Inject
    public AuthService(Console console) {
        this.console = console;    
    }
    
    public boolean isLoggedIn() {
        return false;
    }
}
