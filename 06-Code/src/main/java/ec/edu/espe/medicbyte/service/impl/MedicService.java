package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.util.PathUtils;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import ec.edu.espe.medicbyte.util.MedicModelSerializer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ec.edu.espe.medicbyte.service.IMedicService;
import ec.edu.espe.medicbyte.service.IMedicService;
import ec.edu.espe.medicbyte.service.ISpecialityService;
import ec.edu.espe.medicbyte.service.ISpecialityService;
import ec.edu.espe.medicbyte.service.IUserService;
import ec.edu.espe.medicbyte.service.IUserService;

/**
 *
 * @author Andres Jonathan J.
 */
public class MedicService implements IMedicService {
    private Gson gson;
    private IUserService userService;
    private ISpecialityService specialityService;
    
    @Inject()
    public MedicService(IUserService userService, ISpecialityService specialityService) {
        this.userService = userService;
        this.specialityService = specialityService;
        
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Medic.class, new MedicModelSerializer());
        gson = gsonBuilder.create();
        
        PathUtils.ensureFiles(
            PathUtils.currentPath("data/medics.json")
        );
    }
    
    @Override
    public Medic getMedic(int id) {
        return getAllMedics().stream()
            .filter(medic -> medic.getId() == id)
            .findFirst().orElse(null);
    }
    
    @Override
    public boolean addMedic(User user, Speciality speciality, String fullName) {
        Medic medic = new Medic(user);
        medic.setSpeciality(speciality);
        medic.getProfile().setFullName(fullName);
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
        
        medics.add(medic);
        
        String content = gson.toJson(medics.toArray());
        File jsonFile = PathUtils.currentPath("data/medics.json").toFile();
        
        try {
            Files.write(content.getBytes(), jsonFile);
        } catch (IOException exception) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public List<Medic> getAllMedics() {
        File json = PathUtils.currentPath("data/medics.json").toFile();
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
            
            User user = userService.getUser(userId);
            user.setProfile(userService.getUserProfile(userId));
            
            Medic medic = new Medic(user);
            Speciality speciality = specialityService.getSpeciality(specialityId);
            
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
