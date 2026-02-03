package xyxx.task;

import org.junit.jupiter.api.Test;
import xyxx.datetime.PartialDateTime;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineTaskTest {

    @Test
    public void toStringContainsByTest() {
        PartialDateTime p = PartialDateTime.createDateTime(LocalDateTime.of(2021, 1, 2, 15, 30));
        DeadlineTask d = new DeadlineTask("submit", p);
        assertTrue(d.toString().contains("(by:"));
    }

    @Test
    public void saveLoadRoundTripTest() throws IOException {
        PartialDateTime p = PartialDateTime.createDateTime(LocalDateTime.of(2021, 1, 2, 15, 30));
        DeadlineTask original = new DeadlineTask("deadline", p);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        original.save(out);

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        DeadlineTask loaded = DeadlineTask.loadInstance(in);

        assertEquals(original.toString(), loaded.toString());
    }
}
