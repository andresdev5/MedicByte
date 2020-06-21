package ec.edu.espe.medicbyte.model;

import ec.edu.espe.medicbyte.utils.FileManager;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class Appointment {

    private int code;
    private Patient patient;
    private Medic medic;

    public void showAppointment() {
        FileManager fileManager = new FileManager();
        System.out.println("CITAS MEDICAS DISPONIBLES");
        System.out.println("CODIGO\t FECHA\t HORA\t DOCTOR\t");
        fileManager.readFile();

    }

    public void generaCode() {
        for (int i = 1; i <= 7; i++) {
            code = 1;
            code++;
        }
    }

    public void generarCita() {
        System.out.println("Paciente: ");
        patient.toString();
        System.out.println("Medico: ");
        medic.getName();
        medic.getSpeciality();

    }
}
