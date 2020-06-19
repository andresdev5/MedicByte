package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.ConsoleView;

/**
 *
 * @author Andres Jonathan J.
 */
public class AuthView extends ConsoleView {
    @Override
    protected String template() {
        return "${title}\n\nLOGIN | REGISTER";
    }
}
