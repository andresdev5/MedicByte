package ec.edu.espe.medicbyte.util.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Location;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.model.UserProfile;
import ec.edu.espe.medicbyte.service.ILocationService;
import ec.edu.espe.medicbyte.service.IMedicService;
import ec.edu.espe.medicbyte.service.IPatientService;
import ec.edu.espe.medicbyte.service.ISpecialityService;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentModelDeserializer implements JsonDeserializer<Appointment> {
    private final IMedicService medicService;
    private final IPatientService patientService;
    private final ISpecialityService specialityService;
    private final ILocationService locationService;
    private List<UserProfile> profiles;
    
    public AppointmentModelDeserializer(
        IMedicService medicService, IPatientService patientService,
        ISpecialityService specialityService, ILocationService locationService) {
        this.medicService = medicService;
        this.patientService = patientService;
        this.specialityService = specialityService;
        this.locationService = locationService;
    }
    
    @Override
    public Appointment deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext context) throws JsonParseException {
        JsonObject root = jsonElement.getAsJsonObject();
        Appointment appointment = new Appointment();
        
        appointment.setId(context.deserialize(root.get("id"), int.class));
        appointment.setDescription(context.deserialize(root.get("description"), String.class));
        appointment.setStatus(context.deserialize(root.get("status"), Appointment.Status.class));
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        appointment.setDate(LocalDate.parse(root.get("date").getAsString(), dateFormatter));
        
        if (root.has("hour") && !root.get("hour").isJsonNull()) {
            appointment.setHour(LocalTime.parse(root.get("hour").getAsString(), hourFormatter));
        }
        
        if (root.has("medicId") && !root.get("medicId").isJsonNull()) {
            Medic medic = medicService.getMedic(root.get("medicId").getAsInt());
            appointment.setMedic(medic);
        }
        
            
        if (root.has("patientId") && !root.get("patientId").isJsonNull()) {
            Patient patient = patientService.getPatient(root.get("patientId").getAsInt());
            appointment.setPatient(patient);
        }
        
        if (root.has("locationId") && !root.get("locationId").isJsonNull()) {
            Location location = locationService.getLocation(root.get("locationId").getAsInt());
            appointment.setLocation(location);
        }
        
        if (root.has("specialityId") && !root.get("specialityId").isJsonNull()) {
            Speciality speciality = specialityService.getSpeciality(root.get("specialityId").getAsInt());
            appointment.setSpeciality(speciality);
        }

        return appointment;
    }
}
