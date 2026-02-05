package xyxx.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class PartialDateTimeTest {

    @Test
    public void parseDateOnlyTest() {
        PartialDateTime p = PartialDateTime.fromString("2020-01-02");
        assertNotNull(p);
        assertEquals("Jan 02, 2020", p.toString());
    }

    @Test
    public void parseDateTimeTest() {
        PartialDateTime p = PartialDateTime.fromString("2020-01-02 1530");
        assertNotNull(p);
        assertEquals("Jan 02, 2020 15:30", p.toString());
    }

    @Test
    public void parseInvalidTest() {
        PartialDateTime p = PartialDateTime.fromString("not-a-date");
        assertNull(p);
    }

    @Test
    public void saveLoadRoundTripTest() throws IOException {
        PartialDateTime original = PartialDateTime.createDateTime(LocalDateTime.of(2021, 2, 3, 4, 5, 6));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        original.save(out);

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        PartialDateTime loaded = PartialDateTime.loadInstance(in);

        assertEquals(original.toString(), loaded.toString());
    }

    @Test
    public void createDateTest() {
        LocalDate date = LocalDate.of(2023, 5, 15);
        PartialDateTime p = PartialDateTime.createDate(date);
        assertNotNull(p);
        assertEquals("May 15, 2023", p.toString());
    }

    @Test
    public void createDateLeapYearTest() {
        LocalDate date = LocalDate.of(2020, 2, 29);
        PartialDateTime p = PartialDateTime.createDate(date);
        assertNotNull(p);
        assertEquals("Feb 29, 2020", p.toString());
    }

    @Test
    public void createDateFirstDayOfYearTest() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        PartialDateTime p = PartialDateTime.createDate(date);
        assertNotNull(p);
        assertEquals("Jan 01, 2025", p.toString());
    }
}
