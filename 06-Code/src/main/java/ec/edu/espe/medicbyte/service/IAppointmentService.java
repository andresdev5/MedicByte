package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Patient;
import java.util.List;
import java.util.function.Function;

/**
 *
 * @author Andres Jonathan J.
 */
public interface IAppointmentService {
    List<Appointment> getAllAppointments();
    int getTotalAppointments();
    boolean addPatientAppointment(Appointment appointment, Patient patient);
    List<Appointment> getPatientAppointments(int userId);
    Appointment getAppointment(int id);
    Appointment findAppointment(Function<Appointment, Boolean> comparable);
    boolean updateAppointment(Appointment appointment);
    boolean deleteAppointment(Appointment appointment);
    boolean deleteAppointment(int id);
    boolean addAppointment(Appointment appointment);
}
