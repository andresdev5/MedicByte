/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.utils;

import ec.edu.espe.medicbyte.controller.AppointmentsController;
import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.Speciality;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        System.out.print("                      INTEGRANTES: JUNIOR JURADO                    MATERIA: PROGRAMACION A      ");
        System.out.print("\n                                 MICHAEL COBACANDO                           OBJETOS     ");
        System.out.print("\n                                 ANDRES JACOME \n");
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

                AppointmentsController controller = new AppointmentsController();
                List<Appointment> appointments = controller.getAllAppointments()
                        .stream()
                        .filter(appointment -> {
                            return appointment.getMedic().getSpeciality() == speciality;
                        }).collect(Collectors.toList());

                if (appointments.isEmpty()) {
                    System.out.println("No existen citas medicas en esa especialidad");
                    return;
                }

                for (Appointment appointment : appointments) {
                    String code = appointment.getCode();
                    String date = appointment.getDate();
                    String doctor = appointment.getMedic().getName();

                    System.out.printf(
                            "------------------------\n"
                            + "codigo: %s\n"
                            + "fecha: %s\n"
                            + "doctor: %s\n"
                            + "disponibilidad: %s\n"
                            + "------------------------\n",
                            code, date, doctor,
                            (appointment.isTaken() ? "no disponible" : "disponible")
                    );
                }

                boolean doCreate = false;

                do {
                    System.out.println("Desea crear una cita? [y/n]: ");
                    String choosed = scanner.nextLine().toLowerCase();

                    if (choosed.equals("y") || choosed.equals("n")) {
                        doCreate = choosed.equals("y");
                        break;
                    }
                } while (true);

                if (doCreate) {
                    DataEntry dataEntry = new DataEntry();
                    Patient patient = dataEntry.addPatient();
                    String selectedCode;
                    
                    do {
                        System.out.print("Ingrese el codigo de la cita: ");
                        String code = scanner.nextLine().trim().toLowerCase();
                        Appointment found = appointments.stream()
                                .filter(appointment -> {
                                    return appointment.getCode().trim()
                                            .equalsIgnoreCase(code);
                                }).findFirst().orElse(null);
                            
                        if (found != null && !found.isTaken()) {
                            selectedCode = code;
                            break;
                        }
                        
                        if (found != null && found.isTaken()) {
                            System.out.println("\n[cita no disponible]");
                        }
                    } while (true);

                    FileManager fileManager = new FileManager("user_appointments.txt");
                    fileManager.writeFile(String.format(
                            "%s, %s", selectedCode, patient.getIdentificationcard()));

                    FileManager fileManager2 = new FileManager("Appointments.txt");
                    List<Appointment> appointments2 = controller.getAllAppointments();

                    fileManager2.clear();

                    for (Appointment appointment : appointments2) {
                        if (appointment.getCode().trim().equalsIgnoreCase(selectedCode)) {
                            appointment.setTaken(true);
                        }

                        fileManager2.writeFile(appointment.toString());
                    }
                }

                break;
        }

    }

    public void showMenuAdmin() throws IOException {
        int option;
        Scanner scanner = new Scanner(System.in);
        DataEntry dataEntry = new DataEntry();
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
        switch (option) {
            case 1:
                dataEntry.createAppointment();
                break;

            case 2:
                break;

            case 3:
                dataEntry.addMedic();
                break;
            case 4:
                break;
            case 5:
                dataEntry.showMedic();
                break;
        }

    }

}
