package dippy.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Controller for DialogBox component
 */
public class DialogBox extends HBox {
    @FXML
    private Label userName;
    @FXML
    private ImageView profileImage;

    public DialogBox(String text, Image i) {
        try {
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
    }

    public void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static DialogBox getUserDialog(String text, Image i) {
        return new DialogBox(text, i);
    }

    public static DialogBox getDippyDialog(String text, Image i) {
        DialogBox outputDialogBox = new DialogBox(text, i);
        outputDialogBox.flip();
        return outputDialogBox;
    }
}