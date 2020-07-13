package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.ConsoleView;

import static org.fusesource.jansi.Ansi.ansi;

/**
 *
 * @author Andres Jonathan J.
 */
public class LoginView extends ConsoleView {
    @Override
    public void init() {
        listen("onLogin", this::displayLoginError);
    }
    
    @Override
    public void display() {
        String username = console.input("Enter an username: ");
        String password = console.mask().input("Enter an password: ");
        
        setField("username", username);
        setField("password", password);
    }
    
    public void displayLoginError(Object loggedIn) {
        boolean isLoggedIn = (boolean) loggedIn;
        
        if (!isLoggedIn) {
            console.newLine(1).errorln("Invalid credentials!");
            console.newLine();
        } else {
            console.newLine(1).echoln(
                ansi().fgBrightGreen()
                    .a("Successfully authenticated!").reset());
        }
        
        console.pause();
    }
    
    @Override
    protected String template() {
        return "";
    }
}
