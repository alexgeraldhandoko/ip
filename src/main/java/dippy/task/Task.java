package dippy.task;

/**
 * Task is the most basic form of a Task with only essential
 * information such as the name of the task and the completion
 * status of the task.
 */
public class Task {
    private boolean isDone;
    private String name;

    public Task(String name) {
        isDone = false;
        this.name = name;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Returns whether the task is done or not.
     */
    public boolean getDone() {
        return isDone;
    }

    /**
     * Returns the name of the task
     */
    public String getName() {
        return name;
    }

    /**
     * @return The entire task object in a format to be saved on disk, which can
     * later be parsed.
     */
    public String saveFormat() {
        String taskType = "T";
        String oneOrZero = isDone ? "1" : "0";
        return taskType + " | " + oneOrZero + " | " + name;
    }

    /**
     * Parses the user input into its corresponding Task object.
     * @param fields The line containing the task from the user input that has
     *            been split into individual Strings
     * @return The Task object that was represented by the user input
     */
    public static Task parseToTask(String[] fields) {
        boolean isTaskDone = fields[1].equals("1") ? true : false;
        String taskName = fields[2];
        Task newTask = new Task(taskName);
        if (isTaskDone) {
            newTask.markAsDone();
        }
        return newTask;
    }

    @Override
    public String toString() {
        if (isDone) {
            return "[T][X] " + name;
        } else {
            return "[T][ ] " + name;
        }
    }
}
