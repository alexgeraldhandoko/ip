package dippy.ui;

import dippy.exception.DippyException;
import dippy.logic.Response;
import dippy.parser.Parser;
import dippy.task.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Ui is the Class responsible for managing standard
 * output messages, and also standard formatting operations
 * for output messages.
 */
public class Ui {
    private static final String HORIZONTAL_LINE = "________________________________________"
        + "______________________________\n";
    private static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));

    public static String greetCli() {
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
        String furtherInfo = "To see what I can do, type in \"help\", then send!\n";
        System.out.print(wrap(logo + greeting + furtherInfo));
        return logo + greeting + furtherInfo;
    }

    public static String greetGui() {
        String greeting = "Hello! I'm Dippy\nWhat can I do for you?\n";
        String furtherInfo = "To see what I can do, type in \"help\", then send!\n";
        return greeting + furtherInfo;
    }

    public static String sayFarewell() {
        String farewell = "Bye. Hope to see you again soon!\n";
        return wrap(farewell);
    }

    public static String printInstructionsCli() {
        // Initialise a StringBuilder
        StringBuilder instructions = new StringBuilder();

        // Craft the instructions
        String instruction1 = """
                Below are the commands that you can do:
            """.stripIndent();
        String instruction2 = """
                * "list": Displays a list of items you have added so far
            """.stripIndent();
        String instruction3 = """
                * "todo [task]": Adds the task to the list of tasks that you have
            """.stripIndent();
        String instruction4 = """
                * "deadline [task] /by [YYYY-MM-DD]": Adds a deadline task to the tasklist, with the deadline
                displayed
            """.stripIndent();
        String instruction5 = """
                * "event [task] /from [start time/date] /to [end time/date]": Adds an event task to the tasklist
                with the start and end times displayed
            """.stripIndent();
        String instruction6 = """
                * "delete [task number]": Deletes the task specified by its number in the task list
            """.stripIndent();
        String instruction7 = """
                * "mark [task number]": Marks the task specified by its number as finished. This finished
                status shows up in the task list
            """.stripIndent();
        String instruction8 = """
                * "find [keyword]": Shows all the tasks that contains this keyword
            """.stripIndent();
        String instruction9 = """
            * "bye": To close this programme, you need to send me a whole message
               containing only "bye"
            """.stripIndent();

        // Gather the instructions inside the StringBuilder
        instructions.append(instruction1);
        instructions.append(instruction2);
        instructions.append(instruction3);
        instructions.append(instruction4);
        instructions.append(instruction5);
        instructions.append(instruction6);
        instructions.append(instruction7);
        instructions.append(instruction8);
        instructions.append(instruction9);

        // Print all instructions
        System.out.print(wrap(instructions.toString()));
        return wrap(instructions.toString());
    }

    public static String printInstructionsGui() {
        StringBuilder instructions = new StringBuilder();

        // Craft the instructions
        String instruction1 = """
                Below are the commands that you can do:
            """.stripIndent();
        String instruction2 = """
                * "list": Displays a list of items you have added so far
            """.stripIndent();
        String instruction3 = """
                * "todo [task]": Adds the task to the list of tasks that you have
            """.stripIndent();
        String instruction4 = """
                * "deadline [task] /by [YYYY-MM-DD]": Adds a deadline task to the tasklist, with the deadline
                displayed
            """.stripIndent();
        String instruction5 = """
                * "event [task] /from [start time/date] /to [end time/date]": Adds an event task to the tasklist
                with the start and end times displayed
            """.stripIndent();
        String instruction6 = """
                * "delete [task number]": Deletes the task specified by its number in the task list
            """.stripIndent();
        String instruction7 = """
                * "mark [task number]": Marks the task specified by its number as finished. This finished
                status shows up in the task list
            """.stripIndent();
        String instruction8 = """
                * "find [keyword]": Shows all the tasks that contains this keyword
            """.stripIndent();
        String instruction9 = """
            * "bye": To close this programme, you need to send me a whole message
               containing only "bye"
            """.stripIndent();

        // Gather the instructions inside the StringBuilder
        instructions.append(instruction1);
        instructions.append(instruction2);
        instructions.append(instruction3);
        instructions.append(instruction4);
        instructions.append(instruction5);
        instructions.append(instruction6);
        instructions.append(instruction7);
        instructions.append(instruction8);
        instructions.append(instruction9);

        // Print all instructions
        return wrap(instructions.toString());
    }

    public static String printInvalidCommandMessage() {
        String msg = "Oh no, I don't understand that command, please refer to the instructions below: ";
        return wrap(msg);
    }

    public static void interact(ArrayList<Task> tasks) {
        while (true) {
            try {
                // Store the whole input into a String
                System.out.println("Type your message here:");
                String userInput = gatherInput();

                // ChatGPT recommends: use trim() to get rid of newline characters
                userInput = userInput.trim();

                Response response = Parser.parseUserInput(userInput);
                System.out.println(response.getReply());
                if (response.getShouldExit()) {
                    break;
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
