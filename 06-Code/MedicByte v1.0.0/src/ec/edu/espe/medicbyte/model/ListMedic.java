/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 *
 * @author Michael Cobacango -ESPE -DCCO
 */
public class ListMedic {

    Collection<Medic> listMedic = new ArrayList();
    
    Medic medic = new Medic("1722066956", "Michael", "Cobacango",
            "Odontologo", "0999082067", "michaelpm63@gmail.com", Gender.MALE);
    Medic medic1 = new Medic("1710379346", "Alfonso", "Cadena",
            "Cirujano", "0985623744", "alfonso@gmail.com", Gender.MALE);
    Medic medic2 = new Medic("1714548846", "Juan", "Pardo",
            "Pediatra", "0958421365", "juanpa@gmail.com", Gender.MALE);
    Medic medic3 = new Medic("1711558915", "Maria", "Salazar",
            "Odontologa", "0985474547", "mariasa@gmail.com", Gender.FEMALE);
    Medic medic4 = new Medic("1723658945", "Paola", "Suarez",
            "Pediatra", "0936541225", "paolasu@gmail.com", Gender.FEMALE);
    Medic medic5 = new Medic("1752684123", "Damaris", "Cartagena",
            "Odontologa", "0947521635", "damaris@gmail.com", Gender.FEMALE);
    
    public void enterMedic() {
        listMedic.add(medic);
        listMedic.add(medic1);
        listMedic.add(medic2);
        listMedic.add(medic3);
        listMedic.add(medic4);
        listMedic.add(medic5);
    }
    
    public void showListMedic() {
        for (Medic medics : listMedic) {
            System.out.println(medics.toString());
        }
    }
    
    public void chooseMedic() {
        
        Scanner dataEntry = new Scanner(System.in);
        int modify;
        System.out.print("Modificar: \n1: Médico 1"
                + "\n2: Médico 2 \n3: Médico 3"
                + "\n4: Médico 4 \n5: Médico 5"
                + "\n6: Médico 6"
                + "\nDigite su Opción: ");
        modify = dataEntry.nextInt();
        switch (modify) {
            case 1:
                modifyData(medic);
                break;
            case 2:
                modifyData(medic1);
                break;
            case 3:
                modifyData(medic2);
                break;
            case 4:
                modifyData(medic3);
                break;
            case 5:
                modifyData(medic4);
                break;
            case 6:
                modifyData(medic5);
                break;
            default:
                System.out.println("No se Encontro ese Médico");
        }
    }
    
    public void modifyData(Medic medic) {
        //Corregir el ingreso del nuevo dato a ser cambiado
        Scanner dataEntry = new Scanner(System.in);
        int modifyData;
        System.out.print("Modificar: "
                + "\n1: Cédula: \n2: Apellido"
                + "\n3: Nombre \n4: Especialidad"
                + "\n5: Teléfono \n6: Email"
                + "\n7: Género"
                + "\nDigite su Opción: ");
        modifyData = dataEntry.nextInt();
        switch (modifyData) {
            case 1:
                System.out.print("Ingrese el nuevo # de Cédula: ");
                medic.setIdentificationcard(dataEntry.nextLine());
                System.out.println("\nNuevo Número de Cédula: " + medic.getIdentificationcard());
                break;
            case 2:
                System.out.print("Ingrese el nuevo Nombre: ");
                medic.setName(dataEntry.nextLine());
                System.out.println("\nNuevo Nombre: " + medic.getName());
                break;
            case 3:
                System.out.print("Ingrese el nuevo Apellido: ");
                medic.setSurname(dataEntry.nextLine());
                System.out.println("\nNuevo Apellido: " + medic.getSurname());
                break;
            case 4:
                System.out.print("Ingrese la nueva Especialidad: ");
                medic.setSpeciality(dataEntry.nextLine());
                System.out.println("\nNueva Especialidad: " + medic.getSpeciality());
                break;
            case 5:
                System.out.print("Ingrese el nuevo # Télefono: ");
                medic.setPhone(dataEntry.nextLine());
                System.out.println("\nNuevo # Telefono: " + medic.getPhone());
                break;
            case 6:
                System.out.print("Ingrese el nuevo Correo: ");
                medic.setEmail(dataEntry.nextLine());
                System.out.println("\nNuevo Correo: " + medic.getEmail());
                break;
            case 7:
                System.out.print("Ingrese el nuevo Genero: ");
                medic.setGender(Gender.valueOf(dataEntry.nextLine()));
                System.out.println("\nNuevo Genero: " + medic.getGender());
                break;
            default:
                System.out.println("No se encontraron coincidencias");
            
        }
    }
}
