package ec.edu.espe.medicbyte.controller;

import ec.edu.espe.medicbyte.model.Gender;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.utils.FileManager;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Andres Jonathan J.
 */
public class UsersController {    
    // TODO: use provider and develop this code
    public void createMedic() {
        
    }
    
    // TODO: use provider and clean this code
    public void showMedics() {
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = new FileManager("ListMedic.txt");
        Medic medic = new Medic();

        System.out.println("Ingrese el nombre del medico");
        medic.setName(scanner.nextLine());

        int index = 0;
        int selected = 0;

        System.out.println("seleccione la especialidad: ");

        for (Speciality speciality : Speciality.values()) {
            System.out.printf("%d: %s\n", index + 1, speciality.getLabel());
            index++;
        }

        do {
            System.out.print("especialidad: ");
            selected = scanner.nextInt();
            scanner.nextLine();
        } while (selected <= 0 || selected > Speciality.values().length);

        Speciality speciality = Speciality.values()[selected - 1];
        medic.setSpeciality(speciality);

        String content = fileManager.readFile();
        int count = (int) Arrays.asList(content.split("\n"))
                .stream()
                .filter(line -> !line.isEmpty())
                .count();

        medic.setId(count + 1);
        fileManager.writeFile(medic.toString());
    }
}
