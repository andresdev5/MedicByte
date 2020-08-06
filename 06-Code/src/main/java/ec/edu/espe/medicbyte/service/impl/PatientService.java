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
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.util.PathUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ec.edu.espe.medicbyte.service.IPatientService;
import ec.edu.espe.medicbyte.service.IUserService;

/**
 *
 * @author Andres Jonathan J.
 */
public class PatientService implements IPatientService {
    private Gson gson = new Gson();
    private IUserService userService;
    
    @Inject()
    public PatientService(IUserService userService) {
        this.userService = userService;
        
        PathUtils.ensureFiles(
            PathUtils.currentPath("data/patients.json")
        );
    }
    
    @Override
    public Patient addPatient(int userId, String idCard, boolean affiliated) {
        Patient patient = new Patient();
        patient.setId(userId);
        patient.setIdCard(idCard);
        patient.setAffiliated(false);
        
        return addPatient(patient);
    }
    
    @Override
    public Patient addPatient(Patient patient) {
        List<Patient> patients = getAllPatients();
        
        boolean exists = patients.stream().filter(current -> {
            return current.getId() == patient.getId();
        }).count() > 0;
        
        if (exists) {
            return null;
        }
        
        List<Map<String, Object>> entries = new ArrayList<>();
        
        patients.add(patient);
        patients.forEach(p -> {
            Map<String, Object> entry = new HashMap<>();
            entry.put("userId", p.getId());
            entry.put("idCard", p.getIdCard());
            entry.put("affiliated", p.isAffiliated());
            
            entries.add(entry);
        });
        
        String content = gson.toJson(entries);
        File jsonFile = PathUtils.currentPath("data/patients.json").toFile();
        
        try {
            Files.write(content.getBytes(), jsonFile);
        } catch (IOException exception) {
            return null;
        }
        
        return getPatient(patient.getId());
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
        File json = PathUtils.currentPath("data/patients.json").toFile();
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
            User user = userService.getUser(userId);
            Patient patient = new Patient();
            
            patient.setId(user.getId());
            patient.setUsername(user.getUsername());
            patient.setPassword(user.getPassword());
            patient.setEmail(user.getEmail());
            patient.setRole(user.getRole());
            patient.setProfile(user.getProfile());
            
            String idCard = object.get("idCard").getAsString();
            patient.setIdCard(idCard);
            
            boolean affiliated = object.get("affiliated").getAsBoolean();
            patient.setAffiliated(affiliated);
            
            patients.add(patient);
        }
        
        return patients;
    }
    
    @Override
    public int getTotalPatients() {
        return getAllPatients().size();
    }
}
