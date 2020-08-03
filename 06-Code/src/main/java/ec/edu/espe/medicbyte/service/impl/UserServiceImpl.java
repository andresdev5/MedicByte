package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.UserService;
import ec.edu.espe.medicbyte.util.PathUtils;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.service.RoleService;
import ec.edu.espe.medicbyte.util.IOUtils;
import ec.edu.espe.medicbyte.util.UserModelDeserializer;
import ec.edu.espe.medicbyte.util.UserModelSerializer;
import java.util.function.Function;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Andres Jonathan J.
 */
public class UserServiceImpl implements UserService {
    private final Gson gson;
    
    @Inject()
    public UserServiceImpl(RoleService roleService) {        
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(User.class, new UserModelSerializer());
        gsonBuilder.registerTypeAdapter(User.class, new UserModelDeserializer(roleService));
        gson = gsonBuilder.create();
        
        PathUtils.ensureFiles(
            PathUtils.currentPath("data/users.json")
        );
    }

    @Override
    public User getUser(int id) {
        return findUser(user -> user.getId() == id);
    }

    @Override
    public User getUser(String username) {
        return findUser(user -> user.getUsername().equalsIgnoreCase(username));
    }
    
    @Override 
    public boolean userExists(int id) {
        return getUser(id) != null;
    }
    
    @Override 
    public boolean userExists(String username) {
        return getUser(username) != null;
    }

    @Override
    public User findUser(Function<User, Boolean> comparator) {
        return findUsers(comparator::apply).stream()
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public List<User> findUsers(Function<User, Boolean> comparator) {
        return getAllUsers().stream()
            .filter(comparator::apply)
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean createUser(String username, String plainPassword, Role role) {
        List<User> users = getAllUsers();
        User user = new User();
        
        if (getUser(username) != null) {
            return false;
        }
        
        String salt = BCrypt.gensalt();
        
        user.setId(getTotalUsers() + 1);
        user.setUsername(username);
        user.setPassword(BCrypt.hashpw(plainPassword, salt));
        user.setSalt(salt);
        user.setRole(role);
        users.add(user);
        
        String content = gson.toJson(users.toArray());
        File jsonFile = PathUtils.currentPath("data/users.json").toFile();
        
        try {
            Files.write(content.getBytes(), jsonFile);
        } catch (IOException exception) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public List<User> getAllUsers() {
        File jsonFile = PathUtils.currentPath("data/users.json").toFile();
        String json = IOUtils.readFile(jsonFile);
        User[] users = gson.fromJson(json.isEmpty() ? "[]" : json, User[].class);
        
        if (users == null) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(Arrays.asList(users));
    }
    
    @Override
    public int getTotalUsers() {
        return getAllUsers().size();
    }
}