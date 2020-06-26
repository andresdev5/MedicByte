package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.model.Gender;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.service.PatientService;
import ec.edu.espe.medicbyte.util.StringUtils;
import ec.edu.espe.tinyio.CsvFile;
import ec.edu.espe.tinyio.CsvRecord;
import ec.edu.espe.tinyio.FileLine;
import ec.edu.espe.tinyio.FileManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class PatientServiceImpl implements PatientService {
    private final FileManager patientsDb;
    
    public PatientServiceImpl() {
        this.patientsDb = new FileManager("data/patients.csv", true);
    }
    
    @Override
    public void savePatient(Patient patient) {
        patientsDb.write(patient.toString());
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        CsvFile csv = patientsDb.toCsv(
            "identificationcard", "firstName", "lastName", 
            "phone", "email", "gender", "age"
        );
        
        // code,date,hour,id_medic
        for (CsvRecord record : csv.getRecords()) {
            patients.add(csvRecordToPatient(record));
        }

        return patients;
    }
    
    @Override
    public int getTotalPatients() {
        return patientsDb.countLines();
    }

    @Override
    public Patient getPatient(String identification) {
        FileLine found = patientsDb.findFirst((line) -> {
            Patient patient = csvRecordToPatient(line.csv());
            return patient.getIdentificationcard()
                .trim().equalsIgnoreCase(identification);
        });
        
        if (found == null) {
            return null;
        }
        
        return csvRecordToPatient(found.csv());
    }
    
    public Patient csvRecordToPatient(CsvRecord record) {
        Patient patient = new Patient();
        List<String> values = record.getColumnValues();
        
        // cedula, surname, name, phone, email. gender, age
        patient.setIdentificationcard(values.get(0));
        patient.setFullName(values.get(1));
        patient.setPhone(values.get(2));
        patient.setEmail(values.get(3));

        Gender gender = Gender.UNIDENTIFIED;

        if (values.get(4).equals("FEMALE")) {
            gender = Gender.FEMALE;
        } else if (values.get(5).equals("MALE")) {
            gender = Gender.MALE;
        }

        patient.setGender(gender);
        patient.setBirthday(StringUtils.parseDate(values.get(5)));
        
        return patient;
    }
}
