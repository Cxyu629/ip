package ui;

public record UiSettings(int messageWidth, int indent) {
    static class Builder {
        private int messageWidth = 80;
        private int indent = 4;

        private Builder() {
        }

        public Builder setMessageWidth(int messageWidth) {
            this.messageWidth = messageWidth;
            return this;
        }

        public Builder setIndent(int indent) {
            this.indent = indent;
            return this;
        }

        public UiSettings build() {
            return new UiSettings(this.messageWidth, this.indent);
        }
    }
    public static Builder builder() {
        return new Builder();
    }

    public UiSettings(int messageWidth, int indent) {
        this.messageWidth = messageWidth;
        this.indent = indent;
    }
}
