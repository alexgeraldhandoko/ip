package dippy.task;

public class Event extends Task {
    private String startDate;
    private String endDate;

    public Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return The start date of the Event task.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @return The end date of the Event task.
     */
    public String getEndDate() {
        return endDate;
    }

    @Override
    public String saveFormat() {
        String taskType = "E";
        String oneOrZero = super.getDone() ? "1" : "0";
        return taskType + " | " + oneOrZero + " | " + super.getName() + " | "
            + startDate + " | " + endDate;
    }

    /**
     * Parses the user input into its corresponding Event task object.
     * @param fields The line containing the task from the user input that has
     *            been split into individual Strings
     * @return The Event task object that was represented by the user input
     */
    public static Event parseToTask(String[] fields) {
        assert(fields.length >= 6);
        boolean isTaskDone = fields[1].equals("1") ? true : false;
        String taskName = fields[2];
        String startDate = fields[3];
        String endDate = fields[4];
        Event newTask = new Event(taskName, startDate, endDate);
        if (isTaskDone) {
            newTask.markAsDone();
        }
        return newTask;
    }

    @Override
    public String toString() {
        String out = "[E]";
        if (getDone()) {
            out += "[X] ";
        } else {
            out += "[ ] ";
        }
        out += getName() + " (from: " + startDate + " to: " + endDate + ")";
        return out;
    }
}
