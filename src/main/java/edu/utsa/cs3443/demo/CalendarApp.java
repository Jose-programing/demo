package edu.utsa.cs3443.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CalendarView.fxml"));
        Parent root = loader.load();

        // for giving controller to another screen
        // CalendarController controller = loader.getController(); //FOR REUSE

        Scene scene = new Scene(root, 520, 460);
        stage.setTitle("Student Planner — Calendar");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
