package edu.utsa.cs3443.demo;

import edu.utsa.cs3443.demo.model.TaskManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AddController {
    private TaskManager manager;
    public void setManager(TaskManager manager) {
        this.manager = manager;
    }

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

    @FXML
    private TextField priorityField;

    @FXML
    void FinalAddButton(ActionEvent event) throws IOException {
        String title = titleField.getText();
        String time = timeField.getText();
        String type = typeField.getText();
        //check if fields are empty
        if(title.isEmpty() || time.isEmpty() || type.isEmpty() || priorityField.getText().isEmpty()) {
            showAlert("Please fill in all fields!", Alert.AlertType.WARNING);
            return;
            //if any of the fields are empty
        }
        //Checks if its a number or not
        int priority = 0;
        try {
            priority = Integer.parseInt(priorityField.getText());
        } catch (NumberFormatException e) {
            showAlert("Priority must be a number", Alert.AlertType.ERROR);
            return;
        }
        //checks if item exists
        if(manager.taskExists(title, time)){
            showAlert("This item already exists",Alert.AlertType.ERROR);
            return;
        }
        //cheks if priority is in use and again this is temporary
        if(manager.priorityExists(priority)) { // this is only temporary and its not user friendly
            showAlert("Priority is in use", Alert.AlertType.WARNING);
            return;
        }
        //Goes through and creates the task.
        Task newTask = new Task("2026-4-20", title, type, priority, time);
        manager.addTask(newTask);
        manager.SaveDataToFile();
        showAlertAfterAdd();
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
        priorityField.clear();
    }

}




