package dippy.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    @Test
    public void task_newTaskObject_correctName() {
        String inputName = "I am a new task";
        Task inputTask = new Task(inputName);
        assertEquals(inputName, inputTask.getName());
    }

    @Test
    public void task_newTaskObject_successfulMarkDone() {
        Task inputTask = new Task("");
        inputTask.markAsDone();
        assertEquals(true, inputTask.getDone());
    }
}
