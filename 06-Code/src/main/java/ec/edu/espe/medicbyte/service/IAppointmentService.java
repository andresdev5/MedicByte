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
    public List<Appointment> getAllAppointments();
    
    public int getTotalAppointments();
    
    public boolean addPatientAppointment(Appointment appointment, Patient patient);
    
    public List<Appointment> getPatientAppointments(int userId);
    
    public Appointment getAppointment(int id);
    
    public Appointment findAppointment(Function<Appointment, Boolean> comparable);
    
    public boolean updateAppointment(Appointment appointment);
    
    public boolean deleteAppointment(Appointment appointment);
    
    public boolean deleteAppointment(int id);

    public boolean addAppointment(Appointment appointment);
}
