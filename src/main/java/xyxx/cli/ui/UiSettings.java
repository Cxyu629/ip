package xyxx.cli.ui;

/**
 * Settings that control how {@link CliUi} formats messages.
 *
 * @param messageWidth the width of the top/bottom border
 * @param indent       number of leading spaces for printed messages
 */
public record UiSettings(int messageWidth, int indent) {
    /**
     * Builder for {@link UiSettings} to allow fluent configuration.
     */
    public static class Builder {
        private int messageWidth = 70;
        private int indent = 10;

        private Builder() {}

        /**
         * Sets the width used for border lines.
         * 
         * @param messageWidth the message width
         * @return the builder instance
         */
        public Builder setMessageWidth(int messageWidth) {
            this.messageWidth = messageWidth;
            return this;
        }

        /**
         * Sets the indent (number of spaces before each message line).
         * 
         * @param indent the indent size
         * @return the builder instance
         */
        public Builder setIndent(int indent) {
            this.indent = indent;
            return this;
        }

        /**
         * Builds the {@link UiSettings} instance.
         * 
         * @return the UiSettings instance
         */
        public UiSettings build() {
            return new UiSettings(this.messageWidth, this.indent);
        }
    }

    /**
     * Creates a new {@link Builder} for {@link UiSettings}.
     * 
     * @return the builder instance
     */
    public static Builder builder() {
        return new Builder();
    }
}
