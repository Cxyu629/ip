package xyxx.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class TodoTaskTest {

    @Test
    public void toStringPrefixTest() {
        TodoTask t = new TodoTask("buy milk");
        assertEquals("[T][ ] buy milk", t.toString());
    }

    @Test
    public void saveLoadRoundTripTest() throws IOException {
        TodoTask original = new TodoTask("remember");
        original.markAsDone();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        original.save(out);

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        TodoTask loaded = TodoTask.loadInstance(in);

        assertEquals(original.toString(), loaded.toString());
    }
}
