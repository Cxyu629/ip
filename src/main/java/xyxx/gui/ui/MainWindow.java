package xyxx.gui.ui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import xyxx.Result;
import xyxx.Xyxx;

/**
 * Controller for the main window of the GUI application. Handles user input and displays dialog
 * boxes for user and Xyxx responses.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private Xyxx xyxx;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image xyxxImage = new Image(this.getClass().getResourceAsStream("/images/DaDude.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setXyxx(Xyxx xyxx) {
        this.xyxx = xyxx;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        Result result = xyxx.handleInput(input);
        String response = result.continueRunning() ? result.message() : "Goodbye!";
        dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userImage),
                DialogBox.getXyxxDialog(response, xyxxImage));
        userInput.clear();

        if (!result.continueRunning()) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(e -> Platform.exit());
            delay.play();
        }
    }
}
