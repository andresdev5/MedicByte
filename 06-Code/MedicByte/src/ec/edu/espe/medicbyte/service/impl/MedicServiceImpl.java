package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.service.MedicService;
import ec.edu.espe.tinyio.CsvFile;
import ec.edu.espe.tinyio.CsvRecord;
import ec.edu.espe.tinyio.FileManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public class MedicServiceImpl implements MedicService {
    private final FileManager medicsDb;
    
    public MedicServiceImpl() {
        medicsDb = new FileManager("data/medics.csv", true);
    }
    
    @Override
    public int getTotalMedics() {
        return medicsDb.countLines();
    }
    
    @Override
    public void saveMedic(Medic medic) {
        medicsDb.write(medic.toString());
    }
    
    @Override
    public Medic getMedic(int id) {
        return getAllMedics().stream()
                .filter(medic -> medic.getId() == id)
                .findFirst().get();
    }

    @Override
    public List<Medic> getAllMedics() {
        CsvFile csv = medicsDb.toCsv();
        List<Medic> medics = new ArrayList<>();

        for (CsvRecord record : csv.getRecords()) {            
            Medic medic = new Medic();
            List<String> values = record.getColumnValues();

            medic.setId(Integer.parseInt(values.get(0)));
            medic.setSpeciality(Speciality.values()[Integer.parseInt(values.get(1)) - 1]);
            medic.setName(values.get(2));
            medics.add(medic);
        }

        return medics;
    }
}
