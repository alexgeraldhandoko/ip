package dippy.storage;

import dippy.task.Deadline;
import dippy.task.Event;
import dippy.task.Task;

import java.io.*;
import java.util.ArrayList;

/**
 * Class to handle storage-related logic, mainly saving to and loading from disk memory
 */
public class Storage {
    /**
     * Loads a stored list of tasks from disk and returns it as an array of
     * tasks to the caller.
     */
    public static final String STORAGE_FILE_PATH = System.getProperty("user.home") + "/.dippy/dippy.txt";

    public static ArrayList<Task> load() {
        createStorageDir();
        try {
            BufferedReader br = new BufferedReader(new FileReader(STORAGE_FILE_PATH));
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
        createStorageDir();
        StringBuilder sb = new StringBuilder();
        try {
            for (Task task : tasks) {
                sb.append(task.saveFormat() + "\n");
            }
            FileWriter fw = new FileWriter(STORAGE_FILE_PATH);
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {
            System.out.println("Failed to save tasks to disk: " + e.getMessage());
        }
    }

    /**
     * Creates the directory in which to store the data if it doesn't exist
     */
    public static void createStorageDir() {
        File file = new File(STORAGE_FILE_PATH).getParentFile();
        file.mkdirs();
    }
}
