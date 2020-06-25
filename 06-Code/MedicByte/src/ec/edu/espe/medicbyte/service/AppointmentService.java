package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Appointment;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public interface AppointmentService {
    public List<Appointment> getAllAppointments();
    public int getTotalAppointments();
    public void saveAppointment(Appointment appointment);
    public boolean addPatientToAppointment(String userCI, int appointmentId);
    public Appointment getAppointment(int appointmentID);
    public void updateAppointment(Appointment appointment);
    public void deleteAppointment(Appointment appointment);
    public int getLastId();
}
