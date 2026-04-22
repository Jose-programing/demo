package edu.utsa.cs3443.demo;

import edu.utsa.cs3443.demo.model.QuoteModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for the Horizon Planner Main Menu.
 * Handles navigation, quotes, and today's tasks.
 */
public class MainMenuController {

    // UI elements from MainMenuScreen.fxml
    @FXML private VBox taskListBox;
    @FXML private Label quoteLabel;
    @FXML private Label studySessionLabel;
    @FXML private Label priorityLabel;

    @FXML private Button calendarButton;
    @FXML private Button crudButton;
    @FXML private Button studyButton;
    @FXML private Button backButton;
    @FXML private Button quoteButton;

    private final QuoteModel quoteModel = new QuoteModel();

    /**
     * Initializes the screen by loading today's tasks and default labels.
     */
    @FXML
    private void initialize() {
        loadTodaysTasks();
        studySessionLabel.setText("Study for Midterms");
        priorityLabel.setText("High Priority");
    }

    /**
     * Loads tasks due today into the VBox.
     * Replace this with your real task model later.
     */
    private void loadTodaysTasks() {
        taskListBox.getChildren().clear();

        ArrayList<String> tasks = new ArrayList<>();
        tasks.add("Calculus Homework — 11:59 PM");
        tasks.add("Algorithms Homework — 11:59 PM");
        tasks.add("Review Notes for Midterms");

        for (String t : tasks) {
            Label taskLabel = new Label(t);
            taskLabel.setStyle("-fx-font-size: 16px;");
            taskListBox.getChildren().add(taskLabel);
        }
    }

    // ---------------------------------------------------------
    // NAVIGATION BUTTON HANDLERS
    // ---------------------------------------------------------

    @FXML
    private void onCalendar(ActionEvent event) {
        loadScreen(event, "CalendarScreen.fxml");
    }

    @FXML
    private void onCrud(ActionEvent event) {
        loadScreen(event, "CrudScreen.fxml");
    }

    @FXML
    private void onStudySession(ActionEvent event) {
        loadScreen(event, "StudySessionScreen.fxml");
    }

    @FXML
    private void onBack(ActionEvent event) {
        loadScreen(event, "SplashScreen.fxml");
    }

    // ---------------------------------------------------------
    // QUOTE GENERATOR
    // ---------------------------------------------------------

    @FXML
    private void onGenerateQuote() {
        quoteLabel.setText(quoteModel.getRandomQuote());
    }

    // ---------------------------------------------------------
    // SCREEN LOADING HELPER
    // ---------------------------------------------------------

    private void loadScreen(ActionEvent event, String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/edu/utsa/cs3443/demo/" + fxmlName)
            );

            Scene newScene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(newScene);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Replace with an Alert dialog later
        }
    }
}
