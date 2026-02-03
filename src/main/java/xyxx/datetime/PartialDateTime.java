package xyxx.datetime;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class PartialDateTime {
    private LocalDateTime dateTime;
    private Precision precision;

    public static final String FORMAT_HINT = "yyyy-mm-dd [HHmm]";

    private enum Precision {
        DATE_ONLY, DATE_TIME,
    }

    private PartialDateTime(LocalDateTime dateTime, Precision precision) {
        this.dateTime = dateTime;
        this.precision = precision;
    }

    public static PartialDateTime createDate(LocalDate date) {
        return new PartialDateTime(date.atStartOfDay(), Precision.DATE_ONLY);
    }

    public static PartialDateTime createDateTime(LocalDateTime dateTime) {
        return new PartialDateTime(dateTime, Precision.DATE_TIME);
    }

    /**
     * Parses and converts a datetime string in the format specified in FORMAT_HINT to
     * {@link PartialDateTime}.
     *
     * @param dateTime
     * @return {@link PartialDateTime} if parsing succeeds, otherwise {@code null}.
     */
    public static PartialDateTime fromString(String dateTime) {
        DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd")
                .optionalStart().appendPattern(" HHmm").optionalEnd().toFormatter();

        try {
            TemporalAccessor ta = fmt.parse(dateTime);

            if (ta.isSupported(ChronoField.MINUTE_OF_DAY)) {
                return createDateTime(LocalDateTime.from(ta));
            } else {
                return createDate(LocalDate.from(ta));
            }
        } catch (DateTimeParseException e) {
            return null;
        }

    }

    public void save(DataOutputStream out) throws IOException {
        out.writeByte(precision.ordinal());
        out.writeLong(dateTime.toEpochSecond(ZoneOffset.UTC));
        out.writeInt(dateTime.getNano());
    }

    public static PartialDateTime loadInstance(DataInputStream in) throws IOException {
        PartialDateTime pdt = new PartialDateTime(null, null);
        pdt.precision = Precision.values()[in.readByte()];
        pdt.dateTime = LocalDateTime.ofEpochSecond(in.readLong(), in.readInt(), ZoneOffset.UTC);
        return pdt;
    }

    @Override
    public String toString() {
        return switch (precision) {
        case DATE_ONLY -> dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        case DATE_TIME -> dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));
        };
    }
}
