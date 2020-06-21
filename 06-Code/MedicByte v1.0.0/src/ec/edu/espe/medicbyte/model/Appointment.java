/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.model;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class Appointment {

    private int code;
    private Patient patient;
    private Medic medic;

    public void showAppointment() {
        generaCode();

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
