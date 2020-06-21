/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.model;

import java.util.Scanner;

/**
 *
 * @author Michael Cobacango -ESPE -DCCO
 */
public class Patient extends User {

    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void enterPatient(Patient patient) {
        int option;
        Scanner dataEntry = new Scanner(System.in);
        System.out.println("\n**INGRESE SUS DATOS**");
        System.out.print("Cédula: ");
        patient.setIdentificationcard(dataEntry.nextLine());
        System.out.print("Nombres: ");
        patient.setName(dataEntry.nextLine());
        System.out.print("Apellidos: ");
        patient.setSurname(dataEntry.nextLine());
        System.out.print("Fecha de Nacimiento: ");
        patient.setAge(dataEntry.nextLine());
        System.out.print("Telefono: ");
        patient.setPhone(dataEntry.nextLine());
        System.out.print("Email: ");
        patient.setEmail(dataEntry.nextLine());
        System.out.print("Género: \n1: Femenino\n2: Masculino\n3: No Especificado: "
                + "\n: ");
        option = dataEntry.nextInt();
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

    @Override
    public String toString() {
        System.out.println("\n\n***PACIENTE REGISTRADO***\nCON LOS SIGUIENTES DATOS:");
        return super.toString().concat("\nFecha de Nacimiento: " + age);
    }
}
