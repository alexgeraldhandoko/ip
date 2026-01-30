import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dippy {
    private static final String horizontalLine = "________________________________________"
            + "______________________________\n";
    private static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        System.out.println("user.dir = " + System.getProperty("user.dir"));
        greet();
        printInstructions();
        execute();
        sayFarewell();
    }

    /**
     * Outputs the default greeting message to the standard output.
     */
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

    /**
     * Outputs the default farewell message to the standard output.
     */
    public static void sayFarewell() {
        String farewell = "Bye. Hope to see you again soon!\n";
        System.out.print(wrap(farewell));
    }

    public static void execute() {
        // Prepare list of items for user to store into
        ArrayList<Task> tasks = Storage.load();

        // Keep prompting user after every user input, unless they want to
        // stop the programme
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
                    displayList(tasks);
                } else if (userInput.toLowerCase().matches("^mark\\s+\\d+$")) {
                    finishTask(tasks, userInput);
                } else if (userInput.toLowerCase().matches("^delete\\s+\\d+$")) {
                    deleteTask(tasks, userInput);
                } else {
                    addTask(tasks, userInput);
                }
            } catch (IOException e) {
                System.out.println("Failed to read user input.");
            } catch (DippyException e) {
                String out = indent(e.getMessage());
                out = "Dippy:\n" + out;
                System.out.println(wrap(out));
            }
        }
        Storage.save(tasks);
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

    public static void displayList(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1) + ". " + tasks.get(i) + "\n");
        }
        String out = "Here are the tasks in your list:\n";
        out += indent(sb.toString());
        out = indent(out);
        out = "Dippy:\n" + out;
        System.out.println(wrap(out));
    }

    public static void finishTask(ArrayList<Task> tasks, String userInput) {
        // Initialise the StringBuilder for the print output
        StringBuilder sb = new StringBuilder();

        // Obtain the integer from the command and assign it to a variable
        Scanner sc = new Scanner(userInput);
        int index = 0; // Although initialising to a phony value is recommended against by CS2103,
        // Intellij complains if it is not initialised definitely later on
        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                index = sc.nextInt() - 1;
            } else {
                sc.next();
            }
        }

        // Mark the task with the corresponding input as done
        tasks.get(index).markAsDone();

        // Output the response message
        sb.append("Nice! I've marked this task as done:\n");
        sb.append(tasks.get(index) + "\n");
        String out = "Dippy:\n" + indent(sb.toString());
        out = wrap(out);
        System.out.print(out);
    }

    public static void addTask(ArrayList<Task> tasks, String userInput) throws DippyException {
        // Declare variables common to every kind of task to be added
        Scanner sc = new Scanner(userInput);
        String command = sc.next();
        String out = "Got it. I've added the following item to your list:\n";
        String taskName = "";
        Task newTask;

        // Create task and output message based on the kind of task
        if (command.equalsIgnoreCase("todo")) {
            // Check if the task description exists
            if (!sc.hasNext()) {
                throw new DippyTodoException();
            }
            // Gather the task name
            while (sc.hasNext()) {
                taskName += sc.next() + " ";
            }

            // Create the task with the corresponding name and add to the tasklist
            newTask = new Task(taskName);
            tasks.add(newTask);

            // Craft the out message
            out += newTask.toString() + "\n";
            out = indent(out);
            out = "Dippy:\n" + out;
        } else if (command.equalsIgnoreCase("deadline")) {
            // Gather the task name until the date description
            while (sc.hasNext()) {
                String tmp = sc.next();
                if (tmp.equalsIgnoreCase("/by")) {
                    break;
                }
                taskName += tmp + " ";
            }
            taskName = taskName.trim();

            // Gather the task end date
            String date = "";
            while (sc.hasNext()) {
                date += sc.next() + " ";
            }
            date = date.trim();

            // Create the deadline task and add it to the tasklist
            newTask = new Deadline(taskName, date);
            tasks.add(newTask);

            // Craft the out message
            out += newTask.toString();
            out = indent(out);
            out = "Dippy:\n" + out;
        } else if (command.equalsIgnoreCase("event")) {
            // Gather the task name until the task description
            while (sc.hasNext()) {
                String tmp = sc.next();
                if (tmp.equalsIgnoreCase("/from")) {
                    break;
                }
                taskName += tmp + " ";
            }
            taskName = taskName.trim();

            // Gather the start date
            String startDate = "";
            while (sc.hasNext()) {
                String tmp = sc.next();
                if (tmp.equalsIgnoreCase("/to")) {
                    break;
                }
                startDate += tmp + " ";
            }
            startDate = startDate.trim();

            // Gather the end date
            String endDate = "";
            while (sc.hasNext()) {
                endDate += sc.next() + " ";
            }
            endDate = endDate.trim();

            // Create the new deadline task and add it to the task list
            newTask = new Event(taskName, startDate, endDate);
            tasks.add(newTask);

            // Craft the out message
            out += newTask.toString();
            out = indent(out);
            out = "Dippy:\n" + out;
        } else {
            throw new DippyCommandNotFoundException();
        }
        out += "Now you have " + tasks.size() + " tasks in the list.\n";
        System.out.println(wrap(out));
    }

    public static void deleteTask(ArrayList<Task> tasks, String userInput) {
        // Obtain the index from user input
        Scanner sc = new Scanner(userInput);
        sc.next();
        int index = sc.nextInt();

        // Remove task
        Task removedTask = tasks.remove(index - 1);

        // Craft output message
        String out = "Noted. I've removed this task:\n";
        out += indent(removedTask.toString()) + "\n";
        out += "You now have " + tasks.size() + " tasks in the list.\n";
        out = wrap(out);

        // Print output message
        System.out.println(out);
    }
}