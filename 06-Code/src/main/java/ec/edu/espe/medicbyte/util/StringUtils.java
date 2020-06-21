package ec.edu.espe.medicbyte.util;

/**
 *
 * @author Andres Jonathan J.
 */
public class StringUtils {
    public static String repeat(String sequence, int times) {
        return new String(new char[times]).replace("\0", sequence);
    }
}
