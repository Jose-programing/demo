package edu.utsa.cs3443.demo;

import edu.utsa.cs3443.demo.model.CalendarModel;
import edu.utsa.cs3443.demo.model.Task;
import edu.utsa.cs3443.demo.model.TaskManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {
    private TaskManager manager;
    public void setTaskManager(TaskManager manager) {
        this.manager = manager;

        // Also give data to model
        model.loadTaskMap(manager.getTaskMap());
        refreshGrid();
    }



    //@FXML private ComboBox<Month> monthCombo;
    @FXML private ComboBox<String> monthCombo;
    @FXML private ComboBox<Integer> yearCombo;
    @FXML private GridPane dayGrid;
    @FXML private Label selectedDateLabel;



    private final CalendarModel model = new CalendarModel();





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupMonthCombo();
        setupYearCombo();
        refreshGrid();
    }



    private void setupMonthCombo(){

        String[] months = new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
        monthCombo.getItems().addAll(months);

        int currentMonth = model.getCurrentYearMonth().getMonthValue() - 1; //cause arrays
        monthCombo.setValue(months[currentMonth]);
    }





    private void setupYearCombo() {
        int currentYear = model.getCurrentYearMonth().getYear();
        for (int y = currentYear - 5; y <= currentYear + 5; y++) {
            yearCombo.getItems().add(y);
        }
        yearCombo.setValue(currentYear);
    }





    //buttons handling


    @FXML
    private void onDropdownChange(){

        String selectedMonth = monthCombo.getValue();
        Integer selectedYear = yearCombo.getValue();


        if (selectedMonth != null && selectedYear != null){

            int index = monthCombo.getItems().indexOf(selectedMonth);
            int monthNumber = index + 1; //cause array // again
            Month month = Month.of(monthNumber);

            model.setCurrentYearMonth(YearMonth.of(selectedYear, month));
            refreshGrid();
        }
    }




    @FXML
    private void onViewDayClicked() {


        if (model.getSelectedDate() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a date first", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("crud-screen.fxml"));
            Parent root = loader.load();

            // Get controller of CRUD screen
            CrudController controller = loader.getController();

            // Pass data
            controller.setDate(model.getSelectedDate());
            controller.setTaskManager(manager);

            Stage stage = (Stage) dayGrid.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    private void refreshGrid() {

        // Remove all day-number cells (keep row 0 headers)
        dayGrid.getChildren().removeIf(node -> {
            Integer row = GridPane.getRowIndex(node);
            return row != null && row > 0;
        });

        YearMonth ym = model.getCurrentYearMonth();
        int daysInMonth = model.getDaysInMonth();
        int startOffset = model.getStartOffset(); // 0=Mon 6=Sun

        int row = 1;
        int col = startOffset;

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = ym.atDay(day);
            Button cell = buildDayCell(day, date);
            dayGrid.add(cell, col, row);

            col++;
            if (col > 6) { col = 0; row++; }
        }

    }

    private Button buildDayCell(int day, LocalDate date) {
        int taskCount = model.getTaskCount(date);

        String label = String.valueOf(day);
        if (taskCount > 0) {
            label += "\n● " + taskCount;
        }

        Button cell = new Button(label);
        cell.setMaxWidth(Double.MAX_VALUE);
        cell.setMaxHeight(Double.MAX_VALUE);
        cell.setWrapText(true);
        cell.setAlignment(Pos.TOP_CENTER);

        if (model.isSelected(date)) {
            cell.setStyle("-fx-background-color: #4A90D9; -fx-text-fill: white; -fx-font-weight: bold;");
        }
        else if (model.isToday(date)) {
            cell.setStyle("-fx-background-color: #E8F4FD; -fx-border-color: #4A90D9; -fx-border-width: 1.5;");
        }
        else {
            cell.setStyle("-fx-background-color: #f5f5f5;");
        }

        cell.setOnAction(e -> {
            model.setSelectedDate(date);
            selectedDateLabel.setText("Selected: " + date);
            refreshGrid();
        });

        return cell;

    }


    /**
     * On click handle for the back button
     * Return to main menu
     */
    @FXML
    private void onBackClicked() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) monthCombo.getScene().getWindow();

            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //call after building hash map
    public void loadTaskMap(HashMap<LocalDate, ArrayList<Task>> taskMap) {
        model.loadTaskMap(taskMap);
        refreshGrid();
    }

    /**
     * call to see which date got selected
     */
    public LocalDate getSelectedDate() {
        return model.getSelectedDate();
    }
}
