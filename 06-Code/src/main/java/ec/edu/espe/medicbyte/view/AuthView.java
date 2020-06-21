package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.ConsoleView;
import ec.edu.espe.medicbyte.util.StringUtils;

import static org.fusesource.jansi.Ansi.ansi;

/**
 *
 * @author Andres Jonathan J.
 */
public class AuthView extends ConsoleView {
    @Override
    public void init() {
        listen("onLogin", this::displayLoginError);
    }
    
    @Override
    public void display() {
        int width = console.getTerminal().getWidth();
        
        console
            .clear()
            .echoln(StringUtils.repeat("-", width - 1))
            .echoln(ansi().a(" Autorización "))
            .echoln(StringUtils.repeat("-", width - 1))
            .newLine();
        
        String username = console.input("Ingresa tu nombre de usuario: ");
        String password = console.input("Ingresa tu contraseña: ");
        
        setField("username", username);
        setField("password", password);
    }
    
    public void displayLoginError(Object loggedIn) {
        boolean isLoggedIn = (boolean) loggedIn;
        
        if (!isLoggedIn) {
            console.newLine(1).errorln("Credenciales invalidas!");
            console.newLine();
            
            String option = console.input("Intentar denuevo? [y/n]: ", (line) -> {
                return line.equalsIgnoreCase("y") || line.equalsIgnoreCase("n");
            });
            
            if (option.equalsIgnoreCase("n")) {
                emit("cancelLogin");
            }
        } else {
            console.newLine(1).echoln(
                ansi().fgBrightGreen()
                    .a("Se ha autentificado correctamente!").reset());
        }
    }
    
    @Override
    protected String template() {
        return "";
    }
}
