package edu.utsa.cs3443.demo;


import edu.utsa.cs3443.demo.model.Task;
import edu.utsa.cs3443.demo.model.TaskManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class UpdateController {

    private TaskManager manager;

    public void setManager(TaskManager manager) {
        this.manager = manager;
    }

    @FXML
    private Button returnButton;

    @FXML
    private Button UpdateTask;

    @FXML
    private TextField timeField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField priorityField;

    private Task task;

    public void setTask(Task task) {
        this.task = task;

        titleField.setText(task.getTaskTitle());
        typeField.setText(task.getTaskType());
        timeField.setText(task.getTime());
        priorityField.setText(String.valueOf(task.getPriority()));

    }

    @FXML
    void FinalUpdateButton(ActionEvent event) throws IOException {//It works like the add rn so i need to modify it
        String title = titleField.getText().trim();
        String time = timeField.getText().trim();
        String type = typeField.getText().trim();
        if(title.isEmpty() || time.isEmpty() || type.isEmpty() || priorityField.getText().isEmpty()) {
            showAlert("Please fill in all fields!", Alert.AlertType.WARNING);
            return;
        }
        int priority = 0;
        try {
            priority = Integer.parseInt(priorityField.getText());
        } catch (NumberFormatException e) {
            showAlert("Priority must be a number", Alert.AlertType.ERROR);
            return;
        }

        if (!task.getTaskTitle().equals(title) || !task.getTime().equals(time)) {
            if (manager.taskExists(title, time)) {
                showAlert("This item already exists", Alert.AlertType.ERROR);
                return;
            }
        }
        if (manager.priorityExists(priority) && task.getPriority() != priority) {
            showAlert("Priority is in use", Alert.AlertType.WARNING);
            return;
        }
        task.setTaskType(type);
        task.setTaskTitle(title);
        task.setTime(time);
        task.setPriority(priority);
        manager.SaveDataToFile();
        showAlert("Item updated Succesfully", Alert.AlertType.INFORMATION);
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Adding Tasks");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void goToCRUD(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setDate(LocalDate selectedDate) {
    }
}
