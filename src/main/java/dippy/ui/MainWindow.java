package dippy.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    public void handleUserInput() {
        DialogBox userText = DialogBox.getUserDialog(userInput.getText(), userImage);
        DialogBox dippyText = DialogBox.getDippyDialog(getDippyResponse(userInput.getText()),
            dippyImage
        );
        dialogContainer.getChildren().addAll(userText, dippyText);
        userInput.clear();
    }

    public String getDippyResponse(String text) {
        return "Dippy heard: " + text;
    }
}
