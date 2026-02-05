package xyxx.ui;

/**
 * Defines the interface for user interface adapters that handle input and output operations.
 * Implementations of this interface can provide different UI mechanisms such as command-line,
 * graphical, or web-based interfaces.
 */
public interface UiAdapter {
    /**
     * Sends a message to the user.
     *
     * @param message the message to display
     */
    public void sendMessage(String message);

    /**
     * Sends a greeting message to the user.
     */
    public void sendGreetMessage();

    /**
     * Sends an exit message to the user.
     */
    public void sendExitMessage();

    /**
     * Receives input from the user.
     *
     * @return the input string entered by the user
     */
    public String receiveInput();
}
