package dippy.task;

public class Task {
    private boolean isDone;
    private String name;

    public Task(String name) {
        isDone = false;
        this.name = name;
    }

    public void markAsDone() {
        isDone = true;
    }

    public boolean getDone() {
        return isDone;
    }

    public String getName() {
        return name;
    }

    public String saveFormat() {
        String taskType = "T";
        String oneOrZero = isDone ? "1" : "0";
        return taskType + " | " + oneOrZero + " | " + name;
    }

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
