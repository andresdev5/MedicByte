package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.ConsoleView;

/**
 *
 * @author Andres Jonathan J.
 */
public class CreateAppointmentView extends ConsoleView {
    @Override
    public void display() {
        console.clear();
        console.echoln("create new appointment").newLine(2);
        
    }
    
    @Override
    protected String template() {
        return "";
    }
}
