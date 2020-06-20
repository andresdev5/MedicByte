/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.utils;

import ec.edu.espe.medicbyte.model.Gender;
import ec.edu.espe.medicbyte.model.User;
import java.util.Scanner;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class Data {
    
    public static void main (String[] args){
        User user = new User();
        
        int option = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su cedula");
        user.setIdentificationcard(scanner.nextLine());
        
        System.out.println("Ingrese su nombres");
        user.setName(scanner.nextLine());
        
        System.out.println("Ingrese su apellido");
        user.setSurname(scanner.nextLine());
        
        while(option < 1 || option > 3 ){
        System.out.println("Escoja su genero");
        System.out.println("1.- " + Gender.FEMALE);
        System.out.println("2.- " + Gender.MALE);
        System.out.println("3.-"  + Gender.UNIDENTIFIED);
        System.out.print("Ingrese la opcion escogida: ");
        option = scanner.nextInt();
        }
        
        System.out.println("Diguite su numero telefonico");
        user.setPhone(scanner.nextLine());
        
        System.out.println("Ingrese un correo electronico ");
        user.setEmail(scanner.nextLine());
            
    }
}
