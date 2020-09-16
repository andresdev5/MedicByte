package ec.edu.espe.medicbyte.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.common.core.DaoService;
import ec.edu.espe.medicbyte.model.UserProfile;
import ec.edu.espe.medicbyte.service.IUserService;
import ec.edu.espe.medicbyte.util.IOUtils;
import ec.edu.espe.medicbyte.util.PathUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;

/**
 *
 * @author Andres Jonathan J.
 */
public class UserService extends DaoService<User>  implements IUserService {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public UserService() {
        PathUtils.ensureFiles(PathUtils.currentPath("data/users.json"));
    }
    
    @Override
    public User get(String username) {
        User user = field("username").equal(username).first();
        User jsonUser = getFromJson(username);
        
        if (user == null && jsonUser != null) {
            return jsonUser;
        }
        
        if (jsonUser != null) {
            user.setUsername(jsonUser.getUsername());
            user.setEmail(jsonUser.getEmail());
            user.setPassword(jsonUser.getPassword());
        }
        
        return user;
    }
    
    @Override
    public User get(ObjectId id) {
        User user = super.get(id);
        User jsonUser = getFromJson(id);
        
        if (user == null && jsonUser != null) {
            return jsonUser;
        }
        
        if (jsonUser != null) {
            user.setUsername(jsonUser.getUsername());
            user.setEmail(jsonUser.getEmail());
            user.setPassword(jsonUser.getPassword());
        }
        
        return user;
    }
    
    @Override
    public UserProfile createProfile() {
        ObjectId id = (ObjectId) databaseManager.getDatastore().save(new UserProfile()).getId();
        return databaseManager.getDatastore().find(UserProfile.class).field("_id").equal(id).first();
    }
    
    @Override 
    public boolean exists(String username) {
        return get(username) != null;
    }
    
    @Override
    public boolean save(User user) {
        boolean added = super.save(user);
        saveToJson(getAll());
        
        return added;
    }
    
    @Override
    public List<User> getAll() {
        List<User> jsonUsers = getAllFromJson();
        List<User> users = super.getAll();
        
        for (User user : users) {
            User jsonUser = jsonUsers.stream()
                .filter(u -> u.getId().equals(user.getId()))
                .findFirst()
                .orElse(null);
            
            if (jsonUser == null) continue;
            
            user.setUsername(jsonUser.getUsername());
            user.setEmail(jsonUser.getEmail());
            user.setPassword(jsonUser.getPassword());
        }
        
        return users;
    }
    
    private void saveToJson(List<User> users) {
        String json = gson.toJson(users);
        Path jsonPath = PathUtils.currentPath("data/users.json");
        
        try {
            Files.write(jsonPath, json.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<User> getAllFromJson() {
        Path jsonPath = PathUtils.currentPath("data/users.json");
        String json = IOUtils.readFile(jsonPath.toFile());
        
        if (json == null) {
            saveToJson(super.getAll());
            return super.getAll();
        }
        
        User usersArray[] = gson.fromJson(json, User[].class);
        
        if (usersArray == null) {
            saveToJson(super.getAll());
            return super.getAll();
        }
        
        return new ArrayList<>(Arrays.asList(usersArray));
    }
    
    private User getFromJson(String username) {
        return getAllFromJson().stream()
            .filter(user -> user.getUsername().equalsIgnoreCase(username))
            .findFirst()
            .orElse(null);
    }
    
    private User getFromJson(ObjectId id) {
        return getAllFromJson().stream()
            .filter(user -> user.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
}