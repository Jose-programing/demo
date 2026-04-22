package edu.utsa.cs3443.demo;

import edu.utsa.cs3443.demo.model.TaskManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.scene.Scene;



public class CrudController {

    private TaskManager manager =  new TaskManager();
    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextArea outputArea;

    @FXML
    private Button returnButton;

    @FXML
    private Button updateButton;

    @FXML
    private ListView<Task> taskListView;

    private void refreshList() {
        taskListView.setItems(FXCollections.observableArrayList(manager.getTasks()));
    }

    @FXML
     public void initialize() throws IOException { // Starts and pops up when CRUD screen is opened
        manager.loadTasks();
        manager.sortByPriority();
        refreshList();
    }

    @FXML
    void goToAdd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-screen.fxml"));
            Parent root = loader.load();
            AddController controller = loader.getController();
            controller.setManager(manager);


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshList();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToDelete(ActionEvent event) throws IOException {
        Task selected = taskListView.getSelectionModel().getSelectedItem();
        if(selected == null) {
            showAlert("Please select a task to remove", Alert.AlertType.WARNING);
            return;
        }
        manager.deleteTask(selected);
        refreshList();
    }

    @FXML
    void goToUpdate(ActionEvent event) {
        Task selected = taskListView.getSelectionModel().getSelectedItem();
        if(selected == null) {
            showAlert("Please select a task to update", Alert.AlertType.WARNING);
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("update-screen.fxml"));
            Parent root = loader.load();
            UpdateController controller = loader.getController();
            controller.setTask(selected);
            controller.setManager(manager);


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshList();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Adding Tasks");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void goToCalendar(ActionEvent event) {
        //also victor

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CalendarView.fxml"));
            Parent root = loader.load();


            CalendarController calendarController = loader.getController();

            //convert arraylist to hashmap
            java.util.HashMap<java.time.LocalDate, java.util.ArrayList<Task>> taskMap = new java.util.HashMap<>();

            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-M-d");


            //loop through list and put into map
            for (Task t : manager.getTasks()){
                java.time.LocalDate date = java.time.LocalDate.parse(t.getDay(), formatter);
                taskMap.putIfAbsent(date, new java.util.ArrayList<>());
                taskMap.get(date).add(t);

            }

            //give map back to calendar and go back
            calendarController.loadTaskMap(taskMap);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    //Below added by victor to link with calendar

    private java.time.LocalDate currentDate;

    public void setDayToDisplay(java.time.LocalDate date){
        this.currentDate = date;
        System.out.println("Got to day from calender. Day = " + date);
    }

}
