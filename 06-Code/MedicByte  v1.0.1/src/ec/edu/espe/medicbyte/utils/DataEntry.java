/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.utils;

import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Gender;
import ec.edu.espe.medicbyte.model.ListMedic;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Patient;
import java.util.Scanner;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class DataEntry {

    public void dataPatient() {
        int option;
        Scanner scanner = new Scanner(System.in);
        Patient patient = new Patient();
        System.out.println("\n**INGRESE SUS DATOS**");
        System.out.print("Cédula: ");
        patient.setIdentificationcard(scanner.nextLine());
        System.out.print("Nombres: ");
        patient.setName(scanner.nextLine());
        System.out.print("Apellidos: ");
        patient.setSurname(scanner.nextLine());
        System.out.print("Fecha de Nacimiento: ");
        patient.setAge(scanner.nextLine());
        System.out.print("Telefono: ");
        patient.setPhone(scanner.nextLine());
        System.out.print("Email: ");
        patient.setEmail(scanner.nextLine());
        System.out.print("Género: \n1: Femenino\n2: Masculino\n3: No Especificado: "
                + "\n: ");
        option = scanner.nextInt();
        switch (option) {
            case 1:
                patient.setGender(Gender.FEMALE);
                break;
            case 2:
                patient.setGender(Gender.MALE);
                break;
            case 3:
                patient.setGender(Gender.UNIDENTIFIED);
                break;
            default:
                System.out.println("No se Encontraron Coincidencias");
        }
    }

    public void dataMedic() {
        Scanner scanner = new Scanner(System.in);
        Medic medic = new Medic();
        System.out.println("Ingrese el nombre del medico");
        medic.setName(scanner.nextLine());
        System.out.println("Ingrese la especialidad");
        medic.setSpeciality(scanner.nextLine());
        FileManager fileManager = new FileManager("ListMedic.txt");
        fileManager.writeFile(medic.toString());
    }


    public void showMedic() {
        int option;
        Scanner scanner = new Scanner(System.in);
        System.out.print("1: Lista de Médicos"
                + "\n2: Lista de Medicos (Especialidad)"
                + "\nDigite su Opción: ");
        option = scanner.nextInt();
        switch (option) {
            case 1: ListMedic listMedic = new ListMedic();
                    listMedic.showListMedic();
                break;
            case 2:
                System.out.print("1: Pediatric"
                        + "\n2: Odontologo"
                        + "\n3: Traumatologo"
                        + "\nDigite su Opción: ");
                option = scanner.nextInt();
                switch (option) {
                    case 1: //Pediatric
                        break;
                    case 2: //Odontology
                        break;
                    case 3: //Traumatology
                        break;
                    default:
                        System.out.println("No se Encontraron Coincidencias");
                }
                break;
            default:
                System.out.println("No se Encontraron Coincidencias");

        }
    }

    public void createAppointment(){
        Scanner scanner = new Scanner(System.in);
        Appointment appointment = new Appointment();
        ListMedic listMedic = new ListMedic();
        
        System.out.println("MEDICOS DISPONIBLES");
        listMedic.showListMedic();
       
        System.out.println("Ingrese un codigo: ");
        appointment.setCode(scanner.nextLine());
        
        System.out.println("Ingrese la fecha: ");
        appointment.setDate(scanner.nextLine());
        
        System.out.println("Ingrese la Hora: ");
        appointment.setHour(scanner.nextLine());
    
    }
}
