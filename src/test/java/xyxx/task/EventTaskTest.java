package xyxx.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import xyxx.datetime.PartialDateTime;

public class EventTaskTest {

    @Test
    public void toStringContainsFromToTest() {
        PartialDateTime f = PartialDateTime.createDateTime(LocalDateTime.of(2021, 3, 4, 10, 0));
        PartialDateTime t = PartialDateTime.createDateTime(LocalDateTime.of(2021, 3, 4, 12, 0));
        EventTask e = new EventTask("meeting", f, t);
        String s = e.toString();
        assertTrue(s.contains("(from:") && s.contains("to:"));
    }

    @Test
    public void saveLoadRoundTripTest() throws IOException {
        PartialDateTime f = PartialDateTime.createDateTime(LocalDateTime.of(2021, 3, 4, 10, 0));
        PartialDateTime t = PartialDateTime.createDateTime(LocalDateTime.of(2021, 3, 4, 12, 0));
        EventTask original = new EventTask("event", f, t);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        original.save(out);

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        EventTask loaded = EventTask.loadInstance(in);

        assertEquals(original.toString(), loaded.toString());
    }
}
