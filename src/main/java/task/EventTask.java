package task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class EventTask extends Task {
    protected String from;
    protected String to;

    public EventTask(String description, String from, String to) {
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
        out.writeUTF(from);
        out.writeUTF(to);
    }

    @Override
    void load(DataInputStream in) throws IOException {
        super.load(in);
        this.from = in.readUTF();
        this.to = in.readUTF();
    }

    public static EventTask loadInstance(DataInputStream in) throws IOException {
        EventTask task = new EventTask(null, null, null);
        task.load(in);
        return task;
    }
}