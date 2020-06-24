package ec.edu.espe.medicbyte.controller;

import ec.edu.espe.medicbyte.model.Gender;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.service.PatientService;
import ec.edu.espe.medicbyte.service.impl.PatientServiceImpl;
import java.util.Scanner;

/**
 *
 * @author Andres Jonathan J.
 */
public class PatientsController {
    public void createPatient() {
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
        patient.setAge(20); // TODO: cambiar luego
        scanner.nextLine();
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

        PatientService patientService = new PatientServiceImpl();
        patientService.savePatient(patient);
    }
}
