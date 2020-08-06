package ec.edu.espe.medicbyte.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ec.edu.espe.medicbyte.model.Medic;
import java.lang.reflect.Type;

/**
 *
 * @author Andres Jonathan J.
 */
public class MedicModelSerializer implements JsonSerializer<Medic> {
    @Override
    public JsonElement serialize(Medic medic, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        
        jsonObject.add("userId", context.serialize(medic.getId()));
        jsonObject.add("specialityId", context.serialize(medic.getSpeciality().getId()));
        
        return jsonObject;
    }
}
