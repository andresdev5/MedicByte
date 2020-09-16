package ec.edu.espe.medicbyte.service.impl;

import com.google.inject.Inject;
import dev.morphia.Key;
import ec.edu.espe.medicbyte.common.core.DaoService;
import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.IAppointmentService;
import ec.edu.espe.medicbyte.service.IUserService;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentService extends DaoService<Appointment> implements IAppointmentService {
    @Inject() IUserService userService;
    
    @Override
    public List<Appointment> getPatientAppointments(ObjectId patientId) {
        List<Appointment> appointments = databaseManager.getDatastore()
            .createQuery(Appointment.class)
            .field("patient")
            .equal(new Key<>(Patient.class, "users", patientId))
            .find()
            .toList();
        
        addJsonUsers(appointments);
                
        return appointments;
    }
    
    @Override
    public List<Appointment> getAll() {
        List<Appointment> appointments = super.getAll();
        addJsonUsers(appointments);
        return appointments;
    }
    
    private void addJsonUsers(List<Appointment> appointments) {
        List<User> users = userService.getAll();
        
        for (Appointment appointment : appointments) {
            User patient = users.stream()
                .filter(u -> u.getId().equals(appointment.getPatient().getId()))
                .findFirst()
                .orElse(null);
            
            if (patient != null) {
                appointment.getPatient().setUsername(patient.getUsername());
                appointment.getPatient().setEmail(patient.getEmail());
                appointment.getPatient().setPassword(patient.getPassword());
            }
            
            if (appointment.getMedic() != null) {
                User medic = users.stream()
                    .filter(u -> u.getId().equals(appointment.getMedic().getId()))
                    .findFirst()
                    .orElse(null);
                
                if (medic != null) {
                    appointment.getMedic().setUsername(medic.getUsername());
                    appointment.getMedic().setEmail(medic.getEmail());
                    appointment.getMedic().setPassword(medic.getPassword());
                }
            }
        }
    }
}
