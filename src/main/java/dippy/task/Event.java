package dippy.task;

public class Event extends Task {
    private String startDate;
    private String endDate;

    public Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    @Override
    public String saveFormat() {
        String taskType = "E";
        String oneOrZero = super.getDone() ? "1" : "0";
        return taskType + " | " + oneOrZero + " | " + super.getName() + " | "
            + startDate + " | " + endDate;
    }

    public static Event parseToTask(String[] arr) {
        boolean isTaskDone = arr[1].equals("1") ? true : false;
        String taskName = arr[2];
        String startDate = arr[3];
        String endDate = arr[4];
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
        out += "(from: " + this.startDate + " to: " + this.endDate + ")";
        return out;
    }
}
