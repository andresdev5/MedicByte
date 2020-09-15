package ec.edu.espe.medicbyte.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andres Jonathan J.
 */
public class ValidationUtilsTest {
    /**
     * Test of isValidUsername method, of class ValidationUtils.
     */
    @Test
    public void testIsValidUsername() {
        String username = "jhon_doe";
        assertTrue(ValidationUtils.isValidUsername(username));
    }

    /**
     * Test of isValidFullName method, of class ValidationUtils.
     */
    @Test
    public void testIsValidFullName() {
        String fullName = "John Doe";
        assertTrue(ValidationUtils.isValidFullName(fullName));
    }

    /**
     * Test of isValidIDCard method, of class ValidationUtils.
     */
    @Test
    public void testIsValidIDCard() {
        String idCard = "1726744293";
        assertTrue(ValidationUtils.isValidIDCard(idCard));
    }

    /**
     * Test of isValidEmail method, of class ValidationUtils.
     */
    @Test
    public void testIsValidEmail() {
        String email = "ksprwhite@medicbyte.com";
        assertTrue(ValidationUtils.isValidEmail(email));
    }
}
