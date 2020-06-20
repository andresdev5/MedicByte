/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Michael Cobacango -ESPE -DCCO
 */
public class ListMedic {

    Collection<Medic> listMedic = new ArrayList();

    Medic medic = new Medic("1722066956", "Michael", "Cobacango",
            "Odontologo", "0999082067", "michaelpm63@gmail.com", Gender.MALE);
    Medic medic1 = new Medic("1710379346", "Alfonso", "Cadena",
            "Cirujano", "0985623744", "alfonso@gmail.com", Gender.MALE);
    Medic medic2 = new Medic("1714548846", "Juan", "Pardo",
            "Pediatric", "0958421365", "juanpa@gmail.com", Gender.MALE);
    Medic medic3 = new Medic("1711558915", "Maria", "Salazar",
            "Odontologa", "0985474547", "mariasa@gmail.com", Gender.FEMALE);
    Medic medic4 = new Medic("1723658945", "Paola", "Suarez",
            "Pediatric", "0936541225", "paolasu@gmail.com", Gender.FEMALE);
    Medic medic5 = new Medic("1752684123", "Damaris", "Cartagena",
            "Odontologa", "0947521635", "damaris@gmail.com", Gender.FEMALE);

    public void enterMedic() {
        listMedic.add(medic);
        listMedic.add(medic1);
        listMedic.add(medic2);
        listMedic.add(medic3);
        listMedic.add(medic4);
        listMedic.add(medic5);
    }

    public void showListMedic() {
        for (Medic medics : listMedic) {
            System.out.println(medics.toString());
        }
    }

    public void saveMedicPediatric() {
        for (Medic medics : listMedic) {
            if (medics.getSpeciality() == "Pediatric") {
                System.out.println(medics.toString());
            }
        }
    }

}
