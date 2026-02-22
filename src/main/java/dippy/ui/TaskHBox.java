package dippy.ui;

import dippy.task.Deadline;
import dippy.task.Event;
import dippy.task.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * This is a template class for an HBox that represents a task to be displayed by the Dippy GUI
 */
public class TaskHBox extends HBox {
    @FXML
    private ImageView taskType;
    @FXML
    private ImageView finishedTick;
    @FXML
    private Label taskDescription;

    public TaskHBox(Task task, int orderNumber) {
        // Load the fxml file into this controller
        try {
            FXMLLoader loader = new FXMLLoader(TaskHBox.class.getResource(""
                + "/view/TaskHBox.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load TaskHBox.fxml", e);
        }

        // Set the finished task logo if task is finished
        if (task.getDone()) {
            finishedTick.setImage(new Image(getClass().getResourceAsStream(""
                + "/images/tick.png")));
        }

        // Set the task type logo
        if (task instanceof Deadline) {
            taskType.setImage(new Image(getClass().getResourceAsStream(""
                + "/images/deadline.png")));
        } else if (task instanceof Event) {
            taskType.setImage(new Image(getClass().getResourceAsStream(""
                + "/images/event.png")));
        } else {
            taskType.setImage(new Image(getClass().getResourceAsStream(""
                + "/images/task.png")));
        }

        // Set the sizes for the icons
        double iconSize = 36;
        taskType.setFitWidth(iconSize);
        taskType.setFitHeight(iconSize);
        taskType.setPreserveRatio(true);
        taskType.setSmooth(true);

        finishedTick.setFitWidth(iconSize);
        finishedTick.setFitHeight(iconSize);
        finishedTick.setPreserveRatio(true);
        finishedTick.setSmooth(true);

        taskDescription.setText(orderNumber + " " + task.toString());
    }
}
