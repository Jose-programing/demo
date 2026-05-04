package edu.utsa.cs3443.demo;

import edu.utsa.cs3443.demo.model.DataFileStore;
import edu.utsa.cs3443.demo.model.DataStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class AddController {

    /// data gets passed from crud controller to here

    LocalDate currentDate;

    @FXML
    private Button returnButton;

    @FXML
    private Button addTask;

    @FXML
    private TextField timeField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField typeField;


    public void setDate(LocalDate date) {
        this.currentDate = date;
    }

    @FXML
    void FinalAddButton(ActionEvent event) throws IOException {
        String title = titleField.getText();
        String time = timeField.getText();
        String type = typeField.getText();

        if (title.isEmpty() || time.isEmpty() || type.isEmpty()) {
            showAlert("Please fill in all fields!", Alert.AlertType.WARNING);
            return;
        }

        Task newTask = new Task(currentDate, title, type, time);
        for (Task t : DataStore.taskMap.getOrDefault(currentDate, new ArrayList<>())) {
            if(newTask.getTime().equals(t.getTime()) && newTask.getTaskTitle().equals(t.getTaskTitle()) && newTask.getTaskType().equals(t.getTaskType())) {
                showAlert("This task already exists", Alert.AlertType.WARNING);
                return;
            }

        }

        //adds to the map
        DataStore.taskMap.putIfAbsent(currentDate, new ArrayList<>());
        DataStore.taskMap.get(currentDate).add(newTask);

        showAlertAfterAdd();
        DataFileStore.save();
    }

    @FXML
    void goToCRUD(ActionEvent event) { //closes the screen
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //pop up alert
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Adding Tasks");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //popup for yes or no to leave or stay and make another task
    private void showAlertAfterAdd() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Task added successfully. What would you like to do next?");

        ButtonType addAnother = new ButtonType("Add Another");
        ButtonType returnBtn = new ButtonType("Return");

        alert.getButtonTypes().setAll(addAnother, returnBtn);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == addAnother) {
            clearFields(); // stay on same screen
        } else {
            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.close(); // return to CRUD
        }
    }

    public void clearFields() {
        titleField.clear();
        typeField.clear();
        timeField.clear();
    }


}




