package dippy.ui;

import dippy.exception.DippyException;
import dippy.task.Task;
import dippy.task.TaskList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Ui {
    private static final String HORIZONTAL_LINE = "________________________________________"
        + "______________________________\n";
    private static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));

    public static void greet() {
        // This ASCII art was generated from:
        // https://patorjk.com/software/taag/
        String logo = " ______     _                            \n" +
            "|_   _ `.  (_)                           \n" +
            "  | | `. \\ __  _ .--.   _ .--.   _   __  \n" +
            "  | |  | |[  |[ '/'`\\ \\[ '/'`\\ \\[ \\ [  ] \n" +
            " _| |_.' / | | | \\__/ | | \\__/ | \\ '/ /  \n" +
            "|______.' [___]| ;.__/  | ;.__/[\\_:  /   \n" +
            "              [__|     [__|     \\__.'    \n\n";
        String greeting = "Hello! I'm Dippy\nWhat can I do for you?\n";
        System.out.print(wrap(logo + greeting));
    }

    public static void sayFarewell() {
        String farewell = "Bye. Hope to see you again soon!\n";
        System.out.print(wrap(farewell));
    }

    public static void printInstructions() {
        // Initialise a StringBuilder
        StringBuilder instructions = new StringBuilder();

        // Craft the instructions
        String instruction1 = """
                Type any message you like, followed by "send" in a new line!
                Below are special commands:
                
                """;
        String instruction2 = """
                * "send": You need to type "send" in a newline to send me your message
                          (this applies to special commands as well).
                """;
        String instruction3 = """
                * "list": Displays a list of items you have added so far
                """;
        String instruction4 = """
                * "bye": To close this programme, you need to send me a whole message
                         containing only "bye" followed by a newline, followed by "send"
                """;

        // Gather the instructions inside the StringBuilder
        instructions.append(instruction1);
        instructions.append(instruction2);
        instructions.append(instruction3);
        instructions.append(instruction4);

        // Print all instructions
        System.out.print(wrap(instructions.toString()));
    }

    public static void interact(ArrayList<Task> tasks) {
        while (true) {
            try {
                // Store the whole input into a String
                System.out.println("Type your message here:");
                String userInput = gatherInput();

                // ChatGPT recommends: use trim() to get rid of newline characters
                userInput = userInput.trim();

                // ChatGPT recommends: use equalsIgnoreCase instead of converting to lower case
                if (userInput.equalsIgnoreCase("bye")) {
                    break;
                } else if (userInput.equalsIgnoreCase("list")) {
                    TaskList.displayList(tasks);
                } else if (userInput.toLowerCase().matches("^mark\\s+\\d+$")) {
                    TaskList.finishTask(tasks, userInput);
                } else if (userInput.toLowerCase().matches("^delete\\s+\\d+$")) {
                    TaskList.deleteTask(tasks, userInput);
                } else {
                    TaskList.addTask(tasks, userInput);
                }
            } catch (IOException e) {
                System.out.println("Failed to read user input.");
            } catch (DippyException e) {
                String out = indent(e.getMessage());
                out = "Dippy:\n" + out;
                System.out.println(wrap(out));
            }
        }
    }

    /**
     * Collects input from standard input into a single string, regardless of
     * number of lines in the input
     */
    public static String gatherInput() throws IOException {
        String line = BR.readLine();
        StringBuilder sb = new StringBuilder();
        while (line != null && !(line.equalsIgnoreCase("send"))) {
            sb.append(line).append("\n");
            line = BR.readLine();
        }
        return sb.toString();
    }

    /**
     * Returns the input string wrapped in two horizontal lines
     * One horizontal line above it and one under it
     *
     * @param input The input string to be wrapped around horizontal lines
     */
    public static String wrap(String input) {
        return HORIZONTAL_LINE + input + HORIZONTAL_LINE;
    }

    /**
     * Left indents every line in the string passed to the function
     * @param input The input string the caller wants to indent
     * @return The input but with left indentation
     */
    public static String indent(String input) {
        String indentation = "    ";
        // ChatGPT recommends to break by \R, representing any line break
        // Obtain an array of individual lines from the input
        String[] lines = input.split("\\R");

        // Indent each of the line and add to a StringBuilder
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(indentation + line + "\n");
        }

        // Output the String representation of the StringBuilder
        return sb.toString();
    }
}
