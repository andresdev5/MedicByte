package ec.edu.espe.medicbyte.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.model.UserProfile;
import java.lang.reflect.Type;
import ec.edu.espe.medicbyte.service.IRoleService;
import ec.edu.espe.medicbyte.service.IUserService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class UserModelDeserializer implements JsonDeserializer<User> {
    private Gson gson;
    private final IUserService userService;
    private final IRoleService roleService;
    private List<UserProfile> profiles;
    
    public UserModelDeserializer(IUserService userService, IRoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        
        gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                (JsonSerializer<LocalDateTime>) (dateTime, type, context) -> {
                Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
                Date date = Date.from(instant);
                return new JsonPrimitive(date.getTime());
            })
            .registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>) (jsonElement, type, context)
                -> Instant.ofEpochMilli(jsonElement.getAsLong()).atZone(ZoneId.systemDefault()).toLocalDateTime())
            .create();
    }
    
    @Override
    public User deserialize(JsonElement paramJsonElement, Type paramType,
            JsonDeserializationContext paramJsonDeserializationContext) throws JsonParseException {
        User user = gson.fromJson(paramJsonElement.getAsJsonObject(), User.class);

        try {
            if (paramJsonElement.getAsJsonObject().has("roleId")) {
                int id = paramJsonElement.getAsJsonObject().get("roleId").getAsInt();
                Role role = roleService.getRole(id);
                
                if (role != null) {
                    user.setRole(role);
                }
            }
            
            UserProfile profile = userService.getUserProfile(user.getId());
            
            if (profile != null) {
                user.setProfile(profile);
            }
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }

        return user;
    }
}
