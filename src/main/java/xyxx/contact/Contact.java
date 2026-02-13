package xyxx.contact;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents a contact with a name, phone number, email, and notes.
 */
public class Contact {
    private String name;
    private String phoneNumber;
    private String email;
    private String notes;

    /**
     * Constructs a new contact with the given name and phone number.
     *
     * @param name the contact's name
     * @param phoneNumber the contact's phone number
     */
    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the email of this contact.
     * 
     * @param email the contact's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the notes for this contact.
     * 
     * @param notes additional notes about the contact
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Returns the unique identifier for this contact.
     * 
     * @return the contact's unique ID
     */
    public String getId() {
        return phoneNumber;
    }

    /**
     * Returns the name of this contact.
     * 
     * @return the contact's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the phone number of this contact.
     * 
     * @return the contact's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" (").append(phoneNumber).append(")");
        return sb.toString();
    }

    /**
     * Serializes this contact to the output stream.
     * 
     * @param out the DataOutputStream
     * @throws IOException if an I/O error occurs
     */
    public void save(DataOutputStream out) throws IOException {
        out.writeUTF(name);
        out.writeUTF(phoneNumber);
        out.writeBoolean(email != null);
        if (email != null) {
            out.writeUTF(email);
        }
        out.writeBoolean(notes != null);
        if (notes != null) {
            out.writeUTF(notes);
        }
    }

    /**
     * Deserializes a contact from the input stream.
     * 
     * @param in the DataInputStream
     * @return the deserialized Contact instance
     * @throws IOException if an I/O error occurs
     */
    public static Contact loadInstance(DataInputStream in) throws IOException {
        String name = in.readUTF();
        String phoneNumber = in.readUTF();
        Contact contact = new Contact(name, phoneNumber);
        if (in.readBoolean()) {
            contact.setEmail(in.readUTF());
        }
        if (in.readBoolean()) {
            contact.setNotes(in.readUTF());
        }
        return contact;
    }
}
