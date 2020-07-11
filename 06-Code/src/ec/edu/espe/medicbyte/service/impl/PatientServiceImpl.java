package ec.edu.espe.medicbyte.service.impl;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import ec.edu.espe.medicbyte.model.Gender;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.service.PatientService;
import ec.edu.espe.medicbyte.service.UserService;
import ec.edu.espe.medicbyte.util.PathUtils;
import ec.edu.espe.medicbyte.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Andres Jonathan J.
 */
public class PatientServiceImpl implements PatientService {
    private Gson gson = new Gson();
    private UserService userService;
    
    @Inject()
    public PatientServiceImpl(UserService userService) {
        this.userService = userService;
        
        PathUtils.ensureFiles(
            new File(PathUtils.currentPath("data/patients.json"))
        );
    }
    
    @Override
    public boolean addPatient(Patient patient) {
        List<Patient> patients = getAllPatients();
        
        boolean exists = patients.stream().filter(current -> {
            return current.getId() == patient.getId();
        }).count() > 0;
        
        if (exists) {
            return false;
        }
        
        Map<String, String> entry = new HashMap<>();
        entry.put("userId", Integer.toString(patient.getId()));
        entry.put("idCard", patient.getIdCard());
        entry.put("fullName", patient.getFullName());
        entry.put("phone", patient.getPhone());
        entry.put("email", patient.getEmail());
        entry.put("gender", patient.getGender().name());
        entry.put("birthday", patient.getBirthday().toString());
        
        String content = gson.toJson(entry);
        File jsonFile = new File(PathUtils.currentPath("data/medics.json"));
        
        try {
            Files.write(content.getBytes(), jsonFile);
        } catch (IOException exception) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public Patient getPatient(String idCard) {
        return getAllPatients().stream()
            .filter(patient -> patient.getIdCard().equalsIgnoreCase(idCard))
            .findFirst()
            .orElse(null);
    }

    @Override
    public Patient getPatient(int userId) {
        return getAllPatients().stream()
            .filter(patient -> patient.getId() == userId)
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Patient> getAllPatients() {
        File json = new File(PathUtils.currentPath("data/patients.json"));
        CharSource source = Files.asCharSource(json, Charsets.UTF_8);
        List<Patient> patients = new ArrayList<>();
        String content;
        
        try {
            content = source.read();
        } catch (IOException exception) {
            return patients;
        }
        
        JsonElement root = JsonParser.parseString(content);
        
        if (!root.isJsonArray()) {
            return patients;
        }
        
        JsonArray items = root.getAsJsonArray();
        
        for (JsonElement item : items) {
            if (!item.isJsonObject()) {
                continue;
            }
            
            JsonObject object = item.getAsJsonObject();
            
            if (!object.has("userId")) {
                continue;
            }
            
            int userId = object.get("userId").getAsInt();
            Patient patient = (Patient) userService.getUser(userId);
            
            if (patient == null) {
                continue;
            }
            
            String idCard = object.get("idCard").getAsString();
            String fullName = object.get("fullName").getAsString();
            String phone = object.get("phone").getAsString();
            String email = object.get("email").getAsString();
            Gender gender = Gender.valueOf(object.get("gender").getAsString());
            Date birthday = StringUtils.parseDate(
                    object.get("birthday").getAsString());
            
            patients.add(patient);
        }
        
        return patients;
    }
    
    @Override
    public int getTotalPatients() {
        return getAllPatients().size();
    }
}
