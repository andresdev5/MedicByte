/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.utils;

import ec.edu.espe.medicbyte.controller.PatientsController;
import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Gender;
import ec.edu.espe.medicbyte.model.ListMedic;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.Speciality;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class DataEntry {

    public Patient addPatient() {
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
        
        PatientsController patients = new PatientsController();
        patients.savePatient(patient);
        
        return patient;
    }

    public void addMedic() {
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = new FileManager("ListMedic.txt");
        Medic medic = new Medic();
        
        System.out.println("Ingrese el nombre del medico");
        medic.setName(scanner.nextLine());
                
        int index = 0;
        int selected = 0;
        
        System.out.println("seleccione la especialidad: ");
        
        for (Speciality speciality : Speciality.values()) {
            System.out.printf("%d: %s\n", index + 1, speciality.getLabel());
            index++;
        }
        
        do {
            System.out.print("especialidad: ");
            selected = scanner.nextInt();
            scanner.nextLine();
        } while (selected <= 0 || selected > Speciality.values().length);
        
        Speciality speciality = Speciality.values()[selected - 1];
        medic.setSpeciality(speciality);
        
        String content = fileManager.readFile();
        int count = (int) Arrays.asList(content.split("\n"))
                .stream()
                .filter(line -> !line.isEmpty())
                .count();
        
        medic.setId(count + 1);
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
            case 1: 
                ListMedic listMedic = new ListMedic();
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
            break;

        }
    }
    
    public void scheduleAppointment() {
        
    }
    
    // id, fecha, hora, id_medico
    public void createAppointment(){
        Scanner scanner = new Scanner(System.in);
        Appointment appointment = new Appointment();
        ListMedic listMedic = new ListMedic();
        
        System.out.println("MEDICOS DISPONIBLES");
        List<Medic> medics = listMedic.getAllMedics();
        int selected;

        listMedic.showListMedic();
        
        do {
            System.out.println("Seleccione el medico: ");
            selected = scanner.nextInt();
            scanner.nextLine();
        } while (selected <= 0 || selected > medics.size());
        
        Medic medic = medics.get(selected - 1);
        appointment.setMedic(medic);
        
        System.out.println("Ingrese un codigo: ");
        appointment.setCode(scanner.nextLine());
        
        System.out.println("Ingrese la fecha: ");
        appointment.setDate(scanner.nextLine());
        
        System.out.println("Ingrese la Hora: ");
        appointment.setHour(scanner.nextLine());
        
        FileManager fileManager = new FileManager("Appointments.txt");
        fileManager.writeFile(appointment.toString());
    }
}
