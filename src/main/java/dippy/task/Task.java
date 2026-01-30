package dippy.task;

public class Task {
    private boolean isDone;
    private String name;

    public Task(String name) {
        this.isDone = false;
        this.name = name;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public boolean getDone() {
        return this.isDone;
    }

    public String getName() {
        return this.name;
    }

    public String saveFormat() {
        String taskType = "T";
        String oneOrZero = this.isDone ? "1" : "0";
        return taskType + " | " + oneOrZero + " | " + this.name;
    }

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
