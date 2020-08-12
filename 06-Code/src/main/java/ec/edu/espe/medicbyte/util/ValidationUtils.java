package ec.edu.espe.medicbyte.util;

import static ec.edu.espe.medicbyte.util.StringUtils.parseDate;
import static ec.edu.espe.medicbyte.util.StringUtils.parseHour;

/**
 *
 * @author Andres Jonathan J.
 */
public class ValidationUtils {
    private final static String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+"
        + "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0"
        + "c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b"
        + "\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
        + "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?"
        + "[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]"
        + "*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-"
        + "\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    
    public static boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9\\-\\_\\.]+$");
    }
    
    public static boolean isValidFullName(String fullName) {
        return fullName.matches("^[\\w'\\-,.][^0-9_!¡?÷?¿\\/\\+=@#$%ˆ&*(){}|~<>;:[\\\\]]{2,}$");
    }
    
    public static boolean isValidIDCard(String id) {
        if (id.length() != 10) {
            return false;
        }
        
        if (!id.matches("^[0-9]+$")) {
            return false;
        }
        
        int lastDigit = Character.getNumericValue((id.charAt(id.length() - 1)));
        int digit;
        int sumPairs = 0;
        int oddSum = 0;

        for (int i = 0; i < 9; i++) {
            digit = Character.getNumericValue(id.charAt(i));

            if ((i + 1) % 2 == 0) {
                sumPairs += digit;
            } else {
                oddSum += (digit * 2) > 9 ? (digit * 2) - 9 : (digit * 2);
            }
        }

        int total = sumPairs + oddSum;
        int higher = (10 - (total % 10)) + total;

        if ((total % 10) == 0) {
            return lastDigit == 0;
        }
        
        return lastDigit == (higher - total);
    }
    

}
