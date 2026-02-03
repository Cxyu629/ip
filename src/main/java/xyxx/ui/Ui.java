package xyxx.ui;

import java.util.Scanner;

/**
 * Responsible for user-facing input/output. The class handles printing
 * messages with formatting and reading a single line of user input.
 */
public class Ui {
    private UiSettings settings;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Creates a UI with the provided settings.
     *
     * @param settings formatting settings
     */
    public Ui(UiSettings settings) {
        this.settings = settings;
    }

    /**
     * Creates a UI with default settings.
     */
    public Ui() {
        this(UiSettings.builder().build());
    }

    /**
     * Prints a message with formatting.
     *
     * @param message the message to print
     */
    public void printMessage(String message) {
        String border = "_".repeat(settings.messageWidth());
        System.out.println(border.indent(settings.indent()));
        System.out.print(message.indent(settings.indent()));
        System.out.println(border.indent(settings.indent()));
    }

    /**
     * Prompts and reads a single line from standard input.
     *
     * @return the line entered by the user (without a trailing newline)
     */
    public String getInput() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    /**
     * Prints the application greeting (logo + welcome text).
     */
    public void printGreetMessage() {
        String logo = """
                 \\o       o/
                  v\\     /v
                   <\\   />
                     \\o/    o      o   \\o    o/  \\o    o/
                      |    <|>    <|>   v\\  /v    v\\  /v
                     / \\   < >    < >    <\\/>      <\\/>
                   o/   \\o  \\o    o/     o/\\o      o/\\o
                  /v     v\\  v\\  /v     /v  v\\    /v  v\\
                 />       <\\  <\\/>     />    <\\  />    <\\
                               /
                              o
                           __/>
                """;

        String message = "Hello from \n \n" + logo + "\nHow may I help you today?";
        printMessage(message);
    }

    /**
     * Prints the application's exit message.
     */
    public void printExitMessage() {
        String message = "See you soon, bye!";
        printMessage(message);
    }
}
