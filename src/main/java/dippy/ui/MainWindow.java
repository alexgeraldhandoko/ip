package dippy.ui;

import dippy.exception.DippyException;
import dippy.logic.Response;
import dippy.parser.Parser;
import dippy.storage.Storage;
import dippy.task.Task;
import dippy.task.TaskList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Controller for the MainWindow UI
 */
public class MainWindow extends AnchorPane {
    private static final Image userImage =
        new Image(MainWindow.class.getResourceAsStream("/images/hepi.jpg"));

    private static final Image dippyImage =
        new Image(MainWindow.class.getResourceAsStream("/images/elgato.png"));

    @FXML
    private TextField userInput;

    @FXML
    private VBox dialogContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        // Display greeting message
        DialogBox greetingText = DialogBox.getDippyDialog(Ui.greet(), dippyImage);
        dialogContainer.getChildren().addAll(greetingText);

        // Load the tasks into the GUI logic
        ArrayList<Task> tasks = Storage.load();
        TaskList.setTasks(tasks);

        // Add listeners
        dialogContainer.heightProperty().addListener(event -> scrollPane.setVvalue(1.0));
    }

    @FXML
    public void printDippyDialogBox(String text) {
        DialogBox dialogBox = DialogBox.getDippyDialog(text, dippyImage);
        dialogContainer.getChildren().addAll(dialogBox);
    }

    @FXML
    public void printUserDialogBox(String text) {
        DialogBox dialogBox = DialogBox.getUserDialog(text, dippyImage);
        dialogContainer.getChildren().addAll(dialogBox);
    }

    @FXML
    public void handleUserInput() throws DippyException {
        DialogBox userText = DialogBox.getUserDialog(userInput.getText(), userImage);

        Response dippyResponse = Parser.parseUserInput(userInput.getText());
        DialogBox dippyText = DialogBox.getDippyDialog(dippyResponse.getReply(), dippyImage);
        dialogContainer.getChildren().addAll(userText, dippyText);

        userInput.clear();
        Storage.save(TaskList.getTasks());
    }
}
