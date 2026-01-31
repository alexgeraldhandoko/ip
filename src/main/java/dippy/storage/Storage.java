package dippy.storage;

import dippy.task.Deadline;
import dippy.task.Event;
import dippy.task.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    /**
     * Loads a stored list of tasks from disk and returns it as an array of
     * tasks to the caller.
     */
    public static ArrayList<Task> load() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/dippy.txt"));
            String line = br.readLine();
            ArrayList<Task> loadedTasks = new ArrayList<>();
            // Expected line format:
            // dippy.task.Task Type | done/not done | dippy.task.Task Name | dippy.task.Task Date
            // T/D/E | %d |
            while (line != null) {
                String[] tokens = line.split(" \\| ");
                char taskType = tokens[0].toCharArray()[0];
                Task newTask;
                if (taskType == 'D') {
                    newTask = Deadline.parseToTask(tokens);
                } else if (taskType == 'E') {
                    newTask = Event.parseToTask(tokens);
                } else {
                    newTask = Task.parseToTask(tokens);
                }
                loadedTasks.add(newTask);
                line = br.readLine();
            }
            return loadedTasks;
        } catch (IOException e) {
            System.out.println("Failed to load tasks from file " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Saves a list of tasks into the local disk.
     * @param tasks The list of tasks that the caller wants to save
     *              to the disk.
     */
    public static void save(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        try {
            for (Task task : tasks) {
                sb.append(task.saveFormat() + "\n");
            }
            FileWriter fw = new FileWriter("data/dippy.txt");
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {
            System.out.println("Failed to save tasks to disk: " + e.getMessage());
        }
    }
}
