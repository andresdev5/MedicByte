package ec.edu.espe.medicbyte.controller;

import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.ListMedic;
import ec.edu.espe.medicbyte.utils.FileManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class AppointmentsController {

    public List<Appointment> getAllAppointments() {
        FileManager fileManager = new FileManager("Appointments.txt");
        List<Appointment> appointments = new ArrayList<>();
        ListMedic listmedic = new ListMedic();
        String content = fileManager.readFile();

        // code,date,hour,id_medic
        for (String line : content.split("\n")) {
            String tokens[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            Appointment appointment = new Appointment();
            appointment.setCode(tokens[0]);
            appointment.setDate(tokens[1]);
            appointment.setHour(tokens[2]);

            int medicId = Integer.parseInt(tokens[3]);
            appointment.setMedic(listmedic.getMedic(medicId));

            boolean isTaken = Integer.parseInt(tokens[4]) != 0;
            appointment.setTaken(isTaken);

            appointments.add(appointment);
        }

        return appointments;
    }
}
