/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.model;

/**
 *
 * @author Michael Cobacango -ESPE -DCCO
 */
public class Patient extends User {

    private boolean hasSecure;

    public Patient(String name, UserInfo info, Role role, String identificationCard) {
        super(name, info, role, identificationCard);
    }

    public boolean isHasSecure() {
        return hasSecure;
    }

    public void setHasSecure(boolean hasSecure) {
        this.hasSecure = hasSecure;
    }

    

    public Appointment[] listAppointment() {
        //TODO
        return null;
    }

    public void findAppointment(int id) {
        //TODO
    }

}
