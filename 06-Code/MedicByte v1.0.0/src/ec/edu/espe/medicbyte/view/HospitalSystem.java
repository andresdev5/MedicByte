
package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Patient;
import java.util.Scanner;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class HospitalSystem {
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
        int opcion;
       Medic medic1 = new Medic();
       Patient patient1 = new Patient();
        System.out.print("Ingrese su opcion: 1: Ingresar Medico 2: Agendar Cita");
        opcion = sc.nextInt();
       switch(opcion){
           case 1: medic1.enterData();
           break;
           case 2: patient1.enterData();
           break;
           default: System.out.println(" ");
       }
    }
    
}
