package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.service.MedicService;
import ec.edu.espe.tinyio.FileLine;
import ec.edu.espe.tinyio.FileManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class MedicServiceImpl implements MedicService {
    private FileManager fileManager;
    private final String DATA_FILENAME = "medics.csv";
    
    public MedicServiceImpl() {
        try {
            this.fileManager = new FileManager(DATA_FILENAME, true);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    @Override
    public int getTotalMedics() {
        return fileManager.countLines();
    }
    
    @Override
    public void saveMedic(Medic medic) {
        fileManager.write(medic.toString());
    }
    
    @Override
    public Medic getMedic(int id) {
        return getAllMedics().stream()
                .filter(medic -> medic.getId() == id)
                .findFirst().get();
    }

    @Override
    public List<Medic> getAllMedics() {
        List<FileLine> lines = fileManager.read();
        List<Medic> medics = new ArrayList<>();

        for (FileLine line : lines) {
            if (line.text().isEmpty()) {
                continue;
            }
            
            Medic medic = new Medic();

            String tokens[] = line.text().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

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
}
