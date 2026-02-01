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
}
