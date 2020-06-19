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
public class User {

    private String identificationcard;
    private String surname;
    private String name;
    private String phone;
    private String email;
    private Gender gender;

    public User(String identificationcard, String surname, String name,
            String phone, String email, Gender gender) {
        this.identificationcard = identificationcard;
        this.surname = surname;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
    }

    public User() {
    }

    public String getIdentificationcard() {
        return identificationcard;
    }

    public void setIdentificationcard(String identificationcard) {
        this.identificationcard = identificationcard;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "\n" + "Cédula: " + identificationcard + "\nApellidos: " + surname
                + "\nNombres: " + name + "\nTelefono: " + phone + "\nEmail: " + email
                + "\nGender: " + gender;

    }

}
