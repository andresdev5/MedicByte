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
public class EntryData {
    
    public void enterDataPatient() {
        int opcion;
        UserInfo userInfo = new UserInfo(null, null, null, null, null, null, null, null);
        Scanner enterData = new Scanner(System.in);
        System.out.print("Ingrese su Número de Cédula: ");
        userInfo.setIdentificationCard(enterData.nextLine());
        System.out.print("Ingrese sus Nombres: ");
        userInfo.setFirstName(enterData.nextLine());
        System.out.print("Ingrese sus Apellidos: ");
        userInfo.setLastName(enterData.nextLine());
        System.out.print("Ingrese Fecha de Nacimiento: ");
        userInfo.setBirthday(enterData.Data());
        System.out.print("Ingrese su Género: \n1: Femenino"
                + "\n2: Masculino"
                + "\n3: No Identificado");
        switch (opcion = enterData.nextInt()) {
            case 1:
                userInfo.setGender(Gender.FEMALE);
                break;
            case 2:
                userInfo.setGender(Gender.MALE);
                break;
            case 3:
                userInfo.setGender(Gender.UNSPECIFIED);
            default:
                System.out.println("No Tiene Coincidencias");
        }
        System.out.print("Ingrese su email: ");
        userInfo.setEmail(enterData.nextLine());
        System.out.print("Ingrese su Número de Telefono/Celular: ");
        userInfo.setPhone(enterData.nextLine());
        System.out.print("Ingrese su Dirección: ");
        userInfo.setAddress(enterData.nextLine());
        
    }
    
}
