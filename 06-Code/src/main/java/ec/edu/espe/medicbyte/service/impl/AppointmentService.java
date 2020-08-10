package ec.edu.espe.medicbyte.service.impl;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Appointment.Status;
import ec.edu.espe.medicbyte.model.Location;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.util.PathUtils;
import java.util.ArrayList;
import java.util.List;
import ec.edu.espe.medicbyte.service.IAppointmentService;
import ec.edu.espe.medicbyte.service.ILocationService;
import ec.edu.espe.medicbyte.service.IMedicService;
import ec.edu.espe.medicbyte.service.IPatientService;
import ec.edu.espe.medicbyte.service.ISpecialityService;
import ec.edu.espe.medicbyte.util.json.AppointmentModelDeserializer;
import ec.edu.espe.medicbyte.util.json.AppointmentModelSerializer;
import ec.edu.espe.medicbyte.util.IOUtils;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentService implements IAppointmentService {
    private final Gson gson;
    private final IMedicService medicService;
    private final IPatientService patientService;
    private final ILocationService locationService;
    private final ISpecialityService specialityService;
    
    @Inject()
    public AppointmentService(
        IMedicService medicService, IPatientService patientService,
        ILocationService locationService, ISpecialityService specialityService) {
        this.medicService = medicService;
        this.patientService = patientService;
        this.locationService = locationService;
        this.specialityService = specialityService;
        
        PathUtils.ensureFiles(
            PathUtils.currentPath("data/appointments.json"),
            PathUtils.currentPath("data/patient_appointments.json")
        );
        
        gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Appointment.class, new AppointmentModelSerializer())
            .registerTypeHierarchyAdapter(
                Appointment.class,
                new AppointmentModelDeserializer(
                    medicService, patientService,
                    specialityService, locationService
                )
            ).create();
    }
    
    /**
     * Retorna una lista de todas las citas medicas
     * 
     * @return La lista de citas
     */
    @Override
    public List<Appointment> getAllAppointments() {        
        File jsonFile = PathUtils.currentPath("data/appointments.json").toFile();
        String json = IOUtils.readFile(jsonFile);
        JsonElement jsonElement = JsonParser.parseString(json);
        
        if (!jsonElement.isJsonArray()) {
            return new ArrayList<>();
        }
        
        JsonArray elements = jsonElement.getAsJsonArray();
        
        List<Appointment> appointments = new ArrayList<>();
        List<Medic> medics = medicService.getAllMedics();
        List<Patient> patients = patientService.getAllPatients();
        List<Location> locations = locationService.getAllLocations();
        List<Speciality> specialities = specialityService.getAllSpecialities();
        
        jsonElement.getAsJsonArray().forEach(element -> {
            JsonObject row = element.getAsJsonObject();
            Appointment appointment = new Appointment();
            int id = row.get("id").getAsInt();
            int patientId = row.get("patientId").getAsInt();
            LocalDate date = LocalDate.parse(
                row.get("date").getAsString(),
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
            );
            Status status = Status.valueOf(row.get("status").getAsString());
            int specialityId = row.get("specialityId").getAsInt();
            
            Patient patient = patients.stream()
                .filter(p -> p.getId() == patientId)
                .findFirst().orElse(null);
            
            Speciality speciality = specialities.stream()
                .filter(s -> s.getId() == specialityId)
                .findFirst().orElse(null);
            
            appointment.setId(id);
            appointment.setPatient(patient);
            appointment.setDate(date);
            appointment.setStatus(status);
            appointment.setSpeciality(speciality);
            
            if (row.has("description") && !row.get("description").isJsonNull()) {
                String description = row.get("description").getAsString();
                appointment.setDescription(description);
            }
            
            if (row.has("hour") && !row.get("hour").isJsonNull()) {
                LocalTime hour = LocalTime.parse(
                    row.get("hour").getAsString(),
                    DateTimeFormatter.ofPattern("HH:mm")
                );
                appointment.setHour(hour);
            }
            
            if (row.has("medicId")) {
                int medicId = row.get("medicId").getAsInt();
                Medic medic = medics.stream()
                    .filter(m -> m.getId() == medicId)
                    .findFirst()
                    .orElse(null);
                
                appointment.setMedic(medic);
            }
            
            if (row.has("locationId")) {
                int locationId = row.get("locationId").getAsInt();
                Location location = locations.stream()
                    .filter(l -> l.getId() == locationId)
                    .findFirst().orElse(null);
                
                appointment.setLocation(location);
            }
            
            appointments.add(appointment);
        });
        
        return appointments;
    }
    
    @Override
    public int getTotalAppointments() {
        return getAllAppointments().size();
    }
    
    @Override
    public boolean addPatientAppointment(Appointment appointment, Patient patient) {        
        List<Appointment> appointments = getPatientAppointments(patient.getId());
        appointments.add(appointment);
        
        String content = gson.toJson(appointments);
        File jsonFile = PathUtils.currentPath("data/patient_appointments.json").toFile();
        
        try {
            Files.write(content.getBytes(), jsonFile);
        } catch (IOException exception) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public List<Appointment> getPatientAppointments(int patientId) {
        File json = PathUtils.currentPath("data/patient_appointments.json").toFile();
        CharSource source = Files.asCharSource(json, Charsets.UTF_8);
        List<Appointment> appointments = new ArrayList<>();
        String content;
        
        try {
            content = source.read();
        } catch (IOException exception) {
            return appointments;
        }
        
        JsonElement root = JsonParser.parseString(content);
        
        if (!root.isJsonArray()) {
            return appointments;
        }
        
        JsonArray items = root.getAsJsonArray();
        
        for (JsonElement item : items) {
            if (!item.isJsonObject()) {
                continue;
            }
            
            JsonObject object = item.getAsJsonObject();
            
            if (!object.has("appointmentId") || !object.has("userId")) {
                continue;
            }
            
            int appointmentId = object.get("appointmentId").getAsInt();
            int userId = object.get("userId").getAsInt();
            
            if (patientId != userId) {
                continue;
            }
            
            Appointment appointment = getAppointment(appointmentId);
            
            if (appointment == null) {
                continue;
            }
            
            appointments.add(appointment);
        }
        
        return appointments;
    }
    
    @Override
    public Appointment getAppointment(int id) {
        return findAppointment(appointment -> appointment.getId() == id);
    }
    
    @Override
    public Appointment findAppointment(Function<Appointment, Boolean> comparable) {
        return getAllAppointments().stream()
            .filter(comparable::apply)
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public boolean updateAppointment(Appointment appointment) {
        List<Appointment> appointments = getAllAppointments();
        
        if (getAppointment(appointment.getId()) == null) {
            return false;
        }
        
        List<Appointment> updatedList = appointments.stream().map(a -> {
            if (a.getId() != appointment.getId()) {
                return a;
            }
            
            return appointment;
        }).collect(Collectors.toList());
        
        return updateAllAppointments(updatedList);
    }
    
    @Override
    public boolean deleteAppointment(Appointment appointment) {
        List<Appointment> appointments = getAllAppointments();
        List<Appointment> updatedList;
        
        if (getAppointment(appointment.getId()) == null) {
            return false;
        }
        
        updatedList = appointments.stream()
            .filter(a -> a.getId() != appointment.getId())
            .collect(Collectors.toList());
        
        return updateAllAppointments(updatedList);
    }
    
    @Override
    public boolean deleteAppointment(int id) {
        Appointment appointment = getAppointment(id);
        
        if (appointment == null) {
            return false;
        }
        
        return deleteAppointment(appointment);
    }

    @Override
    public boolean addAppointment(Appointment appointment) {
        List<Appointment> appointments = getAllAppointments();
        Appointment last = appointments.stream()
            .sorted(Comparator.comparingInt(Appointment::getId))
            .reduce((first, second) -> second).orElse(null);
        
        int lastId = (last == null ? 1 : last.getId() + 1);
        
        appointment.setId(lastId);
        appointments.add(appointment);
        return updateAllAppointments(appointments);
    }
    
    private boolean updateAllAppointments(List<Appointment> appointments) {
        String content = gson.toJson(appointments);
        File jsonFile = PathUtils.currentPath("data/appointments.json").toFile();
        
        try {
            Files.write(content.getBytes(), jsonFile);
        } catch (IOException exception) {
            return false;
        }
        
        return true;
    }
}
