package dippy.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void deadline_create_correctFormat() {
        LocalDate date = LocalDate.parse("2022-12-01");
        Deadline urgentTask = new Deadline("urgent", date);
        assertEquals("[D][ ] urgent (by: Dec 1 2022)", urgentTask.toString());
    }
}
