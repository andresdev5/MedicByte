package ec.edu.espe.medicbyte.util.json;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.model.UserProfile;
import java.lang.reflect.Type;
import ec.edu.espe.medicbyte.service.IRoleService;
import ec.edu.espe.medicbyte.service.IUserService;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class UserModelDeserializer implements JsonDeserializer<User> {
    private final IUserService userService;
    private final IRoleService roleService;
    private List<UserProfile> profiles;
    
    public UserModelDeserializer(IUserService userService, IRoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    
    @Override
    public User deserialize(JsonElement paramJsonElement, Type paramType,
            JsonDeserializationContext paramJsonDeserializationContext) throws JsonParseException {

        User user = new Gson().fromJson(paramJsonElement.getAsJsonObject(), User.class);

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
