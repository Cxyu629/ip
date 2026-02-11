package xyxx.task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents a generic task with a description and a completion flag.
 */
public class Task {
    /** The description of this task. */
    protected String description;
    /** Whether this task is completed. */
    protected boolean isDone;

    /**
     * Constructs a new, not-yet-completed task with the given description.
     *
     * @param description the task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns a textual representation including completion state and
     * description (e.g. "[X] Finish report").
     *
     * @return formatted representation of the task
     */
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Serializes this task to the output stream.
     *
     * @param out the output stream
     * @throws IOException if an I/O error occurs
     */
    public void save(DataOutputStream out) throws IOException {
        out.writeUTF(description);
        out.writeBoolean(isDone);
    }

    /**
     * Loads the task's state from the input stream.
     *
     * @param in the input stream
     * @throws IOException if an I/O error occurs
     */
    void load(DataInputStream in) throws IOException {
        this.description = in.readUTF();
        this.isDone = in.readBoolean();
    }
}
