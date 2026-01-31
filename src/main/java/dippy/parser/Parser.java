package dippy.parser;

import java.time.LocalDate;

public class Parser {
    /**
     * Parses the date from a String format and returns the
     * corresponding LocalDate object.
     * @param date The date String to be parsed.
     * @return The corresponding LocalDate object.
     */
    public static LocalDate stringToDate(String date) {
        return LocalDate.parse(date);
    }
}
