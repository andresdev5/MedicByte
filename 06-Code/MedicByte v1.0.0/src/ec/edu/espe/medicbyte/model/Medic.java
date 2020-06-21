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

    private String speciality;

    public Medic(String identificationcard, String surname,
            String name, String speciality, String phone, String email, Gender gender) {
        super(identificationcard, surname, name, phone, email, gender);
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        System.out.print("\nMedic: ");
        return super.toString().concat("\nSpeciality: " + speciality);
    }

}
