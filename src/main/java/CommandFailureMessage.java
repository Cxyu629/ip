public abstract class CommandFailureMessage {
    public static String invalidTaskNumber(String argument) {
        return String.format("Oops, \"%s\" should be a task number!", argument);
    }

    public static String taskIndexOutOfRange(int index) {
        return String.format("Hmm I can't find task %d...", index);
    }

    public static String emptyTaskDescription() {
        return "Oop, there's nothing to add.";
    }

    public static String invalidFormat(String formatGuide) {
        return String.format("I don't get it... Try formatting it like \"%s\".", formatGuide);
    }
}
