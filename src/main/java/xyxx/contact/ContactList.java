package xyxx.contact;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ContactList {
    private final ArrayList<Contact> contacts = new ArrayList<>();

    public void add(Contact contact) {
        contacts.add(contact);
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public int size() {
        return contacts.size();
    }

    public Optional<Contact> findName(String name) {
        for (Contact contact : contacts) {
            if (contact.toString().contains(name)) {
                return Optional.of(contact);
            }
        }
        return Optional.empty();
    }

    public Optional<Contact> findNumber(String phoneNumber) {
        for (Contact contact : contacts) {
            if (contact.toString().contains(phoneNumber)) {
                return Optional.of(contact);
            }
        }
        return Optional.empty();
    }

    public boolean remove(Contact contact) {
        return contacts.remove(contact);
    }

    public void save(DataOutputStream out) throws IOException {
        out.writeInt(contacts.size());
        for (Contact contact : contacts) {
            contact.save(out);
        }
    }

    void load(DataInputStream in) throws IOException {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            contacts.add(Contact.loadInstance(in));
        }
    }
}
