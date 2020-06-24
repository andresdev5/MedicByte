package ec.edu.espe.medicbyte.controller;

import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Gender;
import ec.edu.espe.medicbyte.model.ListMedic;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.MenuOption;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.utils.Console;
import ec.edu.espe.medicbyte.utils.ConsoleMenu;
import ec.edu.espe.medicbyte.utils.FileManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentsController {
    public void showAppointments() {
    
        
    }
    
    public void createAppointment() {
        Scanner scanner = new Scanner(System.in);
        Appointment appointment = new Appointment();
        ListMedic listMedic = new ListMedic();
        Console console =  Console.getInstance();
        console.echoln("MEDICOS DISPONIBLES");
        List<Medic> medics = listMedic.getAllMedics();
        int selected;

        listMedic.showListMedic();

        do {
            selected = console.readInt("Choose a medic: ");
        } while (selected <= 0 || selected > medics.size());

        Medic medic = medics.get(selected - 1);
        appointment.setMedic(medic);

        appointment.setCode(console.read("Ingrese un codigo: "));
        appointment.setDate(console.read("Ingrese la fecha: "));
        appointment.setHour(console.read("Ingrese la fecha: "));

        FileManager fileManager = new FileManager("Appointments.txt");
        fileManager.writeFile(appointment.toString());
    }
    
    public void takeAppointment() {
        Console console = Console.getInstance();
        ConsoleMenu specialityMenu = new ConsoleMenu();
        
        for (Speciality speciality : Speciality.values()) {
            specialityMenu
                .addOption(speciality.getLabel())
                .addArgument(speciality);
        }
        
        console.newLine();
        specialityMenu.setPrompt("Seleccione la especialidad: ");
        
        MenuOption lastOption = specialityMenu.readOption();
        Speciality speciality = (Speciality) lastOption.getArguments().get(0);
        AppointmentsController controller = new AppointmentsController();
        List<Appointment> appointments = controller.getAllAppointments()
                .stream()
                .filter(appointment -> {
                    return appointment.getMedic().getSpeciality() == speciality;
                }).collect(Collectors.toList());

        if (appointments.isEmpty()) {
            console.echoln("No existen citas medicas en esa especialidad");
            return;
        }
        
        appointments.forEach((appointment) -> {
            String code = appointment.getCode();
            String date = appointment.getDate();
            String doctor = appointment.getMedic().getName();

            console.echofmt(
                "------------------------\n"
                        + "codigo: %s\n"
                        + "fecha: %s\n"
                        + "doctor: %s\n"
                        + "disponibilidad: %s\n"
                        + "------------------------\n",
                code, date, doctor,
                (appointment.isTaken() ? "no disponible" : "disponible")
            );
        });

        String choosed = console.read("Desea crear una cita? [s/n]: ", (input) -> {
            return input.trim().toLowerCase().matches("^(s|n)$");
        });

        boolean doCreate = choosed.equals("s");
        
        if (doCreate) {
            String selectedCode = "-1";
            String code = console.read(
                "Ingrese el codigo de la cita o -1 para salir: ",
                (input) -> {
                    if (input.equals("-1")) {
                        return true;
                    }

                    Appointment found = appointments.stream()
                        .filter((appointment) -> {
                            return appointment.getCode().trim()
                                    .equalsIgnoreCase(input);
                        }).findFirst().orElse(null);
                    
                    boolean valid = found != null && !found.isTaken();
                    
                    if (found == null) {
                        console.newLine().echoln("codigo incorrecto o la cita no existe");
                    } else if (!valid) {
                        console.newLine().echoln("cita no disponible");
                    }
                    
                    return valid;
                }
            );

            if (code.equals("-1")) {
                return;
            }
            
            Patient patient = createPatient();

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
    }
    
    public void deleteAppointment() {}
    
    public List<Appointment> getAllAppointments() {
        FileManager fileManager = new FileManager("Appointments.txt");
        List<Appointment> appointments = new ArrayList<>();
        ListMedic listmedic = new ListMedic();
        String content = fileManager.readFile();

        // code,date,hour,id_medic
        for (String line : content.split("\n")) {
            String tokens[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            Appointment appointment = new Appointment();
            appointment.setCode(tokens[0]);
            appointment.setDate(tokens[1]);
            appointment.setHour(tokens[2]);

            int medicId = Integer.parseInt(tokens[3]);
            appointment.setMedic(listmedic.getMedic(medicId));

            boolean isTaken = Integer.parseInt(tokens[4]) != 0;
            appointment.setTaken(isTaken);

            appointments.add(appointment);
        }

        return appointments;
    }
    
    private Patient createPatient() {
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
}
