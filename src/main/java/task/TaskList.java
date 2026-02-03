package task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public final class TaskList {
    ArrayList<Task> tasks = new ArrayList<>();

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public boolean containsIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void remove(int index) {
        tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    void load(DataInputStream in) throws IOException {
        int taskType;
        while ((taskType = in.read()) != -1) {
            Task task = switch (taskType) {
                case 1 -> TodoTask.loadInstance(in);
                case 2 -> EventTask.loadInstance(in);
                case 3 -> DeadlineTask.loadInstance(in);
                default -> null;
            };

            if (task != null) {
                tasks.add(task);
            }
        }
    }

    public static TaskList loadInstance(DataInputStream in) throws IOException {
        TaskList taskList = new TaskList();
        taskList.load(in);
        return taskList;
    }

    public void save(DataOutputStream out) throws IOException {
        for (Task task : tasks) {
            if (task instanceof TodoTask) {
                out.write(1);
            } else if (task instanceof EventTask) {
                out.write(2);
            } else if (task instanceof DeadlineTask) {
                out.write(3);
            } else {
                throw new UnsupportedOperationException(
                        String.format("Task type %s not supported.", task.getClass()));
            }
            task.save(out);
        }
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            message.append(String.format("% 3d. %s\n", (i + 1), tasks.get(i)));
        }
        return message.toString().stripTrailing();
    }
}
