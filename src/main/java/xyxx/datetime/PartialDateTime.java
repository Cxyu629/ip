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

/**
 * Represents a date or date-time with optional time precision, allowing for partial specification
 * (date only or date and time).
 * <p>
 * This class is useful for scenarios where either a full date-time or just a date is needed, and
 * provides parsing, formatting, and serialization utilities. The format hint for parsing is
 * {@link #FORMAT_HINT}.
 */
public class PartialDateTime {
    /**
     * The precision of the stored value: either only a date, or a full date-time.
     */
    private enum Precision {
        /** Only the date part is meaningful. */
        DATE_ONLY,
        /** Both date and time are meaningful. */
        DATE_TIME,
    }

    /**
     * The expected input format for parsing: {@code yyyy-mm-dd} or {@code yyyy-mm-dd HHmm}.
     * <ul>
     * <li>For date only: {@code 2020-01-02}</li>
     * <li>For date and time: {@code 2020-01-02 1530}</li>
     * </ul>
     */
    public static final String FORMAT_HINT = "yyyy-mm-dd [HHmm]";

    /**
     * The underlying date and time value. If {@link #precision} is DATE_ONLY, the time is 00:00.
     */
    private LocalDateTime dateTime;

    /**
     * The precision of this instance (date only or date-time).
     */
    private Precision precision;

    /**
     * Constructs a PartialDateTime with the given value and precision.
     *
     * @param dateTime the date and time value
     * @param precision the precision (date only or date-time)
     */
    private PartialDateTime(LocalDateTime dateTime, Precision precision) {
        this.dateTime = dateTime;
        this.precision = precision;
    }

    /**
     * Creates a {@link PartialDateTime} instance with date precision only (no time component).
     *
     * @param date the date to use
     * @return a PartialDateTime with only the date part meaningful
     */
    public static PartialDateTime createDate(LocalDate date) {
        return new PartialDateTime(date.atStartOfDay(), Precision.DATE_ONLY);
    }

    /**
     * Creates a {@link PartialDateTime} instance with both date and time precision.
     *
     * @param dateTime the date and time to use
     * @return a PartialDateTime with both date and time meaningful
     */
    public static PartialDateTime createDateTime(LocalDateTime dateTime) {
        return new PartialDateTime(dateTime, Precision.DATE_TIME);
    }

    /**
     * Parses and converts a datetime string in the format specified in FORMAT_HINT to
     * {@link PartialDateTime}. Parses and converts a datetime string in the format specified in
     * {@link #FORMAT_HINT} to a {@link PartialDateTime}.
     *
     * @param dateTime the string to parse (e.g., "2020-01-02" or "2020-01-02 1530")
     * @return a {@link PartialDateTime} if parsing succeeds, otherwise {@code null}
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

    /**
     * Loads a {@link PartialDateTime} from the input stream.
     *
     * @param in the input stream to read from
     * @return the loaded PartialDateTime
     * @throws IOException if an I/O error occurs
     */
    public static PartialDateTime loadInstance(DataInputStream in) throws IOException {
        PartialDateTime pdt = new PartialDateTime(null, null);
        pdt.precision = Precision.values()[in.readByte()];
        pdt.dateTime = LocalDateTime.ofEpochSecond(in.readLong(), in.readInt(), ZoneOffset.UTC);
        return pdt;
    }

    /**
     * Serializes this {@link PartialDateTime} to the output stream.
     *
     * @param out the output stream to write to
     * @throws IOException if an I/O error occurs
     */
    public void save(DataOutputStream out) throws IOException {
        out.writeByte(precision.ordinal());
        out.writeLong(dateTime.toEpochSecond(ZoneOffset.UTC));
        out.writeInt(dateTime.getNano());
    }

    /**
     * Returns a human-readable string representation of this date or date-time.
     * <ul>
     * <li>If date only: formatted as "MMM dd, yyyy" (e.g., "Jan 02, 2020")</li>
     * <li>If date and time: formatted as "MMM dd, yyyy HH:mm" (e.g., "Jan 02, 2020 15:30")</li>
     * </ul>
     *
     * @return the formatted string
     */
    @Override
    public String toString() {
        return switch (precision) {
        case DATE_ONLY -> dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        case DATE_TIME -> dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));
        default -> {
            assert false : "Unhandled Precision: " + precision;
            throw new UnsupportedOperationException("Unsupported precision: " + precision);
        }
        };
    }
}
