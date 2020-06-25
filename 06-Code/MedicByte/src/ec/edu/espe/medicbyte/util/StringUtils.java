package ec.edu.espe.medicbyte.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;


/**
 *
 * @author Andres Jonathan J.
 */
public class StringUtils {
    private final static String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+"
        + "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0"
        + "c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b"
        + "\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
        + "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?"
        + "[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]"
        + "*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-"
        + "\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    
    public static String repeat(String sequence, int times) {
        if (times <= 0) {
            return "";
        }
        
        return new String(new char[times]).replace("\0", sequence);
    }
    
    public static Date parseDate(String input, String format) {
         try {
            return (new SimpleDateFormat(format)).parse(input);
        } catch (ParseException exception) {
            return null;
        }
    }
    
    public static Date parseDate(String input) {
         return parseDate(input, "dd/MM/yyyy");
    }
    
    public static LocalTime parseHour(String input, String format) {
        DateTimeFormatter timeFormat = DateTimeFormatter
            .ofPattern(format)
            .withResolverStyle(ResolverStyle.STRICT);
        
         try {
            return LocalTime.parse(input, timeFormat);
        } catch (DateTimeParseException | NullPointerException exception) {
            return null;
        }
    }
    
    public static LocalTime parseHour(String input) {
        return parseHour(input, "HH:mm");
    }
    
    public static boolean isValidDate(String input, String format) {
        return parseDate(input, format) != null;
    }
    
    public static boolean isValidDate(String input) {
        return parseDate(input) != null;
    }
    
    public static boolean isValidHour(String input, String format) {
        return parseHour(input, format) != null;
    }
    
    public static boolean isValidHour(String input) {
        return parseHour(input) != null;
    }
    
    public static boolean isValidCI(String id) {
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
    
    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
}
