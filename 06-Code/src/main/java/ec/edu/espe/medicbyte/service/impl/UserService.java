package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.util.PathUtils;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.inject.Inject;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.model.UserProfile;
import ec.edu.espe.medicbyte.util.IOUtils;
import ec.edu.espe.medicbyte.util.json.UserModelDeserializer;
import ec.edu.espe.medicbyte.util.json.UserModelSerializer;
import java.util.function.Function;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCrypt;
import ec.edu.espe.medicbyte.service.IRoleService;
import ec.edu.espe.medicbyte.service.IUserService;
import ec.edu.espe.medicbyte.util.json.ByteArrayToBase64TypeAdapter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andres Jonathan J.
 */
public class UserService implements IUserService {
    private final Gson gson;
    
    @Inject()
    public UserService(IRoleService roleService) {        
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder
            .registerTypeAdapter(LocalDateTime.class,
                (JsonSerializer<LocalDateTime>) (dateTime, type, context) -> {
                Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
                Date date = Date.from(instant);
                return new JsonPrimitive(date.getTime());
            })
            .registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>) (jsonElement, type, context)
                -> Instant.ofEpochMilli(jsonElement.getAsLong()).atZone(ZoneId.systemDefault()).toLocalDateTime())
            .registerTypeAdapter(User.class, new UserModelSerializer())
            .registerTypeAdapter(User.class, new UserModelDeserializer(this, roleService))
            .registerTypeAdapter(LocalDate.class,
                (JsonSerializer<LocalDate>) (localDate, type, context)
                -> new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
            .registerTypeAdapter(LocalDate.class,
                (JsonDeserializer<LocalDate>) (jsonElement, type, context)
                -> LocalDate.parse(jsonElement.getAsString(), DateTimeFormatter.ofPattern("dd-MM-yyyy")))
            .registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter());
        
        gson = gsonBuilder.create();
        
        PathUtils.ensureFiles(
            PathUtils.currentPath("data/users.json"),
            PathUtils.currentPath("data/user_profiles.json")
        );
    }

    @Override
    public User getUser(int id) {
        return findUser(user -> user.getId() == id);
    }

    @Override
    public User getUser(String username) {
        return findUser(user -> user.getUsername().trim().equalsIgnoreCase(username));
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
    public User createUser(String username, String email, String plainPassword, Role role) {
        List<User> users = getAllUsers();
        User last = users.stream()
            .sorted(Comparator.comparingInt(User::getId))
            .reduce((first, second) -> second).orElse(null);
        
        int lastId = (last == null ? 1 : last.getId() + 1);
        User user = new User();
        
        if (getUser(username) != null) {
            return null;
        }
        
        String salt = BCrypt.gensalt();
        
        user.setId(lastId);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(BCrypt.hashpw(plainPassword, salt));
        user.setRole(role);
        users.add(user);
        
        String content = gson.toJson(users.toArray());
        File jsonFile = PathUtils.currentPath("data/users.json").toFile();
        
        try {
            Files.write(content.getBytes(), jsonFile);
        } catch (IOException exception) {
            return null;
        }
        
        // create profile
        UserProfile profile = createEmptyUserProfile(user);
        user.setProfile(profile);
        
        return user;
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
    
    @Override
    public UserProfile getUserProfile(int userId) {
        return getUserProfiles().stream()
            .filter(profile -> profile.getUserId() == userId)
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public List<UserProfile> getUserProfiles() {
        File jsonFile = PathUtils.currentPath("data/user_profiles.json").toFile();
        String json = IOUtils.readFile(jsonFile);
        UserProfile[] profiles = gson.fromJson(json.isEmpty() ? "[]" : json, UserProfile[].class);
        
        if (profiles == null) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(Arrays.asList(profiles));
    }
    
    @Override
    public UserProfile updateUserProfile(int userId, UserProfile profile) {
        if (!userExists(userId)) {
            return null;
        }
        
        List<UserProfile> profiles = getUserProfiles();
        profiles = profiles.stream().map(p -> {
            if (p.getUserId() == userId) {
                return profile;
            }
            
            return p;
        }).collect(Collectors.toList());
        
        String content = gson.toJson(profiles);
        File jsonFile = PathUtils.currentPath("data/user_profiles.json").toFile();
        
        try {
            Files.write(content.getBytes(), jsonFile);
        } catch (IOException exception) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        }
        
        return profile;
    }
    
    @Override
    public UserProfile createEmptyUserProfile(User user) {
        List<UserProfile> profiles = getUserProfiles();
        UserProfile profile = new UserProfile();
        profile.setUserId(user.getId());
        profile.setFullName("");
        profile.setGender(UserProfile.Gender.UNSPECIFIED);
        profile.setPhone("");
        profile.setBirthday(null);
        profiles.add(profile);
        
        String content = gson.toJson(profiles.toArray());
        File jsonFile = PathUtils.currentPath("data/user_profiles.json").toFile();
        
        try {
            Files.write(content.getBytes(), jsonFile);
        } catch (IOException exception) {
            return null;
        }
        
        return profile;
    }
}