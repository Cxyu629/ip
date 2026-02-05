package xyxx;

/**
 * Represents the result of handling an input command.
 *
 * @param continueRunning Indicates whether the application should continue running.
 * @param message The message to be displayed to the user.
 * @param isError Indicates whether the result represents an error.
 */
public record Result(boolean continueRunning, String message, boolean isError) {
}
