package dippy;

import dippy.storage.Storage;
import dippy.task.Task;
import dippy.task.TaskList;
import dippy.ui.MainWindow;
import dippy.ui.Ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main programme entry point
 */
public class Main extends Application {

    private Scene scene;

    /**
     * Starts up the Dippy chatbot.
     * @param args Optional variadic command line arguments.
     */
    public static void main(String[] args) {
        Ui.greet();
        Ui.printInstructions();
        execute();
        Ui.sayFarewell();
    }

    /**
     * Executes the Dippy chatbot main logic.
     */
    public static void execute() {
        // Prepare list of items for user to store into
        ArrayList<Task> tasks = Storage.load();

        // Keep prompting user after every user input, unless they want to
        // stop the programme
        TaskList.setTasks(tasks);
        Ui.interact(tasks);

        Storage.save(tasks);
    }

    /**
     * Executes the UI Dippy chatbot from the JavaFX UI
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            scene = new Scene(ap);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
