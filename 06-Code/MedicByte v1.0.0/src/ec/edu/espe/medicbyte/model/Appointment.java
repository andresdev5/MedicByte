package ec.edu.espe.medicbyte.model;

import ec.edu.espe.medicbyte.utils.FileManager;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class Appointment {

    private String code;
    private String date;
    private String hour;
    
    public void createAppointment()   {
       
        Scanner dataEntry = new Scanner(System.in);
        System.out.println("CREAR CITA");
        System.out.print("\nIngrese Codigo: ");
        code = dataEntry.nextLine();
        System.out.print("Ingrese la Fecha: ");
        date = dataEntry.nextLine();
        System.out.print("Ingrese la Hora: ");
        hour = dataEntry.nextLine();
       
    }
}
