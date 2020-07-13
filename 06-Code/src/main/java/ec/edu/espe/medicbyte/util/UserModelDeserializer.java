package ec.edu.espe.medicbyte.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.RoleService;
import java.lang.reflect.Type;

/**
 *
 * @author Andres Jonathan J.
 */
public class UserModelDeserializer implements JsonDeserializer<User> {
    private final RoleService roleService;
    
    public UserModelDeserializer(RoleService roleService) {
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
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
            System.out.println("Gender cannot be serialized ..");
        }

        return user;
    }

}
