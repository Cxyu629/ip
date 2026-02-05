package xyxx;

/**
 * Helper class that centralizes user-facing error messages for command
 * failures.
 */
public final class CommandFailureMessage {
    /**
     * Returns a message for when a non-numeric argument is given where a task
     * number is expected.
     * 
     * @param argument The invalid argument.
     * @return The message indicating the argument is not a valid task number.
     */
    public static String invalidTaskNumber(String argument) {
        return String.format("Oops, \"%s\" should be a task number!", argument);
    }

    /**
     * Returns a message used when a task index does not correspond to an
     * existing task.
     * 
     * @param index The invalid task index.
     * @return The message indicating the task index is out of range.
     */
    public static String taskIndexOutOfRange(int index) {
        return String.format("Hmm I can't find task %d...", index);
    }

    /**
     * Returns a message used when the user attempts to add a task without a
     * description.
     * 
     * @return The message indicating an empty task description.
     */
    public static String emptyTaskDescription() {
        return "Oop, there's nothing to add.";
    }

    /**
     * Returns a message describing an expected input format.
     * 
     * @param formatGuide A guide string showing the expected format.
     * @return The message indicating the correct input format.
     */
    public static String invalidFormat(String formatGuide) {
        return String.format("I don't get it... Try formatting it like \"%s\".", formatGuide);
    }

    public static String parseError(String details) {
        return String.format("Oops, I couldn't parse that! %s", details);
    }
}
