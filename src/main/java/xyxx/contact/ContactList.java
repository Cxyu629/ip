package xyxx.contact;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a list of contacts.
 */
public class ContactList {
    private final ArrayList<Contact> contacts = new ArrayList<>();

    /**
     * Adds a contact to the contact list.
     * 
     * @param contact the contact to add
     */
    public void add(Contact contact) {
        contacts.add(contact);
    }

    /**
     * Returns the list of contacts.
     * 
     * @return the list of contacts
     */
    public List<Contact> getContacts() {
        return contacts;
    }

    /**
     * Returns a list of contacts matching the given IDs.
     * 
     * @param ids the list of contact IDs
     * @return the list of matching contacts
     */
    public List<Contact> getContactsByIds(List<String> ids) {
        List<Contact> result = new ArrayList<>();
        for (String id : ids) {
            for (Contact contact : contacts) {
                if (contact.getId().equals(id)) {
                    result.add(contact);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Returns the number of contacts in the list.
     * 
     * @return the number of contacts
     */
    public int size() {
        return contacts.size();
    }

    /**
     * Finds a contact by name.
     * 
     * @param name the name to search for
     * @return an Optional containing the found contact, or empty if not found
     */
    public Optional<Contact> findName(String name) {
        for (Contact contact : contacts) {
            if (contact.toString().contains(name)) {
                return Optional.of(contact);
            }
        }
        return Optional.empty();
    }

    /**
     * Finds a contact by phone number.
     * 
     * @param phoneNumber the phone number to search for
     * @return an Optional containing the found contact, or empty if not found
     */
    public Optional<Contact> findNumber(String phoneNumber) {
        for (Contact contact : contacts) {
            if (contact.toString().contains(phoneNumber)) {
                return Optional.of(contact);
            }
        }
        return Optional.empty();
    }

    /**
     * Removes a contact from the contact list.
     * 
     * @param contact the contact to remove
     * @return true if the contact was removed, false otherwise
     */
    public boolean remove(Contact contact) {
        return contacts.remove(contact);
    }

    /**
     * Saves the contact list to the output stream.
     * 
     * @param out the DataOutputStream
     * @throws IOException if an I/O error occurs
     */
    public void save(DataOutputStream out) throws IOException {
        out.writeInt(contacts.size());
        for (Contact contact : contacts) {
            contact.save(out);
        }
    }

    /**
     * Loads the contact list from the input stream.
     * 
     * @param in the DataInputStream
     * @throws IOException if an I/O error occurs
     */
    public void load(DataInputStream in) throws IOException {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            contacts.add(Contact.loadInstance(in));
        }
    }
}
