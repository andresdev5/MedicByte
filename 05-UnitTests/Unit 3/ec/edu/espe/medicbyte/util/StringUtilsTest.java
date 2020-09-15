package ec.edu.espe.medicbyte.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andres Jonathan J.
 */
public class StringUtilsTest {
    /**
     * Test of truncate method, of class StringUtils.
     */
    @Test
    public void testTruncate() {
        String message = "Lorem ipsum dolor sit amet";
        assertTrue(StringUtils.truncate(message, 5).endsWith("..."));
    }

    /**
     * Test of repeat method, of class StringUtils.
     */
    @Test
    public void testRepeat() {
        assertTrue(StringUtils.repeat("*", 3).equals("***"));
    }

    /**
     * Test of parseDate method, of class StringUtils.
     */
    @Test
    public void testParseDate() {
        assertNotNull(StringUtils.parseDate("15/01/1993"));
    }

    /**
     * Test of parseDate method, of class StringUtils.
     */
    @Test
    public void testParseDateFormat() {
        assertNotNull(StringUtils.parseDate("15_01_1993", "dd_MM_yyyy"));
    }

    /**
     * Test of parseHour method, of class StringUtils.
     */
    @Test
    public void testParseHour() {
        assertNotNull(StringUtils.parseHour("10:00"));
    }

    /**
     * Test of parseHour method, of class StringUtils.
     */
    @Test
    public void testParseHourFormat() {
        assertNotNull(StringUtils.parseHour("10-00", "HH-mm"));
    }
}
