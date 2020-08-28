package ec.edu.espe.medicbyte.service.impl;

import dev.morphia.Key;
import ec.edu.espe.medicbyte.common.core.DaoService;
import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.service.IAppointmentService;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentService extends DaoService<Appointment> implements IAppointmentService {
    @Override
    public List<Appointment> getPatientAppointments(ObjectId patientId) {
        return databaseManager.getDatastore()
            .createQuery(Appointment.class)
            .field("patient")
            .equal(new Key<>(Patient.class, "users", patientId))
            .find()
            .toList();
    }
}
