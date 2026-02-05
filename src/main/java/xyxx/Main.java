package xyxx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import xyxx.gui.ui.MainWindow;

/**
 * Main application entry point for JavaFX applications.
 */
public class Main extends Application {
    private Xyxx xyxx = new Xyxx();

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Xyxx");

            xyxx.init();
            
            fxmlLoader.<MainWindow>getController().setXyxx(xyxx);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        xyxx.close();
    }
}
