package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.common.core.IDaoService;
import ec.edu.espe.medicbyte.model.Appointment;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Andres Jonathan J.
 */
public interface IAppointmentService extends IDaoService<Appointment> {
    List<Appointment> getPatientAppointments(ObjectId patient);
}
