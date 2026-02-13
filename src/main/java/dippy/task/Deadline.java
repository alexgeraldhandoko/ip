package dippy.task;

import dippy.parser.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Deadline is a Task that has a due date displayed in a MMM-DD-YYYY format
 */
public class Deadline extends Task {
    private LocalDate date;

    public Deadline(String name, LocalDate date) {
        super(name);
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Parses the user input into its corresponding Deadline task object.
     * @param fields The line containing the task from the user input that has
     *            been split into individual Strings
     * @return The Deadline task object that was represented by the user input
     */
    public static Deadline parseToTask(String[] fields) {
        String taskName = fields[2];
        String deadlineDate = fields[3];
        Deadline newTask = new Deadline(taskName, Parser.stringToDate(deadlineDate));
        return newTask;
    }

    @Override
    public String saveFormat() {
        String taskType = "D";
        String oneOrZero = super.getDone() ? "1" : "0";
        return taskType + " | " + oneOrZero + " | " + super.getName() + " | "
            + date.plus(0, ChronoUnit.YEARS);
    }

    @Override
    public String toString() {
        String out = "[D]";
        if (getDone()) {
            out += "[X] " + getName();
        } else {
            out += "[ ] " + getName();
        }
        out += " (by: " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
        return out;
    }
}
