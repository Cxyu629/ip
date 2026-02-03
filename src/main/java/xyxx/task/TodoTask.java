package xyxx.task;

import java.io.DataInputStream;
import java.io.IOException;

public class TodoTask extends Task {
    public TodoTask(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    public static TodoTask loadInstance(DataInputStream in) throws IOException {
        TodoTask task = new TodoTask(null);
        task.load(in);
        return task;
    }
}
