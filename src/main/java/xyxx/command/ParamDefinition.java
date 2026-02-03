package xyxx.command;

/**
 * Represents the definition of a parameter for a command.
 *
 * @param name       the parameter name
 * @param isRequired whether the parameter must be provided
 * @param type       the expected type of the parameter value
 */
public record ParamDefinition(String name, boolean isRequired, Type type) {
    /**
     * Supported parameter types.
     */
    public enum Type {
        /** Any string value. */
        STRING,
        /** A numeric value parseable to {@link Double}. */
        NUMBER,
        /**
         * A date or date-time in {@link xyxx.datetime.PartialDateTime#FORMAT_HINT}
         * format.
         */
        PARTIALDATETIME,
    }
}
