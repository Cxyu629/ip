package xyxx.task;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * A simple "todo" task.
 */
public class TodoTask extends Task {
    /**
     * Creates a new TodoTask with the given description.
     *
     * @param description the task description
     */
    public TodoTask(String description) {
        super(description);
    }

    /**
     * Returns a string representation prefixed with a todo marker ("[T]").
     *
     * @return formatted representation of the todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Loads a {@link TodoTask} from the input stream.
     *
     * @param in the input stream
     * @return the loaded TodoTask
     * @throws IOException if an I/O error occurs
     */
    public static TodoTask loadInstance(DataInputStream in) throws IOException {
        TodoTask task = new TodoTask(null);
        task.load(in);
        return task;
    }
}