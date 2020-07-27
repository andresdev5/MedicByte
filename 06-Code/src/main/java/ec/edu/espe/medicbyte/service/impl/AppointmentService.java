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
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.util.PathUtils;
import java.util.ArrayList;
import java.util.List;
import ec.edu.espe.medicbyte.service.IAppointmentService;
import ec.edu.espe.medicbyte.service.ILocationService;
import ec.edu.espe.medicbyte.service.ISpecialityService;
import ec.edu.espe.medicbyte.util.AppointmentModelDeserializer;
import ec.edu.espe.medicbyte.util.AppointmentModelSerializer;
import ec.edu.espe.medicbyte.util.IOUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentService implements IAppointmentService {
    private final Gson gson;
    
    @Inject()
    public AppointmentService(
        MedicService medicService, PatientService patientService,
        ILocationService locationService, ISpecialityService specialityService) {
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
        Appointment[] appointments = gson.fromJson(json, Appointment[].class);
        
        if (appointments == null) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(Arrays.asList(appointments));
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
