package xyxx;

/**
 * Helper class that centralizes user-facing error messages for command
 * failures.
 */
public abstract class CommandFailureMessage {
    /**
     * Returns a message for when a non-numeric argument is given where a task
     * number is expected.
     */
    public static String invalidTaskNumber(String argument) {
        return String.format("Oops, \"%s\" should be a task number!", argument);
    }

    /**
     * Returns a message used when a task index does not correspond to an
     * existing task.
     */
    public static String taskIndexOutOfRange(int index) {
        return String.format("Hmm I can't find task %d...", index);
    }

    /**
     * Returns a message used when the user attempts to add a task without a
     * description.
     */
    public static String emptyTaskDescription() {
        return "Oop, there's nothing to add.";
    }

    /**
     * Returns a message describing an expected input format.
     */
    public static String invalidFormat(String formatGuide) {
        return String.format("I don't get it... Try formatting it like \"%s\".", formatGuide);
    }
}
