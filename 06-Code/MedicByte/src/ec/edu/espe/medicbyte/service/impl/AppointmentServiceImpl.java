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
    private final FileManager fileManager;
    private static final String DATA_FILENAME = "appointments.csv";
    
    public AppointmentServiceImpl() {
        this.fileManager = new FileManager(DATA_FILENAME, true);
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
        List<FileLine> lines = fileManager.read();
        
        // code,date,hour,id_medic
        for (FileLine line : lines) {
            Appointment appointment = csvRecordToAppointment(line.csv());
            appointments.add(appointment);
        }

        return appointments;
    }
    
    @Override
    public Appointment getAppointment(int appointmentID) {
        CsvFile csv = fileManager.toCsv();
        
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
        List<FileLine> lines = fileManager.read();
        List<String> updatedLines = Collections.emptyList();
        
        if (lines.isEmpty()) {
            return;
        }
        
        fileManager.clear();
        
        for (FileLine line : lines) {
            Appointment parsed = csvRecordToAppointment(line.csv());
            
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
        fileManager.write(appointment.toString());
    }
}
