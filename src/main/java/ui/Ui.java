package ui;

import java.util.Scanner;

public class Ui {
    private UiSettings settings;
    private Scanner scanner = new Scanner(System.in);

    public Ui(UiSettings settings) {
        this.settings = settings;
    }

    public Ui() {
        this(UiSettings.builder().build());
    }

    public void printMessage(String message) {
        String border = "_".repeat(settings.messageWidth());
        System.out.println(border.indent(settings.indent()));
        System.out.print(message.indent(settings.indent()));
        System.out.println(border.indent(settings.indent()));
    }

    public String getInput() {
        System.out.print("> ");
        return scanner.nextLine();
    }

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

    public void printExitMessage() {
        String message = "See you soon, bye!";
        printMessage(message);
    }
}
