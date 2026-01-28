package task;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DeadlineTask extends Task {
    protected String by;
    
    public DeadlineTask(String description, String by) {
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
        out.writeUTF(by);
    }

    @Override
    void load(DataInputStream in) throws IOException {
        super.load(in);
        this.by = in.readUTF();
    }
    
    public static DeadlineTask loadInstance(DataInputStream in) throws IOException {
        DeadlineTask task = new DeadlineTask(null, null);
        task.load(in);
        return task;
    }
}