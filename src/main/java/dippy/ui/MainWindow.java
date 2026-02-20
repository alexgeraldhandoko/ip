package dippy.ui;

import dippy.exception.DippyException;
import dippy.logic.Response;
import dippy.parser.Parser;
import dippy.storage.Storage;
import dippy.task.Task;
import dippy.task.TaskList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
        DialogBox greetingText = DialogBox.getDippyDialog(Ui.greetGui(), dippyImage);
        dialogContainer.prefWidthProperty().bind(scrollPane.widthProperty().subtract(30));
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
        Response dippyResponse;
        try {
            dippyResponse = Parser.parseUserInput(userInput.getText());
        } catch (DippyException e) {
            dippyResponse = new Response(e.getMessage(), false);
        }

        // Invalid user input shouldn't reach this line (DippyException should be thrown)
        // Check if Response object obtained after parsing valid user input
        assert(dippyResponse != null);

        DialogBox dippyText = DialogBox.getDippyDialog(dippyResponse.getReply(), dippyImage);
        dialogContainer.getChildren().addAll(
            wrapRight(userText), wrapLeft(dippyText));

        userInput.clear();
        Storage.save(TaskList.getTasks());
    }

    private HBox wrapRight(DialogBox db) {
        HBox wrapper = new HBox(db);
        wrapper.setAlignment(Pos.TOP_RIGHT);
        bindWrapperWidth(wrapper);
        bindDialogBoxWidth(db, wrapper);
        return wrapper;
    }

    private HBox wrapLeft(DialogBox db) {
        HBox wrapper = new HBox(db);
        wrapper.setAlignment(Pos.TOP_LEFT);
        bindWrapperWidth(wrapper);
        return wrapper;
    }

    private void bindWrapperWidth(HBox wrapper) {
        wrapper.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        wrapper.maxWidthProperty().bind(scrollPane.widthProperty().subtract(20));
    }

    private void bindDialogBoxWidth(DialogBox db, HBox parentWrapper) {
        db.prefWidthProperty().bind(parentWrapper.widthProperty().subtract(20));
        db.maxWidthProperty().bind(parentWrapper.widthProperty().subtract(20));
    }
}
