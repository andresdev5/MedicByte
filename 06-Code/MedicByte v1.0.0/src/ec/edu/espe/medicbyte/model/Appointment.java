
package ec.edu.espe.medicbyte.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class Appointment {
<<<<<<< HEAD
   

=======
    
    private int code;
    private Patient patient;
    private Medic medic;
    
>>>>>>> 8d5d70443c7f9c2fc7706a44d26325b7613d5cdd
    
    public void showAppointment(){
        generaCode();
        
    }
    
    public void generaCode(){
        for(int i=0; i<=1000;i++){
            code=0;
            code++;
        }
    }
    
    public void generarCita(){
        System.out.println("Paciente: ");
        patient.toString();
        System.out.println("Medico: ");
        medic.getName();
        medic.getSpeciality();
        
    }
}
