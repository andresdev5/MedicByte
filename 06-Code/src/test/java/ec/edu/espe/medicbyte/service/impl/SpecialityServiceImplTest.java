package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.service.SpecialityService;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andres Jonathan J.
 */
public class SpecialityServiceImplTest {
    @Test
    public void testAddSpeciality() {
        SpecialityService instance = new SpecialityServiceImpl();
        Speciality actual = new Speciality("ClinicalNutrition");
        instance.addSpeciality(actual);
        Speciality expected = instance.getSpeciality(actual.getName());
        
        assertEquals(expected.getName(), actual.getName());
    }
    
    @Test
    public void testSpecialitiesListNotEmpty() {
        SpecialityService instance = new SpecialityServiceImpl();
        List<Speciality> actual = instance.getAllSpecialities();
        
        assertFalse(actual.isEmpty());
    }
    
    @Test
    public void testSpecialityFields() {
        SpecialityService instance = new SpecialityServiceImpl();
        List<Speciality> specialities = instance.getAllSpecialities();
        Speciality actual = specialities.get(0);
        
        assertNotNull(actual.getId());
        assertNotNull(actual.getName());
    }
    
    @Test
    public void testSpecialityById() {
        SpecialityService instance = new SpecialityServiceImpl();
        Speciality actual = instance.getSpeciality(1);
        
        assertNotNull(actual);
    }
    
    @Test
    public void testSpecialityByName() {
        SpecialityService instance = new SpecialityServiceImpl();
        Speciality actual = instance.getSpeciality("Odontology");
        String expected = "Odontology";
        
        assertNotNull(actual);
        assertEquals(actual.getName(), expected);
    }
}
