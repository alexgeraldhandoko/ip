package dippy.task;

import dippy.exception.DippyCommandNotFoundException;
import dippy.exception.DippyException;
import dippy.exception.DippyTodoException;
import dippy.logic.Response;
import dippy.logic.ResponseType;
import dippy.parser.Parser;
import dippy.ui.Ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * TaskList class manages the list of tasks that Dippy keeps
 * track of for the user and provides interfaces for the user
 * to run different queries on the task list.
 */
public class TaskList {
    private static ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds the task into the task list that was given by parsing it first.
     * @param tasks The list of tasks that want to receive the new task.
     * @param userInput Task that the user wants to add to the list, in String format.
     * @throws DippyException If the user input is in an invalid format.
     */
    public static Response addTask(ArrayList<Task> tasks, String userInput) throws DippyException {
        // Declare variables common to every kind of task to be added
        Scanner sc = new Scanner(userInput);
        String command = sc.next();
        String out = "Got it. I've added the following item to your list:\n";

        // Create task and output message based on the kind of task
        if (command.equalsIgnoreCase("todo")) {
            out = addTodoTask(sc, out);
        } else if (command.equalsIgnoreCase("deadline")) {
            out = addDeadlineTask(sc, out);
        } else if (command.equalsIgnoreCase("event")) {
            out = addEventTask(sc, out);
        } else {
            throw new DippyCommandNotFoundException();
        }

        out += "Now you have " + tasks.size() + " tasks in the list.\n";
        return new Response(Ui.wrap(out));
    }

    /**
     * Adds a To-do task to the Task List and updates the output message
     * of the add to-do task operation accordingly
     * @param sc The scanner object used to read and parse the user input
     * @param out The output message that needs to be updated once the to-do
     *            task is added to the task list
     */
    public static String addTodoTask(Scanner sc, String out) throws DippyTodoException{
        // Initialise the String that contains the name of the task from the user input
        String taskName = "";

        // Check if the task description exists
        if (!sc.hasNext()) {
            throw new DippyTodoException();
        }
        // Gather the task name
        while (sc.hasNext()) {
            taskName += sc.next() + " ";
        }

        // Create the task with the corresponding name and add to the tasklist
        Task newTask = new Task(taskName);
        tasks.add(newTask);

        // Craft the out message
        out += newTask.toString() + "\n";
        out = Ui.indent(out);
        out = "Dippy:\n" + out;
        return out;
    }

    /**
     * Adds a Deadline task to the Task List and updates the output message
     * of the add deadline task operation accordingly
     * @param sc The scanner object used to read and parse the user input
     * @param out The output message that needs to be updated once the deadline
     *            task is added to the task list
     */
    public static String addDeadlineTask(Scanner sc, String out) {
        // Initialise the String that contains the name of the task from the user input
        String taskName = "";

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
        Task newTask = new Deadline(taskName, Parser.stringToDate(date));
        tasks.add(newTask);

        // Craft the out message
        out += newTask.toString();
        out = Ui.indent(out);
        out = "Dippy:\n" + out;
        return out;
    }

    public static String addEventTask(Scanner sc, String out) {
        // Initialise the String that contains the name of the task from the user input
        String taskName = "";

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

        // Create the new event task and add it to the task list
        Task newTask = new Event(taskName, startDate, endDate);
        tasks.add(newTask);

        // Craft the out message
        out += newTask.toString();
        out = Ui.indent(out);
        out = "Dippy:\n" + out;
        return out;
    }

    /**
     * Deletes the user-specified task from the given list of tasks.
     * @param tasks The list of tasks from which the user wants to delete task.
     * @param userInput The task from the list that the user wants to delete,
     *                  in String format
     */
    public static Response deleteTask(ArrayList<Task> tasks, String userInput) {
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
        return new Response(out);
    }

    /**
     * Marks a user-specified task within a given list as done.
     * @param tasks The list of tasks.
     * @param userInput The task that the user wants to mark as done,
     *                  in String format.
     */
    public static Response finishTask(ArrayList<Task> tasks, String userInput) {
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
        return new Response(out);
    }

    /**
     * Displays the user's current tasks to standard output.
     * @param tasks The list of tasks that the user wants to display to
     *              standard output.
     */
    public static Response displayList(ArrayList<Task> tasks) {
        return new Response("", ResponseType.TASK_RESPONSE, tasks, false);
    }

    public static Response findTask(ArrayList<Task> tasks, String userInput) {
        ArrayList<Task> out = new ArrayList<>();
        String keyword = userInput.substring(4).trim();
        for (Task task : tasks) {
            if (task.getName().contains(keyword)) {
                out.add(task);
            }
        }
        return displayList(out);
    }

    public static Response sortTask(ArrayList<Task> tasks, String userInput) {
        ArrayList<Task> out = new ArrayList<>(tasks);
        Collections.sort(out, (taskA, taskB)
            -> taskA.getName().compareToIgnoreCase(taskB.getName()));
        return displayList(out);
    }

    public static ArrayList<Task> getTasks() {
        return tasks;
    }

    public static void setTasks(ArrayList<Task> tasks) {
        TaskList.tasks = tasks;
    }
}
