package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.ConsoleView;
import ec.edu.espe.medicbyte.common.tui.ConsolePagination;
import ec.edu.espe.medicbyte.model.Medic;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class ListAllMedicsView extends ConsoleView {
    @Override
    public void init() {}
    
    @Override
    public void display() {
        ConsolePagination paginator = new ConsolePagination(3);
        List<Medic> medics = get("medics");
        
        if (medics.isEmpty()) {
           console.newLine().echoln("No medics found");
           return;
        }
        
        medics.forEach((medic) -> {
            paginator.addItem(() -> {
                console.echoln(
                    "name: %s\n" +
                    "speciality: %s\n" +
                    "---------------------------\n", 
                    medic.getFullName(), medic.getSpeciality().getName()
                );
            });
        });

        paginator.display();
        console.newLine(2);
    }
    
    @Override
    public String template() { return ""; }
}
