package dippy.ui;

import dippy.logic.Response;
import dippy.logic.ResponseType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static java.awt.SystemColor.text;

/**
 * Controller for DialogBox component
 */
public class DialogBox extends HBox {
    @FXML
    private Label userName;
    @FXML
    private ImageView profileImage;
    @FXML
    private VBox taskList;

    private int fitLength = 99;

    public DialogBox(String text, Image i) {
        try {
            // Load the fxml file into the current controller
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource(
                "/view/DialogBox.fxml"));

            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);

            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        userName.setText(text);
        profileImage.setImage(i);
        styleProfileImage();
    }

    public void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public void styleProfileImage() {
        // Obtain dimension details about the image
        // Gets the Image object out of the profileImage ImageView object
        Image profileImageImageObject = profileImage.getImage();
        double pixelWidth = profileImageImageObject.getWidth();
        double pixelHeight = profileImageImageObject.getHeight();

        // Crop the viewport of the image to a square
        double width = profileImageImageObject.getWidth();
        double height = profileImageImageObject.getHeight();
        double side = Math.min(width, height);
        double x = (width - side) / 2;
        double y = (height - side) / 2;
        Rectangle2D rectangleCrop = new Rectangle2D(x, y, side, side);
        profileImage.setViewport(rectangleCrop);

        // Clip the image to make it have a border radius
        int radius = 24;
        Rectangle clip = new Rectangle(fitLength, fitLength);
        clip.setArcWidth(radius);
        clip.setArcHeight(radius);
        profileImage.setClip(clip);
    }

    public static DialogBox getUserDialog(String responseMessage, Image i) {
        DialogBox outputDialogBox = new DialogBox(responseMessage, i);
        outputDialogBox.hideTaskList();
        return outputDialogBox;
    }

    public static DialogBox getDippyDialog(Response response, Image i) {
        if (response.getResponseType() == ResponseType.TASK_RESPONSE) {
            return getTaskDippyDialog(response, i);
        }
        DialogBox outputDialogBox = new DialogBox(response.getReply(), i);
        outputDialogBox.flip();
        outputDialogBox.hideTaskList();
        return outputDialogBox;
    }

    public static DialogBox getTaskDippyDialog(Response response, Image i) {
        DialogBox displayedDialog = new DialogBox(response.getReply(), i);
        ArrayList<HBox> taskHBoxes = Ui.convertTaskListToHBoxList(response.getResponseTaskList());
        displayedDialog.taskList.getChildren().addAll(taskHBoxes);
        displayedDialog.flip();
        displayedDialog.hideUserName();
        return displayedDialog;
    }

    public void hideTaskList() {
        taskList.setVisible(false);
        taskList.setManaged(false);
        taskList.getChildren().clear();
    }

    public void hideUserName() {
        userName.setVisible(false);
        userName.setManaged(false);
        userName.setText("");
    }
}
