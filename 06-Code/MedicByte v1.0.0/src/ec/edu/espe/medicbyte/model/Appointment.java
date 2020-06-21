package ec.edu.espe.medicbyte.model;

import ec.edu.espe.medicbyte.utils.FileManager;
import java.util.Scanner;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class Appointment {

    private String code;
    private String date;
    private String hour;
    
    public void createAppointment() {
        Scanner dataEntry = new Scanner(System.in);
        ListMedic listMedic = new ListMedic();
        listMedic.showListMedic();
        System.out.println("CREAR CITA");
        System.out.print("\n\nIngrese Codigo: ");
        code = dataEntry.nextLine();
        System.out.print("\nIngrese la Fecha: ");
        date = dataEntry.nextLine();
        System.out.print("\nIngrese la Hora: ");
        hour = dataEntry.nextLine();
        
    }

}
