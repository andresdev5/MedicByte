package ec.edu.espe.medicbyte.controller;

import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.utils.MenuOption;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.service.AppointmentService;
import ec.edu.espe.medicbyte.service.impl.AppointmentServiceImpl;
import ec.edu.espe.medicbyte.service.MedicService;
import ec.edu.espe.medicbyte.service.impl.MedicServiceImpl;
import ec.edu.espe.medicbyte.service.PatientService;
import ec.edu.espe.medicbyte.service.impl.PatientServiceImpl;
import ec.edu.espe.medicbyte.utils.Console;
import ec.edu.espe.medicbyte.utils.ConsoleMenu;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentsController {
    private final Console console;
    private final AppointmentService appointmentService;
    
    public AppointmentsController() {
        this.console = Console.getInstance();
        this.appointmentService = new AppointmentServiceImpl();
    }
    
    public void showAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        
        if (appointments.isEmpty()) {
            console.echoln("no records found");
        }
        
        appointments.stream().forEach((appointment) -> {
            console.echoln("---------------------------------------");
            console.echofmt("id: %d\n", appointment.getId());
            console.echofmt("date: %s\n", appointment.getDate());
            console.echofmt("hour: %s\n", appointment.getHour());
            console.echofmt("medic: %s\n", appointment.getMedic().getName());
            console.echofmt("medic: %s\n", 
                    appointment.getMedic().getSpeciality().getLabel());
        });
    }
    
    /**
     * Crear una cita medica
     * 
     */
    public void createAppointment() {
        Appointment appointment = new Appointment();
        ConsoleMenu medicsMenu = new ConsoleMenu();
        MedicService medicService = new MedicServiceImpl();
        List<Medic> medics = medicService.getAllMedics();
        
        if (medics.isEmpty()) {
            console.echoln("No hay medicos disponibles");
            return;
        }
        
        medics.forEach((medic) -> {
            medicsMenu.addOption(medic.getName())
                .setAwait(false)
                .addArgument(medic);
        });
        
        medicsMenu.setPrompt("Choose a medic: ");
        Medic selected = (Medic) medicsMenu.process().getArgument(0);
        
        appointment.setId(appointmentService.getTotalAppointments() + 1);
        appointment.setMedic(selected);
        appointment.setDate(console.read("Ingrese la fecha: "));
        appointment.setHour(console.read("Ingrese la hora: "));
        
        appointmentService.saveAppointment(appointment);
        console.echoln("Successfully created appointment!");
    }
    
    /**
     * Solicitar una cita medica
     */
    public void takeAppointment() {
        ConsoleMenu specialityMenu = new ConsoleMenu();
        
        for (Speciality speciality : Speciality.values()) {
            specialityMenu
                .addOption(speciality.getLabel())
                .setAwait(false)
                .addArgument(speciality);
        }
        
        console.newLine();
        specialityMenu.setPrompt("Seleccione la especialidad: ");
        
        MenuOption lastOption = specialityMenu.process();
        Speciality speciality = (Speciality) lastOption.getArguments().get(0);
        AppointmentsController controller = new AppointmentsController();
        List<Appointment> appointments = appointmentService.getAllAppointments()
            .stream()
            .filter(appointment -> {
                return appointment.getMedic().getSpeciality() == speciality;
            }).collect(Collectors.toList());

        if (appointments.isEmpty()) {
            console.echoln("No existen citas medicas en esa especialidad");
            return;
        }
        
        appointments.forEach((appointment) -> {
            int id = appointment.getId();
            String date = appointment.getDate();
            String doctor = appointment.getMedic().getName();

            console.echofmt(
                "------------------------\n"
                        + "id: %d\n"
                        + "fecha: %s\n"
                        + "doctor: %s\n"
                        + "disponibilidad: %s\n"
                        + "------------------------\n",
                id, date, doctor,
                (appointment.isTaken() ? "no disponible" : "disponible")
            );
        });

        String choosed = console.read("Desea crear una cita? [s/n]: ", (input) -> {
            return input.trim().toLowerCase().matches("^(s|n)$");
        });

        boolean doCreate = choosed.equals("s");
        
        if (doCreate) {
            String selectedCode = "-1";
            String id = console.read(
                "Ingrese el id de la cita o -1 para salir: ",
                (input) -> {
                    if (input.equals("-1")) {
                        return true;
                    }

                    Appointment found = appointments.stream()
                        .filter((appointment) -> {
                            return appointment.getId() == Integer.parseInt(input);
                        }).findFirst().orElse(null);
                    
                    boolean valid = found != null && !found.isTaken();
                    
                    if (found == null) {
                        console.newLine().echoln("id incorrecto o la cita no existe");
                    } else if (!valid) {
                        console.newLine().echoln("cita no disponible");
                    }
                    
                    return valid;
                }
            );

            if (!id.equals("-1")) {
                // TODO: validate CI
                String identification = console.read("ingrese su cedula: ");
                PatientService patientService = new PatientServiceImpl();
                Patient patient = patientService.getPatient(identification);
                
                if (patient == null) {
                    console.echoln("Paciente no encontrado");
                    
                    String option = console.read("Desea registrarse? (y/n): ", (input) -> {
                        return input.trim().equalsIgnoreCase("y")
                            || input.trim().equalsIgnoreCase("n");
                    });
                    
                    if (option.equals("y")) {
                        PatientsController patientsController;
                        patientsController = new PatientsController();
                        patientsController.createPatient();
                    }
                    
                    int count = patientService.getTotalPatients();
                    patient = patientService.getAllPatients().get(count - 1);
                }
                
                int appointmentID = Integer.parseInt(id);
                appointmentService.addPatientToAppointment(
                        patient.getIdentificationcard(), appointmentID);
                Appointment appointment = appointmentService.getAppointment(appointmentID);

                appointment.setTaken(true);
                appointmentService.updateAppointment(appointment);
            }
        }
    }
    
    public void deleteAppointment() {
        int id = console.readInt("Enter appointment id to delete: ");
        Appointment appointment = appointmentService.getAppointment(id);
        
        if (appointment == null) {
            console.echoln("appointment not found");
        } else {
            appointmentService.deleteAppointment(appointment);
        }
    }
}
