package xyxx.command;

public record ParamDefinition(String name, boolean isRequired, Type type) {
    public enum Type {
        STRING,
        NUMBER,
        PARTIALDATETIME,
    }
}
