package task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsDone() {
        this.isDone = false;
    }

    public void save(DataOutputStream out) throws IOException {
        out.writeUTF(description);
        out.writeBoolean(isDone);
    }

    void load(DataInputStream in) throws IOException {
        this.description = in.readUTF();
        this.isDone = in.readBoolean();
    }
}