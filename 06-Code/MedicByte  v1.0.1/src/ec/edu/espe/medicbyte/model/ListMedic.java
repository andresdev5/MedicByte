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

    public Medic getMedic(int id) {
        return getAllMedics().stream()
                .filter(medic -> medic.getId() == id)
                .findFirst().get();
    }

    public List<Medic> getAllMedics() {
        FileManager fileManager = new FileManager("ListMedic.txt");
        String content = fileManager.readFile();
        List<String> lines = Arrays.asList(content.split("\n"));
        List<Medic> medics = new ArrayList<>();

        for (String line : lines) {
            Medic medic = new Medic();

            String tokens[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

            if (tokens.length == 0) {
                continue;
            }

            medic.setId(Integer.parseInt(tokens[0]));
            medic.setSpeciality(Speciality.values()[Integer.parseInt(tokens[1]) - 1]);
            medic.setName(tokens[2].replace("\"", ""));

            medics.add(medic);
        }

        return medics;
    }

    public void showListMedic() {
        int index = 1;

        for (Medic medic : getAllMedics()) {
            System.out.printf(
                    "%d: %s (%s)\n", index,
                    medic.getName(), medic.getSpeciality().getLabel());
            index++;
        }
    }
}
