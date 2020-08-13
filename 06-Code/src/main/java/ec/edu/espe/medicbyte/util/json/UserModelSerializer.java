package ec.edu.espe.medicbyte.util.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ec.edu.espe.medicbyte.model.User;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Andres Jonathan J.
 */
public class UserModelSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        
        jsonObject.add("id", context.serialize(user.getId()));
        
        if (user.getRole() != null) {
            jsonObject.add("roleId", context.serialize(user.getRole().getId()));
        }
        
        jsonObject.add("username", context.serialize(user.getUsername()));
        jsonObject.add("email", context.serialize(user.getEmail()));
        jsonObject.add("password", context.serialize(user.getPassword()));
        
        Instant instant = user.getRegisteredAt().atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        jsonObject.add("registeredAt", context.serialize(date.getTime()));
        
        return jsonObject;
    }
}
