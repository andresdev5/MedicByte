package ec.edu.espe.medicbyte.service.impl;

import com.google.common.io.Files;
import com.google.gson.Gson;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.SpecialityService;
import ec.edu.espe.medicbyte.util.PathUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class SpecialityServiceImpl implements SpecialityService {
    Gson gson = new Gson();
    
    public SpecialityServiceImpl() {
        PathUtils.ensureFiles(
            new File(PathUtils.currentPath("data/specialities.json"))
        );
    }
    
    @Override
    public boolean addSpeciality(Speciality speciality) {
        List<Speciality> specialities = getAllSpecialities();
        
        if (getSpeciality(speciality.getId()) != null
            || getSpeciality(speciality.getName()) != null) {
            return false;
        }
        
        speciality.setId(getTotalSpecialities() + 1);
        specialities.add(speciality);
        
        String content = gson.toJson(specialities);
        File jsonFile = new File(PathUtils.currentPath("data/specialities.json"));
        
        try {
            Files.write(content.getBytes(), jsonFile);
        } catch (IOException exception) {
            return false;
        }
        
        return true;
    }

    @Override
    public List<Speciality> getAllSpecialities() {
        String json = PathUtils.currentPath("data/specialities.json");
        Speciality[] specialities = gson.fromJson(json, Speciality[].class);
        
        return Arrays.asList(specialities);
    }
    
    @Override
    public int getTotalSpecialities() {
        return getAllSpecialities().size();
    }

    @Override
    public Speciality getSpeciality(int id) {
        return getAllSpecialities().stream()
            .filter(speciality -> speciality.getId() == id)
            .findFirst()
            .orElse(null);
    }

    @Override
    public Speciality getSpeciality(String name) {
        return getAllSpecialities().stream()
            .filter(speciality -> speciality.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }
    
}
