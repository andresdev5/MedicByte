package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.ConsoleView;
import java.util.List;
import static org.fusesource.jansi.Ansi.ansi;

/**
 *
 * @author Andres Jonathan J.
 */
public class RegisterView extends ConsoleView {
    @Override
    public void init() {
        listen("onRegister", this::displayRegisterMessage);
    }
    
    @Override
    public void display() {
        String username = console.input("Enter username: ", (input) -> {
            return !input.isEmpty();
        });
        
        String password = console.mask().input("Enter password: ", (input) -> {
            return !input.isEmpty();
        });
        
        setField("username", username);
        setField("password", password);
    }
    
    private void displayRegisterMessage(Object args) {
        List<Object> parsedArgs = (List<Object>)args;
        boolean valid = (boolean) parsedArgs.get(0);
        
        if (!valid) {
            String message = (String) parsedArgs.get(1);
            console.newLine(1).errorln(message);
        } else {
            console.newLine(1).echoln(
                ansi().fgBrightGreen()
                    .a("successfully registered!").reset());
        }
        
        console.pause();
    }
    
    @Override
    protected String template() {
        return "";
    }
}
