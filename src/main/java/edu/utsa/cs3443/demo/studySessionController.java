package edu.utsa.cs3443.demo;

import edu.utsa.cs3443.demo.model.Topic;
import edu.utsa.cs3443.demo.model.Topics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class studySessionController {

    @FXML
    private Button _30min;

    @FXML
    private Button _60min;

    @FXML
    private Button addCustomTopic;

    @FXML
    private Label label;

    @FXML
    private Label errorLabel;

    @FXML
    private Button viewProgress;

    @FXML
    private ComboBox<Topic> topicComboBox;

    Topics manager = Topics.getInstance();

    public void initialize(){
        refreshTopics();
    }

    @FXML
    void addCustomTopicOnClick(ActionEvent event) {
        launchCustomTopicScreen(event);

    }

    @FXML
    void _30minOnClick(ActionEvent event){
        Topic selectedTopic = topicComboBox.getValue();
        if(selectedTopic == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Topic Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a topic before starting a study session.");
            alert.showAndWait();
            return;
        }
        launch30minScreen(event,selectedTopic);
    }

    @FXML
    void _60minOnClick(ActionEvent event){
        Topic selectedTopic = topicComboBox.getValue();

        if(selectedTopic == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Topic Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a topic before starting a study session.");
            alert.showAndWait();
            return;
        }
        launch60minScreen(event,selectedTopic);
    }

    @FXML
    void viewProgressOnClick(ActionEvent event){
        launchViewProgressScreen(event);
    }

    public void refreshTopics() {
        //topicComboBox.getItems().clear();
        for(int i =0; i < manager.size(); i++) {
            topicComboBox.getItems().add(manager.getTopicAt(i));
        }
    }



    private void launchCustomTopicScreen(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("customTopic-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Add Custom Topic");
            stage.show();
        } catch(IOException e){
            System.out.println("could not load screen");
            e.printStackTrace();
        }
    }

    private void launch30minScreen(ActionEvent event, Topic topic){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("_30min-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            _30minController controller = fxmlLoader.getController();
            controller.setTopic(topic);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("30 minute study session");
            stage.show();
        } catch(IOException e){
            System.out.println("could not load screen");
            e.printStackTrace();
        }
    }
    private void launch60minScreen(ActionEvent event,Topic topic){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("_60min-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            _60minController controller = fxmlLoader.getController();
            controller.setTopic(topic);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("60 minute study session");
            stage.show();
        } catch(IOException e){
            System.out.println("could not load screen");
            e.printStackTrace();
        }
    }

    private void launchViewProgressScreen(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewProgress-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("View Progress");
            stage.show();
        } catch(IOException e){
            System.out.println("could not load screen");
            e.printStackTrace();
        }
    }

//a victor addition

    @FXML
    void goBack(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuScreen.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            stage.setTitle("Horizon Planner");
            stage.show();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

}

