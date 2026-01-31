package dippy;

import dippy.storage.Storage;
import dippy.task.Task;
import dippy.ui.Ui;

import java.util.ArrayList;

public class Main {

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
        Ui.interact(tasks);

        Storage.save(tasks);
    }
}