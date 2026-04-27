package edu.utsa.cs3443.demo;

import edu.utsa.cs3443.demo.model.DataStore;
import edu.utsa.cs3443.demo.model.QuoteModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;
import java.time.LocalDate;
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
    //This is for today's date implemented by jose
    LocalDate currentDate = LocalDate.now();
    private void loadTodaysTasks() {
        ArrayList<Task> tasks = DataStore.taskMap.getOrDefault(currentDate, new ArrayList<>());

        taskListBox.getChildren().clear(); // clear old tasks

        for (Task task : tasks) {
            Label taskLabel = new Label(task.toString());
            taskListBox.getChildren().add(taskLabel);
        }
    }

    // ---------------------------------------------------------
    // NAVIGATION BUTTON HANDLERS
    // ---------------------------------------------------------

    @FXML
    private void onCalendar(ActionEvent event) {
        loadScreen(event, "CalendarView.fxml");
    }

    @FXML
    private void onCrud(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("crud-screen.fxml"));
        Parent root = loader.load();

        CrudController controller = loader.getController();

        // pass the date
        controller.setDayToDisplay(currentDate);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void onStudySession(ActionEvent event) {
        loadScreen(event, "studySession-Screen.fxml");
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
