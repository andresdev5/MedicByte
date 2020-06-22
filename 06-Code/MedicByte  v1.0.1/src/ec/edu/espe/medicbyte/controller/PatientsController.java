package ec.edu.espe.medicbyte.controller;

import ec.edu.espe.medicbyte.model.Gender;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.utils.FileManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class PatientsController {

    public void savePatient(Patient patient) {
        FileManager fileManager = new FileManager("Patients.txt");
        fileManager.writeFile(patient.toString());
    }

    public List<Patient> getPatients() {
        FileManager fileManager = new FileManager("Patients.txt");
        List<Patient> patients = new ArrayList<>();
        String content = fileManager.readFile();

        // code,date,hour,id_medic
        for (String line : content.split("\n")) {
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

            patients.add(patient);
        }

        return patients;
    }
}
