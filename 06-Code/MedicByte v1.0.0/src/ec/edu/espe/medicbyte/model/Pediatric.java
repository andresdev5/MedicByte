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
public class Pediatric {

    private String description;
    ListMedic listMedicPediatric = new ListMedic();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        description = "Es una Especialidad";
    }

    public void medicPediatric() {
        listMedicPediatric.saveMedicPediatric();
    }

}
