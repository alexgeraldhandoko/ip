public class Deadline extends Task {
    private String date;

    public Deadline(String name, String date) {
        super(name);
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public static Deadline parseToTask(String[] arr) {
        boolean isTaskDone = arr[1].equals("1") ? true : false;
        String taskName = arr[2];
        String deadlineDate = arr[3];
        Deadline newTask = new Deadline(taskName, deadlineDate);
        return newTask;
    }

    @Override
    public String saveFormat() {
        String taskType = "D";
        String oneOrZero = super.getDone() ? "1" : "0";
        return taskType + " | " + oneOrZero + " | " + super.getName() + " | " + this.date;
    }

    @Override
    public String toString() {
        String out = "[D]";
        if (getDone()) {
            out += "[X] " + getName();
        } else {
            out += "[ ] " + getName();
        }
        out += " (by: " + this.date + ")";
        return out;
    }
}
