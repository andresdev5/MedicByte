package ec.edu.espe.medicbyte.util.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ec.edu.espe.medicbyte.model.Appointment;
import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentModelSerializer implements JsonSerializer<Appointment> {
    @Override
    public JsonElement serialize(Appointment appointment, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        
        String date = appointment.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String hour = null;
        
        if (appointment.getHour() != null) {
            hour = appointment.getHour().format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        
        jsonObject.add("id", context.serialize(appointment.getId()));
        jsonObject.add("date", context.serialize(date));
        jsonObject.add("hour", context.serialize(hour));
        jsonObject.add("status", context.serialize(appointment.getStatus()));
        jsonObject.add("description", context.serialize(appointment.getDescription()));
        
        Integer medicId = null;
        Integer patientId = null;
        Integer locationId = null;
        Integer specialityId = null;
        
        if (appointment.getMedic() != null) {
            medicId = appointment.getMedic().getId();
        }
        
        if (appointment.getPatient()!= null) {
            patientId = appointment.getPatient().getId();
        }
        
        if (appointment.getLocation()!= null) {
            locationId = appointment.getLocation().getId();
        }
        
        if (appointment.getSpeciality()!= null) {
            specialityId = appointment.getSpeciality().getId();
        }
        
        jsonObject.add("medicId", context.serialize(medicId));
        jsonObject.add("patientId", context.serialize(patientId));
        jsonObject.add("locationId", context.serialize(locationId));
        jsonObject.add("specialityId", context.serialize(specialityId));
        
        return jsonObject;
    }
}
