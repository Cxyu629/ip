package xyxx.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import xyxx.task.TaskList;

/**
 * Simple file-based storage for tasks. Uses a single file ("tasks.dat") in the
 * working directory.
 */
public final class Storage {
    private static final String TASKS_DIR = "tasks.dat";

    private static final File FILE = new File(TASKS_DIR);

    /**
     * Saves the provided {@link TaskList} to disk, creating the file if needed.
     *
     * @param tasks the tasks to save
     * @throws IOException if an I/O error occurs
     */
    public static void save(TaskList tasks) throws IOException {
        if (!FILE.exists()) {
            FILE.createNewFile();
        }
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(FILE))) {
            tasks.save(out);
        }
    }

    /**
     * Loads tasks from disk. Returns an empty {@link TaskList} if no file
     * exists.
     *
     * @return the loaded TaskList
     * @throws IOException if an I/O error occurs
     */
    public static TaskList load() throws IOException {
        if (!FILE.exists()) {
            return new TaskList();
        }

        try (DataInputStream in = new DataInputStream(new FileInputStream(FILE))) {
            return TaskList.loadInstance(in);
        }
    }

}
