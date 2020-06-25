package ec.edu.espe.medicbyte.controller;

import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.service.AppointmentService;
import ec.edu.espe.medicbyte.service.impl.AppointmentServiceImpl;
import ec.edu.espe.medicbyte.service.MedicService;
import ec.edu.espe.medicbyte.service.impl.MedicServiceImpl;
import ec.edu.espe.medicbyte.service.PatientService;
import ec.edu.espe.medicbyte.service.impl.PatientServiceImpl;
import ec.edu.espe.medicbyte.util.Console;
import ec.edu.espe.medicbyte.util.ConsoleChooser;
import ec.edu.espe.medicbyte.util.StringUtils;
import java.time.LocalTime;
import java.util.Date;
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
    
    /**
     * Show an appointments list
     * 
     */
    public void showAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        
        if (appointments.isEmpty()) {
            console.echoln("no records found");
        }
        
        appointments.stream().forEach((appointment) -> {
            console.echoln("---------------------------------------");
            console.echoln("id: %d", appointment.getId());
            console.echoln("date: %s", appointment.getFormatDate());
            console.echoln("hour: %s", appointment.getHour());
            console.echoln("medic: %s", appointment.getMedic().getName());
            console.echoln("speciality: %s", 
                    appointment.getMedic().getSpeciality().getLabel());
        });
    }
    
    /**
     * create a new appointment
     * 
     */
    public void createAppointment() {
        Appointment appointment = new Appointment();
        ConsoleChooser medicsChooser = new ConsoleChooser();
        MedicService medicService = new MedicServiceImpl();
        List<Medic> medics = medicService.getAllMedics();
        
        if (medics.isEmpty()) {
            console.echoln("No hay medicos disponibles");
            return;
        }
        
        medics.forEach((medic) -> {
            medicsChooser.addOption(medic.getName()).addArgument(medic);
        });
        
        Medic selected = (Medic) medicsChooser.choose("Choose a medic: ")
            .getArgument(0);
        
        String rawDate = console.input("Ingrese la fecha (dd/mm/yyyy): ", (input) -> {
            return StringUtils.isValidDate(input);
        });
        
        String rawHour = console.input("Ingrese la hora (HH:mm): ", (input) -> {
            return StringUtils.isValidHour(input);
        });
        
        Date date = StringUtils.parseDate(rawDate);
        LocalTime hour = StringUtils.parseHour(rawHour);
        
        appointment.setId(appointmentService.getLastId() + 1);
        appointment.setMedic(selected);
        appointment.setDate(date);
        appointment.setHour(hour);
        
        appointmentService.saveAppointment(appointment);
        console.newLine().echoln("Successfully created appointment!");
    }
    
    /**
     * Request an appointment
     * 
     */
    public void takeAppointment() {
        ConsoleChooser specialityChooser = new ConsoleChooser();
        
        for (Speciality speciality : Speciality.values()) {
            specialityChooser
                .addOption(speciality.getLabel())
                .addArgument(speciality);
        }
        
        console.newLine();        
        Speciality speciality = (Speciality) specialityChooser
            .choose("Seleccione la especialidad: ")
            .getArgument(0);
        AppointmentsController controller = new AppointmentsController();
        List<Appointment> appointments = appointmentService.getAllAppointments()
            .stream()
            .filter(appointment -> {
                return appointment.getMedic().getSpeciality() == speciality
                    && !appointment.isTaken();
            }).collect(Collectors.toList());

        if (appointments.isEmpty()) {
            console.echoln("No existen citas disponibles en esa especialidad");
            return;
        }
        
        boolean viewAppointments = Console.confirm(
            String.format("Existen %d citas disponibles, desea verlas?", 
                    appointments.size()));
        
        if (viewAppointments) {
            console.newLine(2);
            
            appointments.forEach((appointment) -> {
                int id = appointment.getId();
                Date date = appointment.getDate();
                LocalTime hour = appointment.getHour();
                String doctor = appointment.getMedic().getName();

                console.echoln(
                    "------------------------\n"
                    + "id: %d\n"
                    + "fecha: %s\n"
                    + "hora: %s\n"
                    + "doctor: %s\n"
                    + "------------------------\n",
                    id, date.toString(), hour.toString(), doctor,
                    (appointment.isTaken() ? "no disponible" : "disponible")
                );
            });
        }
        
        console.newLine(2);
        boolean createAppointment = Console.confirm("Desea crear una cita?");
        
        if (createAppointment) {
            String selectedCode = "-1";
            String id = console.input(
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
                String identification = console.input("Cedula de identidad: ", (input) -> {
                    boolean valid = StringUtils.isValidCI(input);

                    if (!valid) {
                        console.newLine().echoln("[Cedula incorrecta]");
                    }

                    return valid;
                });
                
                PatientService patientService = new PatientServiceImpl();
                Patient patient = patientService.getPatient(identification);
                
                if (patient == null) {
                    console.newLine().echoln("Paciente no encontrado");
                    boolean registerNewPatient = Console.confirm("Desea registrarse? ");
                    
                    if (registerNewPatient) {
                        PatientsController patientsController;
                        patientsController = new PatientsController();
                        patientsController.createPatient();
                    } else {
                        return;
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
                
                console.newLine().echoln("Cita agendada con exito!");
            }
        }
    }
    
    /**
     * Delete an appointment by id
     * 
     */
    public void deleteAppointment() {
        int id = console.input(Integer.class, "Enter appointment id to delete: ");
        Appointment appointment = appointmentService.getAppointment(id);
        
        if (appointment == null) {
            console.newLine().echoln("Appointment not found");
        } else {
            appointmentService.deleteAppointment(appointment);
            console.newLine().echoln("Appointment deleted");
        }
    }
    
    public void showScheduledAppointments() {
        String id = console.input("Cedula de identidad: ", (input) -> {
            boolean valid = StringUtils.isValidCI(input);
            
            if (!valid) {
                console.newLine().echoln("[Cedula incorrecta]");
            }
            
            return valid;
        });
        
        List<Appointment> appointments = appointmentService
            .getPatientAppointments(id);
        
        if (appointments.isEmpty()) {
            console.echoln("No se encontraron citas");
            return;
        }
        
        appointments.stream().forEach((appointment) -> {
            console.echoln("---------------------------------------");
            console.echoln("id: %d", appointment.getId());
            console.echoln("fecha: %s", appointment.getFormatDate());
            console.echoln("hora: %s", appointment.getHour());
            console.echoln("medico: %s", appointment.getMedic().getName());
            console.echoln("especialidad: %s", 
                    appointment.getMedic().getSpeciality().getLabel());
        });
    }
}
