package xyxx.task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A container for {@link Task} instances with convenience methods for serialization and common list
 * operations.
 */
public final class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Loads a {@link TaskList} from the input stream.
     * 
     * @param in the input stream
     * @return the loaded TaskList
     * @throws IOException if an I/O error occurs
     */
    public static TaskList loadInstance(DataInputStream in) throws IOException {
        TaskList taskList = new TaskList();
        taskList.load(in);
        return taskList;
    }

    /**
     * Returns the task at the given (zero-based) index.
     * 
     * @param index the index of the task to retrieve
     * @return the task at the specified index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Checks whether the provided index is valid for this list.
     * 
     * @param index the index to check
     * @return true if the index is valid, false otherwise
     */
    public boolean containsIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    /**
     * Adds a task to the end of the list.
     * 
     * @param task the task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes the task at the specified index.
     * 
     * @param index the index of the task to remove
     */
    public void remove(int index) {
        tasks.remove(index);
    }

    /**
     * Returns the number of tasks in this list.
     * 
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Filters the task list by searching for tasks whose descriptions contain the specified
     * keyword. The search is case-insensitive.
     *
     * @param keyword the keyword to search for
     * @return a new TaskList containing only tasks whose descriptions contain the keyword
     */
    public TaskList filterByKeyword(String keyword) {
        TaskList filteredList = new TaskList();
        for (Task task : tasks) {
            if (task.description.toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(task);
            }
        }
        return filteredList;
    }

    /**
     * Saves all tasks to the provided output stream using a simple type byte followed by each
     * task's serialized form.
     * 
     * @param out the output stream to write to
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Returns a numbered list representation of tasks.
     */
    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            message.append(String.format("% 3d. %s\n", (i + 1), tasks.get(i)));
        }
        return message.toString().stripTrailing();
    }

    /**
     * Loads tasks from the input stream until EOF. The stream is expected to contain a task type
     * byte followed by the task's serialized form.
     *
     * @param in the input stream to read from
     * @throws IOException if an I/O error occurs
     */
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
}
