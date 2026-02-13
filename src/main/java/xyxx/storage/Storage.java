package xyxx.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import xyxx.contact.ContactList;
import xyxx.task.TaskList;

/**
 * Simple file-based storage for tasks. Uses a single file ("tasks.dat") in the working directory.
 */
public final class Storage {
    private static final String TASKS_DIR = "tasks.dat";
    private static final String CONTACTS_DIR = "contacts.dat";

    private static final File TASKS_FILE = new File(TASKS_DIR);
    private static final File CONTACTS_FILE = new File(CONTACTS_DIR);

    /**
     * Saves the provided {@link TaskList} to disk, creating the file if needed.
     *
     * @param tasks the tasks to save
     * @throws IOException if an I/O error occurs
     */
    public static void saveTasks(TaskList tasks) throws IOException {
        if (!TASKS_FILE.exists()) {
            TASKS_FILE.createNewFile();
        }

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(TASKS_FILE))) {
            tasks.save(out);
        }
    }

    /**
     * Loads tasks from disk. Returns an empty {@link TaskList} if no file exists.
     *
     * @return the loaded {@link TaskList}
     * @throws IOException if an I/O error occurs
     */
    public static TaskList loadTaskList() throws IOException {
        if (!TASKS_FILE.exists()) {
            return new TaskList();
        }

        try (DataInputStream in = new DataInputStream(new FileInputStream(TASKS_FILE))) {
            return TaskList.loadInstance(in);
        }
    }

    /**
     * Saves the provided {@link ContactList} to disk, creating the file if needed.
     *
     * @param contacts the contacts to save
     * @throws IOException if an I/O error occurs
     */
    public static void saveContacts(ContactList contacts) throws IOException {
        if (!CONTACTS_FILE.exists()) {
            CONTACTS_FILE.createNewFile();
        }

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(CONTACTS_FILE))) {
            contacts.save(out);
        }
    }

    /**
     * Loads contacts from disk. Returns an empty {@link ContactList} if no file exists.
     *
     * @return the loaded {@link ContactList}
     * @throws IOException if an I/O error occurs
     */
    public static ContactList loadContactList() throws IOException {
        if (!CONTACTS_FILE.exists()) {
            return new ContactList();
        }

        try (DataInputStream in = new DataInputStream(new FileInputStream(CONTACTS_FILE))) {
            ContactList contactList = new ContactList();
            contactList.load(in);
            return contactList;
        }
    }
}
