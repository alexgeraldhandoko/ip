package dippy.task;

import dippy.exception.DippyCommandNotFoundException;
import dippy.exception.DippyException;
import dippy.exception.DippyTodoException;
import dippy.parser.Parser;
import dippy.ui.Ui;

import java.util.ArrayList;
import java.util.Scanner;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds the task into the task list that was given by parsing it first.
     * @param tasks The list of tasks that want to receive the new task.
     * @param userInput Task that the user wants to add to the list, in String format.
     * @throws DippyException If the user input is in an invalid format.
     */
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
            out = Ui.indent(out);
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
            newTask = new Deadline(taskName, Parser.stringToDate(date));
            tasks.add(newTask);

            // Craft the out message
            out += newTask.toString();
            out = Ui.indent(out);
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
            out = Ui.indent(out);
            out = "Dippy:\n" + out;
        } else {
            throw new DippyCommandNotFoundException();
        }
        out += "Now you have " + tasks.size() + " tasks in the list.\n";
        System.out.println(Ui.wrap(out));
    }

    /**
     * Deletes the user-specified task from the given list of tasks.
     * @param tasks The list of tasks from which the user wants to delete task.
     * @param userInput The task from the list that the user wants to delete,
     *                  in String format
     */
    public static void deleteTask(ArrayList<Task> tasks, String userInput) {
        // Obtain the index from user input
        Scanner sc = new Scanner(userInput);
        sc.next();
        int index = sc.nextInt();

        // Remove task
        Task removedTask = tasks.remove(index - 1);

        // Craft output message
        String out = "Noted. I've removed this task:\n";
        out += Ui.indent(removedTask.toString()) + "\n";
        out += "You now have " + tasks.size() + " tasks in the list.\n";
        out = Ui.wrap(out);

        // Print output message
        System.out.println(out);
    }

    /**
     * Marks a user-specified task within a given list as done.
     * @param tasks The list of tasks.
     * @param userInput The task that the user wants to mark as done,
     *                  in String format.
     */
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
        String out = "Dippy:\n" + Ui.indent(sb.toString());
        out = Ui.wrap(out);
        System.out.print(out);
    }

    /**
     * Displays the user's current tasks to standard output.
     * @param tasks The list of tasks that the user wants to display to
     *              standard output.
     */
    public static void displayList(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1) + ". " + tasks.get(i) + "\n");
        }
        String out = "Here are the tasks in your list:\n";
        out += Ui.indent(sb.toString());
        out = Ui.indent(out);
        out = "Dippy:\n" + out;
        System.out.println(Ui.wrap(out));
    }
}
