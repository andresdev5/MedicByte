package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.utils.tinyio.FileManager;
import ec.edu.espe.medicbyte.utils.tinyio.FileLine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentServiceImpl implements AppointmentService {
    private FileManager fileManager;
    
    public AppointmentServiceImpl() {
        try {
            this.fileManager = new FileManager("appointments.csv");
            this.fileManager.create();
        } catch (Exception ex) {
            // TODO: fatal error, exit application
            System.err.println(ex.getMessage());
        }
    }
    
    @Override
    public boolean addPatientToAppointment(String userCI, int appointmentId) {
        FileManager patientAppointments = new FileManager("patient_appointments.csv");
        
        try {
            patientAppointments.create();
            patientAppointments.write(String.format("%s, %d", userCI, appointmentId));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public int getTotalAppointments() {
        return fileManager.countLines();
    }
    
    /**
     * Retorna una lista de todas las citas medicas
     * 
     * @return La lista de citas
     */
    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        List<FileLine> lines = fileManager.read();
        
        // code,date,hour,id_medic
        for (FileLine line : lines) {
            Appointment appointment = csvLineToAppointment(line.text());
            appointments.add(appointment);
        }

        return appointments;
    }
    
    @Override
    public Appointment getAppointment(int appointmentID) {
        FileLine foundLine = fileManager.findFirst((line) -> {
            String columns[] = line.text().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            int id = Integer.parseInt(columns[0]);
            return id == appointmentID;
        });
        
        if (foundLine == null) {
            return null;
        }
        
        MedicService medicService = new MedicServiceImpl();
        
        String columns[] = foundLine.text().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        
        if (columns.length < 5) {
            return null;
        }
        
        Appointment appointment = csvLineToAppointment(foundLine.text());
        
        return appointment;
    }
    
    @Override
    public void updateAppointment(Appointment appointment) {
        List<FileLine> lines = fileManager.read();
        List<String> updatedLines = Collections.emptyList();
        
        if (lines.isEmpty()) {
            return;
        }
        
        fileManager.clear();
        
        for (FileLine line : lines) {
            Appointment parsed = csvLineToAppointment(line.text());
            
            if (parsed.getId() == appointment.getId()) {
                parsed.setTaken(true);
            }
            
            fileManager.write(parsed.toString());
        }
    }
    
    @Override
    public void deleteAppointment(Appointment appointment) {
        List<Appointment> appointments = getAllAppointments();
        List<String> lines = appointments.stream()
            .filter(a -> a.getId() != appointment.getId())
            .map(a -> a.toString())
            .collect(Collectors.toList());
        
        fileManager.clear();
        fileManager.write(lines);
    }
    
    public Appointment csvLineToAppointment(String line) {
        String tokens[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        Appointment appointment = new Appointment();
        appointment.setId(Integer.parseInt(tokens[0]));
        appointment.setDate(tokens[1]);
        appointment.setHour(tokens[2]);

        int medicId = Integer.parseInt(tokens[3]);
        MedicService medicService = new MedicServiceImpl();
        Medic medic = medicService.getMedic(medicId);
        appointment.setMedic(medic);

        boolean isTaken = Integer.parseInt(tokens[4]) != 0;
        appointment.setTaken(isTaken);
        
        return appointment;
    }

    @Override
    public void saveAppointment(Appointment appointment) {
        fileManager.write(appointment.toString());
    }
}
