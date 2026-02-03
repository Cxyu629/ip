package storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import task.TaskList;

public final class Storage {
    private static final String TASKS_DIR = "tasks.dat";

    private static final File FILE = new File(TASKS_DIR);

    public static void save(TaskList tasks) throws IOException {
        if (!FILE.exists()) {
            FILE.createNewFile();
        }
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(FILE))) {
            tasks.save(out);
        }
    }

    public static TaskList load() throws IOException {
        if (!FILE.exists())
            return new TaskList();

        try (DataInputStream in = new DataInputStream(new FileInputStream(FILE))) {
            return TaskList.loadInstance(in);
        }
    }

}