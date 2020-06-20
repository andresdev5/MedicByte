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
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class Pediatric {
    
    Collection<ListMedic> listMedicPediatric = new ArrayList();
    public void createListMedicPediatric(){
        ListMedic listPediatric = new ListMedic();
        listPediatric.saveMedicPediatric();
        listMedicPediatric.add(listPediatric);
        
    }
    public void showList(){
        
    }
    
}
