package xyxx.contact;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Contact {
    private String name;
    private String phoneNumber;
    private String email;
    private String notes;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getId() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append("Phone: ").append(phoneNumber).append("\n");
        if (email != null) {
            sb.append("Email: ").append(email).append("\n");
        }
        if (notes != null) {
            sb.append("Notes: ").append(notes).append("\n");
        }
        return sb.toString();
    }

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
