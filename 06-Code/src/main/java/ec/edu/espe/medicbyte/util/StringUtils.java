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
    private final static String NON_THIN = "[^iIl1\\.,']";

    public static String truncate(String text, int max) {

        if (textWidth(text) <= max)
            return text;

        int end = text.lastIndexOf(' ', max - 3);

        if (end == -1)
            return text.substring(0, max-3) + "...";

        int newEnd = end;
        do {
            end = newEnd;
            newEnd = text.indexOf(' ', end + 1);

            // No more spaces.
            if (newEnd == -1)
                newEnd = text.length();

        } while (textWidth(text.substring(0, newEnd) + "...") < max);

        return text.substring(0, end) + "...";
    }
    
    
    
    public static Date parseDate(String input, String format) {
         try {
            return (new SimpleDateFormat(format)).parse(input);
        } catch (ParseException exception) {
            return null;
        }
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

   
}
