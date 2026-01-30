import java.time.LocalDate;

public class Parser {
    public static LocalDate stringToDate(String date) {
        return LocalDate.parse(date);
    }
}
