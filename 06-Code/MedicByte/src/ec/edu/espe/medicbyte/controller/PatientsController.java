package ec.edu.espe.medicbyte.controller;

import java.util.List;

import ec.edu.espe.medicbyte.model.Gender;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.service.PatientService;
import ec.edu.espe.medicbyte.service.impl.PatientServiceImpl;
import ec.edu.espe.medicbyte.util.Console;
import ec.edu.espe.medicbyte.util.ConsoleChooser;
import ec.edu.espe.medicbyte.util.StringUtils;

/**
 *
 * @author Andres Jonathan J.
 */
public class PatientsController {
    private final Console console;
    private PatientService patientService;

    public PatientsController() {
        this.console = Console.getInstance();
        this.patientService = new PatientServiceImpl();
    }

    public void showPatients() {
        List<Patient> patients = patientService.getAllPatients();

        if (patients.isEmpty()) {
            console.echoln("No patients found");
            return;
         }
         
         patients.forEach((patient) -> {
             console.echoln(
                "identification: %s\n" + 
                "name: %s\n" +
                "phone: %s\n" +
                "email: %s\n" +
                "---------------------------\n", 
                patient.getIdentificationcard(), patient.getFullName(),
                patient.getPhone(), patient.getEmail()
             );
         });
    }
    
    public void createPatient() {
        Patient patient = new Patient();
        
        console.echoln("Ingreso de datos: ").newLine();
        
        String id = console.input("Cedula de identidad: ", (input) -> {
            boolean valid = StringUtils.isValidCI(input);
            
            if (!valid) {
                console.newLine().echoln("[Cedula incorrecta]");
            }
            
            return valid;
        });
        
        String fullName = console.input("Nombre completo: ");
        
        String rawBirthday = console.input("Fecha de nacimiento (dd/mm/yyyy): ", (input) -> {
            boolean valid = StringUtils.isValidDate(input);
            
            if (!valid) {
                console.newLine().echoln("[Fecha invalida]");
            }
            
            return valid;
        });
        
        String phone = console.input("Telefono: ", (input) -> {
            boolean valid = input.trim().matches("^09[0-9]{8}$")
                || input.trim().matches("^0[23467][0-9]{6,7}$");
            
            if (!valid) {
                console.newLine().echoln("[Telefono incorrecto]");
            }
            
            return valid;
        });
        
        String email = console.input("Correo: ", (input) -> {
            boolean valid = StringUtils.isValidEmail(input);
            
            if (!valid) {
                console.newLine().echoln("[Email invalido]");
            }
            
            return valid;
        });
        
        ConsoleChooser chooser = new ConsoleChooser();
        chooser.addOption("Masculino").addArgument(Gender.MALE);
        chooser.addOption("Femenino").addArgument(Gender.FEMALE);
        chooser.addOption("No especificado").addArgument(Gender.UNIDENTIFIED);
        Gender gender = (Gender) chooser.choose("Genero: ").getArgument(0);
        
        patient.setIdentificationcard(id);
        patient.setEmail(email);
        patient.setPhone(phone);
        patient.setFullName(fullName);
        patient.setGender(gender);
        patient.setBirthday(StringUtils.parseDate(rawBirthday));
        
        PatientService patientService = new PatientServiceImpl();
        patientService.savePatient(patient);
    }
}
