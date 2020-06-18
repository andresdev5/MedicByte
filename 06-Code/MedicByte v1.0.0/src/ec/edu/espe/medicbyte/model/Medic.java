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
public class Medic extends User {

    private String speciality;

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void enterData() {

        Scanner dataEntry = new Scanner(System.in);

        System.out.print("Cédula: ");
        setIdentificationcard(dataEntry.nextLine());

        System.out.print("Apellidos: ");
        setSurname(dataEntry.nextLine());

        System.out.print("Nombres: ");
        setName(dataEntry.nextLine());

        System.out.print("Especialidad: ");
        setSpeciality(dataEntry.nextLine());

        System.out.print("Teléfono: ");
        setPhone(dataEntry.nextLine());

        System.out.print("Email: ");
        setEmail(dataEntry.nextLine());

        System.out.print("Género: ");
        setGender(Gender.valueOf(dataEntry.nextLine()));

    }

}
