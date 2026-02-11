package xyxx.cli;

import xyxx.Result;
import xyxx.Xyxx;
import xyxx.cli.ui.CliUi;

/**
 * Command Line Interface application entry point.
 */
public class CliApp {

    private CliUi ui;
    private Xyxx xyxx;

    /**
     * Constructs a CliApp instance, initializing the UI and Xyxx components.
     */
    public CliApp() {
        ui = new CliUi();
        xyxx = new Xyxx();
        xyxx.init();
    }

    /**
     * Main method to start the CLI application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new CliApp().run();
    }

    private void run() {
        boolean running = true;

        ui.sendGreetMessage();
        while (running) {
            String input = ui.receiveInput();
            Result result = xyxx.handleInput(input);
            if (!result.message().isEmpty()) {
                ui.sendMessage(result.message());
            }
            running = result.continueRunning();
        }

        ui.sendExitMessage();
        xyxx.close();
    }
}
