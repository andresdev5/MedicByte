/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.model;

import ec.edu.espe.medicbyte.utils.FileManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class ListMedic {

    Collection<Medic> listMedic = new ArrayList<Medic>();
    Medic medic = new Medic();
    public void saveList() {
        listMedic.add(medic);
        System.out.println(medic.toString());
    }
    public void showListMedic() {
        String content;
        FileManager fileManager = new FileManager("ListMedic.txt");
        content = fileManager.readFile();
        List<String> medics = Arrays.asList(content.split("\n"));
        
        for(String medic : medics){
            System.out.println(medic);
        }
        }
    }

