package xyxx.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void markUnmarkTest() {
        Task t = new Task("example");
        assertEquals("[ ] example", t.toString());
        t.markAsDone();
        assertEquals("[X] example", t.toString());
        t.unmarkAsDone();
        assertEquals("[ ] example", t.toString());
    }

    @Test
    public void saveLoadRoundTripTest() throws IOException {
        Task original = new Task("saved", true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        original.save(out);

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        Task loaded = new Task(null);
        loaded.load(in);

        assertEquals(original.toString(), loaded.toString());
    }
}
