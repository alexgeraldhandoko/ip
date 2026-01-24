import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Dippy {
    private static final String horizontalLine = "________________________________________"
            + "______________________________\n";

    public static void main(String[] args) {
        greet();
        printInstructions();
        execute();
    }

    /**
     * Outputs the default greeting message to the standard output.
     */
    public static void greet() {
        String greeting = "Hello! I'm Dippy\nWhat can I do for you?\n";
        System.out.print(wrap(greeting));
    }

    /**
     * Outputs the default farewell message to the standard output.
     */
    public static void sayFarewell() {
        String farewell = "Bye. Hope to see you again soon!\n";
        System.out.print(wrap(farewell));
    }

    public static void execute() {
        while (true) {
            try {
                // Store the whole input into a String
                System.out.println("Type your message here:");
                String userInput = gatherInput();

                // Echo the user input back to standard output
                // ChatGPT recommends:
                // * use equalsIgnoreCase for cleaner code instead
                //   of converting to lower case using .toLowerCase()
                // * use trim() to get rid of newline characters in the "bye"
                if (userInput.trim().equalsIgnoreCase("bye")) {
                    break;
                } else {
                    System.out.print(wrap("Dippy:\n" + userInput));
                }
            } catch (IOException e) {
                System.out.println("Failed to read user input.");
            }
        }
        sayFarewell();
    }

    /**
     * Returns the input string wrapped in two horizontal lines
     * One horizontal line above it and one under it
     *
     * @param input The input string to be wrapped around horizontal lines
     */
    public static String wrap(String input) {
        return horizontalLine + input + horizontalLine;
    }

    /**
     * Collects input from standard input into a single string, regardless of
     * number of lines in the input
     */
    public static String gatherInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        StringBuilder sb = new StringBuilder();
        while (line != null && !(line.equalsIgnoreCase("send"))) {
            sb.append(line).append("\n");
            line = br.readLine();
        }
        return sb.toString();
    }

    public static void printInstructions() {
        // Initialise a StringBuilder
        StringBuilder instructions = new StringBuilder();

        // Craft the instructions
        String instruction1 = "Type any message you like, followed by \"send\" in a new line!\n"
                + "Below are special commands:\n";
        String instruction2 = "* \"send\": You need to type \"send\" in a newline to send "
                + "me your message.\n";
        String instruction3 = "* \"bye\": To close this programme, you need to send me a whole "
                + "message\ncontaining only \"bye\" followed by a newline, then \"send\"\n";

        // Gather the instructions inside the StringBuilder
        instructions.append(instruction1);
        instructions.append(instruction2);
        instructions.append(instruction3);

        // Print all instructions
        System.out.print(wrap(instructions.toString()));
    }
}