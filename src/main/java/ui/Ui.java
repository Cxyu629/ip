package ui;

public class Ui {
    private UiSettings settings;

    public Ui(UiSettings settings) {
        this.settings = settings;
    }

    public Ui() {
        this(UiSettings.builder().build());
    }

    public void sendMessage(String message) {
        String border = "-".repeat(settings.messageWidth());
        System.out.println(border.indent(settings.indent()));
        System.out.println(message.indent(settings.indent()));
        System.out.println(border.indent(settings.indent()));
    }
}
