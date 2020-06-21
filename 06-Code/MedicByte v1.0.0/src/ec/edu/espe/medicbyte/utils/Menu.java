/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.utils;

import ec.edu.espe.medicbyte.model.Odontology;
import java.util.Scanner;



/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class Menu {
    
<<<<<<< HEAD
    public void caratula(){
=======
    public void caratula() {
>>>>>>> 7def94a0563082b548ceeebfcbd4376ae3087448
        
    
        
        System.out.print("\n\t\t================================================================================== ");
    System.out.print("\n                                               || ****** ****** ****** ******   ");
    System.out.print("\n                              UNIVERSIDAD   "+"   || *      *      *    * *      ");
    System.out.print("\n                              DE LAS FUERZAS"+"   || ****** ****** ****** ****** ");
    System.out.print("\n                              ARMADAS       "+"   || *           * *      *      ");
    System.out.print("\n                                               || ****** ****** *      ******   ");
    System.out.print("\n\t\t=================================================================================== ");
    System.out.print("\n\n");
    System.out.print("                      INTEGRANTES: Junior Jurado                     MATERIA: PROGRAMACION A      ");
    System.out.print("\n                                 Michael Cobacango                             OBJETOS     ");
    System.out.print("\n\n");
    System.out.print("                      NRC:     6382                                  ING. EDISON LASCANO");
    System.out.print("\n");
    System.out.print("                      CARRERA: SOFTWARE\n");
    System.out.print("                      TEMA: CITAS MEDICAS");
    System.out.print("\n\n");
   
    } 
    
    public void menu(){
        
        int option;
        Scanner scanner = new Scanner(System.in);
        System.out.println("HOSPITAL SYSTEM");
        System.out.println("1.- Solicitar cita medica");
        System.out.print("Ingrese una opcion: ");
        option = scanner.nextInt();
        switch(option){
            case 1 :
                int optionOne;
                System.out.println("Elija un especilaidad ");
                System.out.println("1.- ODONTOLOGY");
                System.out.println("2.- TRAUMATOLOGY");
                System.out.println("3.- PEDIATRIC");
                System.out.print("Ingrese una opcion: ");
                optionOne = scanner.nextInt();
                switch(optionOne){
                    case 1 :
                       Odontology odontology = new Odontology();
                       odontology.citaOdontologo();
                     break;
                     
                    case 2 :
                     break;
                     
                    case 3 : 
                     break;   
                       
                     
                }
               
             break;
        
        }
      
        
    }
    
   
    
}
