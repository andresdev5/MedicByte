/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.utils;

import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.ListMedic;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class Menu {
    public void caratula() {

        System.out.print("\n\t\t================================================================================== ");
        System.out.print("\n                                               || ****** ****** ****** ******   ");
        System.out.print("\n                              UNIVERSIDAD   " + "   || *      *      *    * *      ");
        System.out.print("\n                              DE LAS FUERZAS" + "   || ****** ****** ****** ****** ");
        System.out.print("\n                              ARMADAS       " + "   || *           * *      *      ");
        System.out.print("\n                                               || ****** ****** *      ******   ");
        System.out.print("\n\t\t=================================================================================== ");
        System.out.print("\n\n");
        System.out.print("                      INTEGRANTES: Junior Jurado                     MATERIA: PROGRAMACION A      ");
        System.out.print("\n                                  Michael Cobacango                             OBJETOS     ");
        System.out.print("\n\n");
        System.out.print("                      NRC:     6382                                  ING. EDISON LASCANO");
        System.out.print("\n");
        System.out.print("                      CARRERA: SOFTWARE\n");
        System.out.print("                      TEMA: CITAS MEDICAS");
        System.out.print("\n\n");

    }

    public void showMenuUser() {
        int option;
        Scanner scanner = new Scanner(System.in);
        System.out.println("HOSPITAL SYSTEM");
        System.out.println("1.- Solicitar cita medica");
        System.out.print("Ingrese una opcion: ");
        option = scanner.nextInt();
        switch (option) {
            case 1:
                int optionOne;
                System.out.println("Elija un especilaidad ");
                System.out.println("1.- ODONTOLOGY");
                System.out.println("2.- TRAUMATOLOGY");
                System.out.println("3.- PEDIATRIC");
                System.out.print("Ingrese una opcion: ");
                optionOne = scanner.nextInt();
                switch (optionOne) {
                    case 1:
                      
                     break;

                    case 2:
                     break;

                    case 3:
                     break;
                }
         }

    }
    
    public void showMenuAdmin() throws IOException{
        int option;
        Scanner scanner = new Scanner(System.in);
        Appointment appointment = new Appointment();
        System.out.print("Menú: "
                + "\n1: Crear cita"
                + "\n2: Eliminar cita"
                + "\n3: Agregar Médico"
                + "\n4: Mostrar Citas Medicas"
                + "\n5: Mostrar Médicos"
                + "\n6: Agregar nueva Especialidad"
                + "\nDigite su Opción: ");
        option = scanner.nextInt();
        switch(option){
            case 1:          
            break;
            
            case 2: 
            break;
            
            case 3:
                DataEntry dataEntry = new DataEntry();
                dataEntry.dataMedic();
                
            break;
        }
          
    }  

}
