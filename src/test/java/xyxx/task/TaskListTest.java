package xyxx.task;

import org.junit.jupiter.api.Test;
import xyxx.datetime.PartialDateTime;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskListTest {

    @Test
    public void addGetRemoveSizeTest() {
        TaskList list = new TaskList();
        assertEquals(0, list.size());

        list.add(new TodoTask("a"));
        list.add(new DeadlineTask("b", PartialDateTime.createDate(LocalDate.of(2020, 1, 1))));
        assertEquals(2, list.size());
        assertTrue(list.containsIndex(1));

        list.remove(0);
        assertEquals(1, list.size());
    }

    @Test
    public void saveLoadRoundTripTest() throws IOException {
        TaskList original = new TaskList();
        original.add(new TodoTask("todo"));
        original.add(new DeadlineTask("due", PartialDateTime.createDateTime(LocalDateTime.of(2020, 6, 7, 8, 9))));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        original.save(out);

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        TaskList loaded = TaskList.loadInstance(in);

        assertEquals(original.toString(), loaded.toString());
    }
}
