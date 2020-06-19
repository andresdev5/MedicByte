package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.model.ListMedic;
import ec.edu.espe.medicbyte.model.ListPatient;
import ec.edu.espe.medicbyte.model.Patient;
import java.util.Scanner;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class HospitalSystem {

    public static void main(String[] args) {
        Scanner dataEntry = new Scanner(System.in);
        int option;
        ListMedic listMedic = new ListMedic();
        ListPatient listPatient = new ListPatient();
        Patient patient = new Patient();
        System.out.print("MENÚ OPCIONES \n1: Ver Médicos"
                + "\n2: Ingresar Paciente"
                + "\nDigite su Opción: ");
        option = dataEntry.nextInt();
        switch (option) {
            case 1:
                listMedic.enterMedic();
                listMedic.showListMedic();
                break;
            case 2:
                listPatient.enterPatient(patient);
                listPatient.showListPatient();
                break;
            default:
                System.out.println("No se encontraron coincidencias");
        }

    }

}
