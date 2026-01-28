import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import task.DeadlineTask;
import task.EventTask;
import task.Task;
import task.TodoTask;

public abstract class TasksStorage {
    private static final String TASKS_DIR = "tasks.dat";
    
    private static final File FILE = new File(TASKS_DIR);

    public static void save(ArrayList<Task> tasks) throws IOException {
        if (!FILE.exists()) {
            FILE.createNewFile();
        }
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(FILE))) {
            for (Task task : tasks) {
                if (task instanceof TodoTask) {
                    out.write(1);
                } else if (task instanceof EventTask) {
                    out.write(2);
                } else if (task instanceof DeadlineTask) {
                    out.write(3);
                } else {
                    throw new UnsupportedOperationException(
                            String.format("Task type %s not supported.", task.getClass()));
                }
                task.save(out);
            }
        }
    }

    public static ArrayList<Task> load() throws IOException {
        System.out.println(FILE.getAbsolutePath());

        if (!FILE.exists())
            return new ArrayList<Task>();

        try (DataInputStream in = new DataInputStream(new FileInputStream(FILE))) {
            ArrayList<Task> tasks = new ArrayList<Task>();
            int taskType;
            while ((taskType = in.read()) != -1) {
                Task task = switch (taskType) {
                    case 1 -> TodoTask.loadInstance(in);
                    case 2 -> EventTask.loadInstance(in);
                    case 3 -> DeadlineTask.loadInstance(in);
                    default -> null;
                };

                if (task != null) {
                    tasks.add(task);
                }
            }
            return tasks;
        }
    }

}