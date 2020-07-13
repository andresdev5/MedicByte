package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.ConsoleView;
import ec.edu.espe.medicbyte.common.tui.ConsoleChooser;
import ec.edu.espe.medicbyte.model.Speciality;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 *
 * @author Andres Jonathan J.
 */
public class RegisterMedicView extends ConsoleView {
    @Override
    public void init() {
        listen("onRegister", this::displayRegisterMessage);
    }
    
    @Override
    public void display() {
        ConsoleChooser specialityChooser = new ConsoleChooser();
        List<Speciality> specialities = get("specialities");
        
        specialities.forEach(speciality -> {
            specialityChooser
                .addOption(speciality.getName())
                .addArgument(speciality);
        });
        
        String username = console.input("Enter username: ", (input) -> {
            return !input.isEmpty();
        });
        String password;
        
        do {
            password = console.mask().input("Enter password: ", (input) -> {
                return !input.isEmpty();
            });

            String password2 = console.mask().input("confirm password: ", (input) -> {
                return !input.isEmpty();
            });
            
            if (!password.equals(password2)) {
                console.errorln("password does not match");
                continue;
            }
            
            break;
        } while (true);
        
        String fullName = console.input("Enter medic full name:", (input) -> {
            return !input.isEmpty();
        });
        
        Speciality speciality = specialityChooser
            .choose("choose an speciality: ")
            .getArgument(0);
        
        setField("username", username);
        setField("fullName", fullName);
        setField("password", password);
        setField("speciality", speciality);
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
