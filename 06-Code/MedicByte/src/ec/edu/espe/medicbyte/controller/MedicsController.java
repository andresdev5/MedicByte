package ec.edu.espe.medicbyte.controller;

import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.service.MedicService;
import ec.edu.espe.medicbyte.service.impl.MedicServiceImpl;
import ec.edu.espe.medicbyte.util.ChooserOption;
import ec.edu.espe.medicbyte.util.Console;
import ec.edu.espe.medicbyte.util.ConsoleChooser;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 * 
 */
public class MedicsController {
    private final Console console;
    private final MedicService medicService;
    
    public MedicsController() {
        this.console = Console.getInstance();
        this.medicService = new MedicServiceImpl();
    }
    
    public void createMedic() {
        ConsoleChooser specialityChooser = new ConsoleChooser();
        Medic medic = new Medic();

        medic.setName(console.input("Enter medic full name: "));

        for (Speciality speciality : Speciality.values()) {
            specialityChooser
                .addOption(speciality.getLabel())
                .addArgument(speciality);
        }

        console.newLine();
        ChooserOption option = specialityChooser.choose("Choose speciality: ");
        Speciality speciality = (Speciality) option.getArgument(0);
        medic.setSpeciality(speciality);

        int count = medicService.getTotalMedics();
        medic.setId(count + 1);
        medicService.saveMedic(medic);

        console.echoln("Registered medic!");
    }
    
    public void showMedics() {
        List<Medic> medics = medicService.getAllMedics();
        
        if (medics.isEmpty()) {
           console.echoln("No medics found");
           return;
        }
        
        medics.forEach((medic) -> {
            console.echoln(
                "name: %s\n" +
                "speciality: %s\n" +
                "---------------------------\n", 
                medic.getName(), medic.getSpeciality().getLabel()
            );
        });
    }
}
