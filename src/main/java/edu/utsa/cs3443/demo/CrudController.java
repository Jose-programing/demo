package edu.utsa.cs3443.demo;


import edu.utsa.cs3443.demo.model.DataFileStore;
import edu.utsa.cs3443.demo.model.DataStore;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class CrudController {

    //hashmap and currentdate passed by calander controller
    private LocalDate currentDate;

    @FXML
    private Button moveUpButton;

    @FXML Button moveDownButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField outputArea;

    @FXML
    private Button returnButton;

    @FXML
    private Button updateButton;

    @FXML
    private ListView<Task> taskListView;


    private void refreshList() {
        if (DataStore.taskMap == null || currentDate == null) return;

        ArrayList<Task> tasks =
                DataStore.taskMap.getOrDefault(currentDate, new ArrayList<>());

        taskListView.setItems(FXCollections.observableArrayList(tasks));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");//change it into this format for the date
        outputArea.setText(currentDate.format(formatter));
    }

    public void setDayToDisplay(LocalDate date) {
        this.currentDate = date;
        refreshList();
    }

    @FXML
    void moveUp() {
        int index = taskListView.getSelectionModel().getSelectedIndex();

        if (index > 0) {
            ArrayList<Task> tasks = DataStore.taskMap.get(currentDate);

            Task temp = tasks.get(index);
            tasks.set(index, tasks.get(index - 1));
            tasks.set(index - 1, temp);

            try {
                DataFileStore.save();
            } catch (IOException e) {
                e.printStackTrace();
            }

            refreshList();
            taskListView.getSelectionModel().select(index - 1);
        }
    }

    @FXML
    void moveDown() {
        int index = taskListView.getSelectionModel().getSelectedIndex();

        ArrayList<Task> tasks = DataStore.taskMap.get(currentDate);
        if(index < 0) {
            return;
        }


        if (index < tasks.size() - 1) {
            Task temp = tasks.get(index);
            tasks.set(index, tasks.get(index + 1));
            tasks.set(index + 1, temp);

            try {
                DataFileStore.save();
            } catch (IOException e) {
                e.printStackTrace();
            }

            refreshList();
            taskListView.getSelectionModel().select(index + 1);
        }
    }

    @FXML
     public void initialize() throws IOException { // Starts and pops up when CRUD screen is opened
        taskListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Image upImage = new Image(getClass().getResourceAsStream("/images/Up.png"));
        Image downImage = new Image(getClass().getResourceAsStream("/images/down.png"));

        ImageView upView = new ImageView(upImage);
        upView.setFitWidth(25);
        upView.setFitHeight(25);

        ImageView downView = new ImageView(downImage);
        downView.setFitWidth(25);
        downView.setFitHeight(25);

        moveUpButton.setGraphic(upView);
        moveDownButton.setGraphic(downView);
        taskListView.setCellFactory(listView -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null) {
                    setGraphic(null);
                    return;
                }

                // Dot
                Label dot = new Label("•  ");
                dot.setStyle("-fx-font-size: 36px; -fx-text-fill: #000000;");

                //  Title (bold)
                Label title = new Label(task.getTaskTitle());
                title.setStyle("-fx-font-weight: bold; -fx-font-size: 36px;");

                // Task Type (next to title)
                Label type = new Label("(" + task.getTaskType() + ")");
                type.setStyle("-fx-text-fill: #888; -fx-font-size: 36px;");

                HBox titleRow = new HBox(title, type);
                titleRow.setSpacing(6);

                // Time
                Label time = new Label(task.getTime());
                time.setStyle("-fx-text-fill: gray; -fx-font-size: 24px;");

                VBox textBox = new VBox(titleRow, time);
                textBox.setSpacing(2);

                HBox row = new HBox(dot, textBox);
                row.setSpacing(8);

                setGraphic(row);
            }

        });
    }

    @FXML
    void goToAdd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-screen.fxml"));
            Parent root = loader.load();
            AddController controller = loader.getController();

            controller.setDate(currentDate);



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

        if(selected == null && taskListView.getItems().isEmpty()) {
            showAlert("No Tasks exist at this moment", Alert.AlertType.WARNING);
            return;
        }
        if (selected == null) {
            showAlert("Please select a task to remove", Alert.AlertType.WARNING);
            return;
        }

        ArrayList<Task> tasks = DataStore.taskMap.get(currentDate);

        if (tasks != null) {
            tasks.remove(selected);
            DataFileStore.save();
        }


        refreshList();
    }

    @FXML
    void goToUpdate(ActionEvent event) {
        Task selected = taskListView.getSelectionModel().getSelectedItem();

        if(selected == null && taskListView.getItems().isEmpty()) {
            showAlert("No Tasks at this moment", Alert.AlertType.WARNING);
            return;
        }

        if (selected == null) {
            showAlert("Please select a task to update", Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("update-screen.fxml"));
            Parent root = loader.load();

            UpdateController controller = loader.getController();
            controller.setTask(selected);

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CalendarView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
