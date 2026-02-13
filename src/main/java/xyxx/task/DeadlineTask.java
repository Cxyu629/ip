package xyxx.task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

import xyxx.contact.Contact;
import xyxx.datetime.PartialDateTime;

/**
 * A task that has a deadline (a {@link PartialDateTime} value).
 */
public class DeadlineTask extends Task {
    /** The deadline of this task. */
    protected PartialDateTime by;

    /**
     * Constructs a new deadline task.
     *
     * @param description the description
     * @param by the deadline
     */
    public DeadlineTask(String description, PartialDateTime by, Collection<Contact> contacts) {
        super(description, contacts);
        this.by = by;
    }

    /**
     * Loads a {@link DeadlineTask} from the input stream.
     *
     * @param in the input stream
     * @return the loaded DeadlineTask
     * @throws IOException if an I/O error occurs
     */
    public static DeadlineTask loadInstance(DataInputStream in) throws IOException {
        DeadlineTask task = new DeadlineTask(null, null, null);
        task.load(in);
        return task;
    }

    /**
     * Returns a string representation including the deadline.
     *
     * @return formatted representation of the deadline task
     */
    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), by);
    }

    /**
     * Serializes this deadline task (including the deadline) to the output stream.
     *
     * @param out the output stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void save(DataOutputStream out) throws IOException {
        super.save(out);
        by.save(out);
    }

    /**
     * Loads this deadline task's state from the input stream.
     *
     * @param in the input stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    void load(DataInputStream in) throws IOException {
        super.load(in);
        this.by = PartialDateTime.loadInstance(in);
    }
}
