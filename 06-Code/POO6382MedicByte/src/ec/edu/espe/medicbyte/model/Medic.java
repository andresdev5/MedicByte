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
public class Medic extends User {

    public Medic(String name, UserInfo info, Role role, String identificationCard) {
        super(name, info, role, identificationCard);
    }

    public Patient[] listPatients() {
        //TODO
        return null;
    }

    public Report[] listReports() {
        //TODO
        return null;
    }
    
    public void findPatient(int id){
        //TODO
    }
}
