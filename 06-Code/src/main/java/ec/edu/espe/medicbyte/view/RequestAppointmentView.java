package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.Console;
import ec.edu.espe.medicbyte.common.core.ConsoleView;
import ec.edu.espe.medicbyte.common.tui.ConsoleChooser;
import ec.edu.espe.medicbyte.common.tui.ConsolePagination;
import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.util.StringUtils;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class RequestAppointmentView extends ConsoleView {
    @Override
    public void init() {
        
    }
    
    @Override
    public void display() {
        ConsolePagination paginator = new ConsolePagination(2);
        ConsoleChooser specialityChooser = new ConsoleChooser();
        List<Speciality> specialities = get("specialities");
        
        specialities.forEach(speciality -> {
            specialityChooser
                .addOption(speciality.getName())
                .addArgument(speciality);
        });
        
        Speciality speciality = specialityChooser
            .choose("choose an speciality: ")
            .getArgument(0);
        
        emit("getAppointmentsBySpeciality", speciality);
        List<Appointment> appointments = get("appointmentsBySpeciality");
        
        if (appointments.isEmpty()) {
            console.newLine();
            console.echoln("There are no appointments available in that specialty.");
            return;
        }
        
        boolean viewAppointments = Console.confirm(
            String.format(
                appointments.size() > 1
                    ? "There are %d Appointments available, Do you want to see them?"
                    : "There is %d appointment available, do you want to see it?", 
                appointments.size()
            )
        );
        
        if (viewAppointments) {
            appointments.forEach((appointment) -> {
                int id = appointment.getId();
                Date date = appointment.getDate();
                LocalTime hour = appointment.getHour();
                String doctor = appointment.getMedic().getFullName();

                paginator.addItem(() -> {
                    console.echoln(
                        "------------------------\n"
                        + "id: %d\n"
                        + "date: %s\n"
                        + "hour: %s\n"
                        + "doctor: %s\n"
                        + "------------------------\n",
                        id, date.toString(), hour.toString(), doctor,
                        (appointment.isTaken() ? "not available" : "available")
                    );
                });
            });
            
            console.newLine(2);
            paginator.display();
        }
        
        console.newLine(2);
        boolean createAppointment = Console.confirm("Do you want to request an appointment?");
        
        if (!createAppointment) {
            return;
        }
        
        String id = console.input(
            "Enter the appointment id or -1 to exit: ",
            (input) -> {
                if (input.equals("-1")) {
                    return true;
                }

                Appointment found = appointments.stream()
                    .filter((appointment) -> {
                        return appointment.getId() == Integer.parseInt(input);
                    }).findFirst().orElse(null);

                boolean valid = found != null && !found.isTaken();

                if (found == null) {
                    console.newLine().echoln("wrong id or appointment doesn't exist");
                } else if (!valid) {
                    console.newLine().echoln("appointment not available");
                }

                return valid;
            }
        );
        
        if (id.equals("-1")) {
            return;
        }
        
        String identification = console.input("Cedula de identidad: ", (input) -> {
            boolean valid = StringUtils.isValidCI(input);

            if (!valid) {
                console.newLine().echoln("[Cedula incorrecta]");
            }

            return valid;
        });
    }
    
    @Override
    protected String template() { return ""; }
}
