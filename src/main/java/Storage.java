import java.io.*;
import java.util.ArrayList;

public class Storage {
    public static ArrayList<Task> load() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/dippy.txt"));
            String line = br.readLine();
            ArrayList<Task> out = new ArrayList<>();
            // Expected line format:
            // Task Type | done/not done | Task Name | Task Date
            // T/D/E | %d |
            while (line != null) {
                String[] arr = line.split(" \\| ");
                char taskType = arr[0].toCharArray()[0];
                Task newTask;
                if (taskType == 'D') {
                    newTask = Deadline.parseToTask(arr);
                } else if (taskType == 'E') {
                    newTask = Event.parseToTask(arr);
                } else {
                    newTask = Task.parseToTask(arr);
                }
                out.add(newTask);
                line = br.readLine();
            }
            return out;
        } catch (IOException e) {
            System.out.println("Failed to load tasks from file " + e.getMessage());
            return new ArrayList<>();
        }
    }

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
