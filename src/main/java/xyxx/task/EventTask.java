package xyxx.task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import xyxx.datetime.PartialDateTime;

/**
 * A task representing an event with a start and end {@link PartialDateTime}.
 */
public class EventTask extends Task {
    /**
     * Loads an EventTask from the input stream.
     */
    public static EventTask loadInstance(DataInputStream in) throws IOException {
        EventTask task = new EventTask(null, null, null);
        task.load(in);
        return task;
    }

    protected PartialDateTime from;

    protected PartialDateTime to;

    /**
     * Constructs an event with a description and a time range.
     *
     * @param description the description
     * @param from        the start time
     * @param to          the end time
     */
    public EventTask(String description, PartialDateTime from, PartialDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation including the start and end times.
     *
     * @return formatted representation of the event task
     */
    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), from, to);
    }

    /**
     * Serializes this event task (including start/end) to the output stream.
     *
     * @param out the output stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void save(DataOutputStream out) throws IOException {
        super.save(out);
        from.save(out);
        to.save(out);
    }

    /**
     * Loads this event task's state from the input stream.
     *
     * @param in the input stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    void load(DataInputStream in) throws IOException {
        super.load(in);
        this.from = PartialDateTime.loadInstance(in);
        this.to = PartialDateTime.loadInstance(in);
    }
}