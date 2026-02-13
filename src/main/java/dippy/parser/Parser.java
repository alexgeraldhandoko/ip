package dippy.parser;

import dippy.exception.DippyException;
import dippy.logic.Response;
import dippy.task.Task;
import dippy.task.TaskList;
import dippy.ui.Ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class that is responsible for methods that parse user input
 * and transforms data from one format into another format
 * required by the Dippy application.
 */
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

    public static Response parseUserInput(String userInput) throws DippyException {
        // ChatGPT recommends: use equalsIgnoreCase instead of converting to lower case
        if (userInput.equalsIgnoreCase("bye")) {
            return new Response(Ui.sayFarewell(), true);
        } else if (userInput.equalsIgnoreCase("list")) {
            return TaskList.displayList(TaskList.getTasks());
        } else if (userInput.toLowerCase().matches("^mark\\s+\\d+$")) {
            return TaskList.finishTask(TaskList.getTasks(), userInput);
        } else if (userInput.toLowerCase().matches("^delete\\s+\\d+$")) {
            return TaskList.deleteTask(TaskList.getTasks(), userInput);
        } else if (userInput.toLowerCase().matches("^find\\s+.+$")) {
            return TaskList.findTask(TaskList.getTasks(), userInput);
        }
        else {
            return TaskList.addTask(TaskList.getTasks(), userInput);
        }
    }
}
