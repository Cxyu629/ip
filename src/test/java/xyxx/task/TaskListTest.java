package xyxx.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import xyxx.datetime.PartialDateTime;

public class TaskListTest {

    @Test
    public void addGetRemoveSizeTest() {
        TaskList list = new TaskList();
        assertEquals(0, list.size());

        list.add(new TodoTask("a", List.of()));
        list.add(new DeadlineTask("b", PartialDateTime.createDate(LocalDate.of(2020, 1, 1)),
                List.of()));
        assertEquals(2, list.size());
        assertTrue(list.containsIndex(1));

        list.remove(0);
        assertEquals(1, list.size());
    }

    @Test
    public void saveLoadRoundTripTest() throws IOException {
        TaskList original = new TaskList();
        original.add(new TodoTask("todo", List.of()));
        original.add(new DeadlineTask("due",
                PartialDateTime.createDateTime(LocalDateTime.of(2020, 6, 7, 8, 9)), List.of()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        original.save(out);

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        TaskList loaded = TaskList.loadInstance(in);

        assertEquals(original.toString(), loaded.toString());
    }

    @Test
    public void findBySubstringTest() {
        TaskList list = new TaskList();
        list.add(new TodoTask("Buy milk", List.of()));
        list.add(new TodoTask("Read book", List.of()));

        TaskList found = list.filterByKeyword("buy");
        assertEquals(1, found.size());
        assertTrue(found.get(0).toString().contains("Buy milk"));
    }

    @Test
    public void findCaseInsensitiveTest() {
        TaskList list = new TaskList();
        list.add(new TodoTask("Buy milk", List.of()));
        list.add(new TodoTask("buy cookies", List.of()));

        TaskList found = list.filterByKeyword("BUY");
        assertEquals(2, found.size());
    }

    @Test
    public void findWithDatetimeTaskTest() {
        TaskList list = new TaskList();
        list.add(new DeadlineTask("Submit report",
                PartialDateTime.createDateTime(LocalDateTime.of(2022, 6, 1, 12, 0)), List.of()));
        list.add(new TodoTask("Submit tax", List.of()));

        TaskList found = list.filterByKeyword("submit");
        assertEquals(2, found.size());
    }

    @Test
    public void noMatchReturnsEmptyTest() {
        TaskList list = new TaskList();
        list.add(new TodoTask("Alpha", List.of()));
        TaskList found = list.filterByKeyword("beta");
        assertEquals(0, found.size());
    }
}
