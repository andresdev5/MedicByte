package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.MedicService;
import ec.edu.espe.medicbyte.service.SpecialityService;
import ec.edu.espe.medicbyte.service.UserService;
import ec.edu.espe.medicbyte.util.PathUtils;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Andres Jonathan J.
 */
public class MedicServiceImpl implements MedicService {    
    private Gson gson = new Gson();
    private UserService userService;
    private SpecialityService specialityService;
    
    
    @Inject()
    public MedicServiceImpl(UserService userService, SpecialityService specialityService) {
        this.userService = userService;
        this.specialityService = specialityService;
        
        PathUtils.ensureFiles(
            new File(PathUtils.currentPath("data/medics.json"))
        );
    }
    
    @Override
    public Medic getMedic(int id) {
        return getAllMedics().stream()
            .filter(medic -> medic.getId() == id)
            .findFirst().get();
    }
    
    @Override
    public boolean addMedic(User user, Speciality speciality) {
        Medic medic = (Medic) user;
        medic.setSpeciality(speciality);
        return addMedic(medic);
    }
    
    @Override
    public boolean addMedic(Medic medic) {
        List<Medic> medics = getAllMedics();
        
        boolean exists = medics.stream().filter(current -> {
            return current.getId() == medic.getId();
        }).count() > 0;
        
        if (exists) {
            return false;
        }
        
        Map<String, Integer> entry = new HashMap<>();
        entry.put("userId", medic.getId());
        entry.put("specialityId", medic.getSpeciality().getId());
        
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
    public List<Medic> getAllMedics() {
        File json = new File(PathUtils.currentPath("data/medics.json"));
        CharSource source = Files.asCharSource(json, Charsets.UTF_8);
        List<Medic> medics = new ArrayList<>();
        String content;
        
        try {
            content = source.read();
        } catch (IOException exception) {
            return medics;
        }
        
        JsonElement root = JsonParser.parseString(content);
        
        if (!root.isJsonArray()) {
            return medics;
        }
        
        JsonArray items = root.getAsJsonArray();
        
        for (JsonElement item : items) {
            if (!item.isJsonObject()) {
                continue;
            }
            
            JsonObject object = item.getAsJsonObject();
            
            if (!object.has("userId") || !object.has("specialityId")) {
                continue;
            }
            
            int userId = object.get("userId").getAsInt();
            int specialityId = object.get("specialityId").getAsInt();
            
            Medic medic = (Medic) userService.getUser(userId);
            Speciality speciality = specialityService.getSpeciality(specialityId);
            
            if (medic == null) {
                continue;
            }
            
            medic.setSpeciality(speciality);
            medics.add(medic);
        }
        
        return medics;
    }
    
    @Override
    public int getTotalMedics() {
        return getAllMedics().size();
    }
}
