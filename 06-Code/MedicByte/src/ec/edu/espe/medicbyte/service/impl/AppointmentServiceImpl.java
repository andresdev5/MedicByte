package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.service.AppointmentService;
import ec.edu.espe.medicbyte.service.MedicService;
import ec.edu.espe.medicbyte.util.StringUtils;
import ec.edu.espe.tinyio.CsvFile;
import ec.edu.espe.tinyio.CsvRecord;
import ec.edu.espe.tinyio.FileManager;
import ec.edu.espe.tinyio.FileLine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentServiceImpl implements AppointmentService {
    private final FileManager appointmentsDb;
    private final FileManager patientAppointmentsDb;
    
    public AppointmentServiceImpl() {
        appointmentsDb = new FileManager("data/appointments.csv", true);
        patientAppointmentsDb = new FileManager("data/patient_appointments.csv", true);
    }
    
    @Override
    public boolean addPatientToAppointment(String userCI, int appointmentId) {        
        try {
            patientAppointmentsDb.create();
            patientAppointmentsDb.write(String.format("\"%s\",%d", userCI, appointmentId));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public List<Appointment> getPatientAppointments(String patientId) {        
        if (patientAppointmentsDb.countLines() == 0) {
            return Collections.emptyList();
        }
        
        CsvFile csv = patientAppointmentsDb.toCsv();
        List<CsvRecord> found = csv.find((record) -> {
            return record.getColumn(0).getValue()
                .trim().equalsIgnoreCase(patientId);
        });
        
        if (found.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<Appointment> appointments = new ArrayList<>();
        
        for (CsvRecord record : found) {
            int id = Integer.parseInt(record.getColumn(1).getValue().trim());
            appointments.add(this.getAppointment(id));
        }
        
        return appointments;
    }
    
    @Override
    public int getTotalAppointments() {
        return appointmentsDb.countLines();
    }
    
    @Override
    public int getLastId() {
        List<Appointment> list = getAllAppointments();
        int lastIndex = getTotalAppointments() - 1;
        
        if (list == null || list.isEmpty() || lastIndex < 0) {
            return 0;
        }
        
        Appointment appointment = list.get(lastIndex);
        
        return appointment.getId();
    }
    
    /**
     * Retorna una lista de todas las citas medicas
     * 
     * @return La lista de citas
     */
    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        List<FileLine> lines = appointmentsDb.read();
        
        // code,date,hour,id_medic
        for (FileLine line : lines) {
            Appointment appointment = csvRecordToAppointment(line.csv());
            appointments.add(appointment);
        }

        return appointments;
    }
    
    @Override
    public Appointment getAppointment(int appointmentID) {
        CsvFile csv = appointmentsDb.toCsv();
        
        List<CsvRecord> foundLine = csv.find((record) -> {
            int id = Integer.parseInt(record.getColumnValue(0));
            return id == appointmentID;
        });
        
        if (foundLine.isEmpty()) {
            return null;
        }
        
        return csvRecordToAppointment(foundLine.get(0));
    }
    
    @Override
    public void updateAppointment(Appointment appointment) {
        List<FileLine> lines = appointmentsDb.read();
        
        if (lines.isEmpty()) {
            return;
        }
        
        appointmentsDb.clear();
        
        for (FileLine line : lines) {
            Appointment parsed = csvRecordToAppointment(line.csv());
            
            if (parsed.getId() == appointment.getId()) {
                parsed.setTaken(true);
            }
            
            appointmentsDb.write(parsed.toString());
        }
    }
    
    @Override
    public void deleteAppointment(Appointment appointment) {
        List<Appointment> appointments = getAllAppointments();
        List<String> lines = appointments.stream()
            .filter(a -> a.getId() != appointment.getId())
            .map(a -> a.toString())
            .collect(Collectors.toList());
        
        appointmentsDb.clear();
        appointmentsDb.write(lines);
        
        for (Appointment item : appointments) {
            patientAppointmentsDb.remove(line -> {
                return Integer.parseInt(
                    line.csv().getColumnValue(1)) == item.getId();
            });
        }
    }
    
    public Appointment csvRecordToAppointment(CsvRecord record) {
        Appointment appointment = new Appointment();
        List<String> values = record.getColumnValues();
        
        appointment.setId(Integer.parseInt(values.get(0)));
        appointment.setDate(StringUtils.parseDate(values.get(1)));
        appointment.setHour(StringUtils.parseHour(values.get(2)));
        
        int medicId = Integer.parseInt(values.get(3));
        MedicService medicService = new MedicServiceImpl();
        Medic medic = medicService.getMedic(medicId);
        appointment.setMedic(medic);

        boolean isTaken = Integer.parseInt(values.get(4)) != 0;
        appointment.setTaken(isTaken);
        
        return appointment;
    }

    @Override
    public void saveAppointment(Appointment appointment) {
        appointmentsDb.write(appointment.toString());
    }
}
