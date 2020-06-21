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
public class Odontology {
    private String description;

    public String getDescripcion() {
        return description;
    }

    public void setDescripcion(String description) {
        this.description = description;
        description = "Es una Especialidad";
    }
    
    
    public void citaOdontologo(){
      Appointment appointment = new Appointment();
      Patient patient = new Patient();
      appointment.showAppointment();
      patient.enterPatient(patient);
      
    }
    
    
}
