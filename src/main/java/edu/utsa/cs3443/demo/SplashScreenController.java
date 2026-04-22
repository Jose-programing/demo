package edu.utsa.cs3443.demo;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SplashScreenController {

    @FXML
    private Button enterButton;

    /**
     * Handles the ENTER button click.
     * Fades out the splash screen, then loads MainMenuScreen.fxml.
     */
    @FXML
    void onContinue(ActionEvent event) {

        // Fade out the splash screen
        Node root = ((Node) event.getSource()).getScene().getRoot();

        FadeTransition fade = new FadeTransition(Duration.millis(300), root);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> loadMainMenu(event));
        fade.play();
    }

    /**
     * Loads the main menu screen after fade-out.
     */
    private void loadMainMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/edu/utsa/cs3443/demo/MainMenuScreen.fxml")
            );

            Scene mainScene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(mainScene);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Replace with an Alert dialog later
        }
    }
}
