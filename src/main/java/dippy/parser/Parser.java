package dippy.parser;

import dippy.exception.DippyException;
import dippy.logic.Response;
import dippy.task.TaskList;
import dippy.ui.Ui;

import java.time.LocalDate;

/**
 * Class that is responsible for methods that parse user input
 * and transforms data from one format into another format
 * required by the Dippy application.
 */
public class Parser {
    private static String byeCommandRegex = "bye";
    private static String listCommandRegex = "list";
    private static String markCommandRegex = "^mark\\s+\\d+$";
    private static String deleteCommandRegex = "^delete\\s+\\d+$";
    private static String findCommandRegex = "^find\\s+.+$";

    /**
     * Parses the date from a String format and returns the
     * corresponding LocalDate object.
     * @param date The date String to be parsed.
     * @return The corresponding LocalDate object.
     */
    public static LocalDate stringToDate(String date) {
        return LocalDate.parse(date);
    }

    public static Response parseUserInput(String userInput) throws DippyException {
        // ChatGPT recommends: use equalsIgnoreCase instead of converting to lower case
        if (userInput.equalsIgnoreCase(byeCommandRegex)) {
            return new Response(Ui.sayFarewell(), true);
        } else if (userInput.equalsIgnoreCase(listCommandRegex)) {
            return TaskList.displayList(TaskList.getTasks());
        } else if (userInput.toLowerCase().matches(markCommandRegex)) {
            return TaskList.finishTask(TaskList.getTasks(), userInput);
        } else if (userInput.toLowerCase().matches(deleteCommandRegex)) {
            return TaskList.deleteTask(TaskList.getTasks(), userInput);
        } else if (userInput.toLowerCase().matches(findCommandRegex)) {
            return TaskList.findTask(TaskList.getTasks(), userInput);
        }
        else {
            return TaskList.addTask(TaskList.getTasks(), userInput);
        }
    }
}
