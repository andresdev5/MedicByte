package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.model.Gender;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.service.PatientService;
import ec.edu.espe.tinyio.FileLine;
import ec.edu.espe.tinyio.FileManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class PatientServiceImpl implements PatientService {
    private FileManager fileManager;
    private final String DATA_FILENAME = "patients.csv";
    
    public PatientServiceImpl() {
        try {
            this.fileManager = new FileManager(DATA_FILENAME, true);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    @Override
    public void savePatient(Patient patient) {
        fileManager.write(patient.toString());
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        List<FileLine> lines = fileManager.read();

        // code,date,hour,id_medic
        for (FileLine line : lines) {
            Patient patient = csvLineToPatient(line.text());
            patients.add(patient);
        }

        return patients;
    }
    
    @Override
    public int getTotalPatients() {
        return fileManager.countLines();
    }

    @Override
    public Patient getPatient(String identification) {
        FileLine found = fileManager.findFirst((line) -> {
            Patient patient = csvLineToPatient(line.text());
            return patient.getIdentificationcard()
                .trim().equalsIgnoreCase(identification);
        });
        
        if (found == null) {
            return null;
        }
        
        return csvLineToPatient(found.text());
    }
    
    public Patient csvLineToPatient(String line) {
        String tokens[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        Patient patient = new Patient();

        // cedula, surname, name, phone, email. gender, age
        patient.setIdentificationcard(tokens[0]);
        patient.setSurname(tokens[1]);
        patient.setName(tokens[2]);
        patient.setPhone(tokens[3]);
        patient.setEmail(tokens[4]);

        Gender gender = Gender.UNIDENTIFIED;

        if (tokens[5].equals("FEMALE")) {
            gender = Gender.FEMALE;
        } else if (tokens[5].equals("MALE")) {
            gender = Gender.MALE;
        }

        patient.setGender(gender);

        int age = Integer.parseInt(tokens[6]);
        patient.setAge(age);
        
        return patient;
    }
}
