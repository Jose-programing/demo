package edu.utsa.cs3443.demo;
import edu.utsa.cs3443.demo.model.Topics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.InputMismatchException;

public class customScreenController {

    @FXML
    private Button back;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField sessionGoal;

    @FXML
    private Button sumbit;

    @FXML
    private TextField topicName;

    public Topics manager = Topics.getInstance();

    @FXML
    void backOnClick(ActionEvent event) {
     launchStudySessionScreen(event);
    }

    @FXML
    void sumbitOnClick(ActionEvent event) {
     String name = topicName.getText();
     try {
         int topicGoal = Integer.parseInt(sessionGoal.getText());
         manager.addTopic(name, topicGoal);
     } catch(NumberFormatException e){
         errorLabel.setText("Please enter a valid number");
         errorLabel.setVisible(true);
         return;
     }
     if(name.isEmpty()){
         errorLabel.setText("Please enter a valid topic name");
         errorLabel.setVisible(true);
         return;
     }
     errorLabel.setText("Topic Successfully added!");
     errorLabel.setTextFill(Paint.valueOf("GREEN"));
     errorLabel.setVisible(true);

    }

    private void launchStudySessionScreen(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("studySession-screen.fxml"));
            //Scene scene = new Scene(fxmlLoader.load());
            javafx.scene.Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setTitle("Study Session");
            //stage.show();
        } catch(IOException e){
            System.out.println("could not load screen");
            e.printStackTrace();
        }
    }
}
