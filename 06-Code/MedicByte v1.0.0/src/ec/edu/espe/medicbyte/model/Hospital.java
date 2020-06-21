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
public class Hospital {

    private String name;
    private String address; //Type Location 
    private ListMedic listMedic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        name = "IESS";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        address = "La Ecuatoriana";
    }

    public void listMedic() {
        listMedic.showListMedic();
    }

}
