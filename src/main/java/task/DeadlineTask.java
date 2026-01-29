package task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import datetime.PartialDateTime;

public class DeadlineTask extends Task {
    public static DeadlineTask loadInstance(DataInputStream in) throws IOException {
        DeadlineTask task = new DeadlineTask(null, null);
        task.load(in);
        return task;
    }

    protected PartialDateTime by;

    public DeadlineTask(String description, PartialDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), by);
    }

    @Override
    public void save(DataOutputStream out) throws IOException {
        super.save(out);
        by.save(out);
    }

    @Override
    void load(DataInputStream in) throws IOException {
        super.load(in);
        this.by = PartialDateTime.loadInstance(in);
    }
}