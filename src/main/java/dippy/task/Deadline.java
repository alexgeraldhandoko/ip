package dippy.task;

import dippy.parser.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Deadline extends Task {
    private LocalDate date;

    public Deadline(String name, LocalDate date) {
        super(name);
        this.date = date;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public static Deadline parseToTask(String[] arr) {
        boolean isTaskDone = arr[1].equals("1") ? true : false;
        String taskName = arr[2];
        String deadlineDate = arr[3];
        Deadline newTask = new Deadline(taskName, Parser.stringToDate(deadlineDate));
        return newTask;
    }

    @Override
    public String saveFormat() {
        String taskType = "D";
        String oneOrZero = super.getDone() ? "1" : "0";
        return taskType + " | " + oneOrZero + " | " + super.getName() + " | "
            + this.date.plus(0, ChronoUnit.YEARS);
    }

    @Override
    public String toString() {
        String out = "[D]";
        if (getDone()) {
            out += "[X] " + getName();
        } else {
            out += "[ ] " + getName();
        }
        out += " (by: " + this.date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
        return out;
    }
}
