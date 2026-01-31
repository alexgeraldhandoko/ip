package dippy.task;

public class Task {
    private boolean isDone;
    private String name;

    public Task(String name) {
        this.isDone = false;
        this.name = name;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Returns whether the task is done or not.
     */
    public boolean getDone() {
        return this.isDone;
    }

    /**
     * Returns the name of the task
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The entire task object in a format to be saved on disk, which can
     * later be parsed.
     */
    public String saveFormat() {
        String taskType = "T";
        String oneOrZero = this.isDone ? "1" : "0";
        return taskType + " | " + oneOrZero + " | " + this.name;
    }

    /**
     * Parses the user input into its corresponding Task object.
     * @param arr The line containing the task from the user input that has
     *            been split into individual Strings
     * @return The Task object that was represented by the user input
     */
    public static Task parseToTask(String[] arr) {
        boolean isTaskDone = arr[1].equals("1") ? true : false;
        String taskName = arr[2];
        Task newTask = new Task(taskName);
        if (isTaskDone) {
            newTask.markAsDone();
        }
        return newTask;
    }

    @Override
    public String toString() {
        if (isDone) {
            return "[T][X] " + this.name;
        } else {
            return "[T][ ] " + this.name;
        }
    }
}
