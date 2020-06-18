/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.model;

import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Michael Cobacango -ESPE -DCCO
 */
public class Patient extends User {

    private Date age;

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public void enterData() {

        Scanner dataEntry = new Scanner(System.in);

        System.out.print("Cédula: ");
        setIdentificationcard(dataEntry.nextLine());

        System.out.print("Apellidos: ");
        setSurname(dataEntry.nextLine());

        System.out.print("Nombres: ");
        setName(dataEntry.nextLine());

        System.out.print("Edad: ");
        setAge(age);

        System.out.print("Teléfono: ");
        setPhone(dataEntry.nextLine());

        System.out.print("Email: ");
        setEmail(dataEntry.nextLine());

        System.out.print("Género: ");
        setGender(Gender.valueOf(dataEntry.nextLine()));

    }

}
