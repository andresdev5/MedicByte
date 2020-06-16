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
public class User {

    private String name;
    private UserInfo info;
    private Role role;
    private String identificationCard;

    public User(String name, UserInfo info, Role role, String identificationCard) {
        this.name = name;
        this.info = info;
        this.role = role;
        this.identificationCard = identificationCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard;
    }

    public boolean hasRole() {
        //TODO
        return false;
    }
    
    public boolean senEmail(String subject, String content){
        //TODO
        return false;
    }

}
