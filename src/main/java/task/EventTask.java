package task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import datetime.PartialDateTime;

public class EventTask extends Task {
    public static EventTask loadInstance(DataInputStream in) throws IOException {
        EventTask task = new EventTask(null, null, null);
        task.load(in);
        return task;
    }

    protected PartialDateTime from;

    protected PartialDateTime to;

    public EventTask(String description, PartialDateTime from, PartialDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), from, to);
    }

    @Override
    public void save(DataOutputStream out) throws IOException {
        super.save(out);
        from.save(out);
        to.save(out);
    }

    @Override
    void load(DataInputStream in) throws IOException {
        super.load(in);
        this.from = PartialDateTime.loadInstance(in);
        this.to = PartialDateTime.loadInstance(in);
    }
}