package xyxx.datetime;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
}
