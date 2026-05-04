package edu.utsa.cs3443.demo;

import edu.utsa.cs3443.demo.model.Topic;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class _60minController {

    @FXML
    private Button backButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button startButton;

    @FXML
    private Label completeLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private Label labelTopic;

    private Timeline timeline;

    private Topic currentTopic;

    private int secondsRemaining = 60 * 60;

    public void initialize(){

        updateLabel();
        pauseButton.setDisable(true);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1),e ->{
            secondsRemaining--;
            updateLabel();

            if(secondsRemaining <= 0){
                timeline.stop();
                startButton.setDisable(true);
                pauseButton.setDisable(true);
                timerLabel.setText("00:00");
                currentTopic.increment();
                completeLabel.setVisible(true);
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void setTopic(Topic topic){
        this.currentTopic = topic;
        labelTopic.setText("Study Session Topic: "+ currentTopic);
    }

    @FXML
    private void startOnClick(){
        timeline.play();
        startButton.setDisable(true);
        pauseButton.setDisable(false);
        System.out.println("TOPIC SELECTED: " + currentTopic);
    }

    @FXML
    private void pauseOnClick(){
        timeline.pause();
        pauseButton.setDisable(true);
        startButton.setDisable(false);
    }

    private void updateLabel(){
        int minutes = secondsRemaining / 60;
        int seconds = secondsRemaining % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    @FXML
    void backOnClick(ActionEvent event) {
        timeline.stop();
        secondsRemaining = 60 * 60;
        updateLabel();
        startButton.setDisable(false);
        pauseButton.setDisable(true);
        launchStudySessionScreen(event);

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
