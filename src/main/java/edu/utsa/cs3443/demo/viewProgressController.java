package edu.utsa.cs3443.demo;

import edu.utsa.cs3443.demo.model.Topic;
import edu.utsa.cs3443.demo.model.Topics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class viewProgressController {


    @FXML
    private TextArea textArea;

    @FXML
    private Button back;

    Topics topicList = Topics.getInstance();

    public void initialize(){
        populateTextArea();
    }


    private void populateTextArea() {
        if (topicList.getTopics().isEmpty()) {
            textArea.setText("No topics added yet.");
            return;
        }
        topicList.updateList(); //removes topics that reached the session's goal
        StringBuilder sb = new StringBuilder();
        sb.append("=============================\n");
        sb.append("      YOUR STUDY PROGRESS    \n");
        sb.append("=============================\n\n");

        for (Topic topic : topicList.getTopics()) {
            sb.append("Topic            : ").append(topic.getName()).append("\n");
            sb.append("Sessions Done    : ").append(topic.getCurrentSessions()).append("\n");
            sb.append("Total Time       : ").append(topic.getCurrentSessions() * 60).append(" minutes\n");
            sb.append("-----------------------------\n\n");
        }

        sb.append("Total Topics     : ").append(topicList.getTopics().size());

        textArea.setText(sb.toString());
        textArea.setEditable(false);
    }

    @FXML
    void backOnClick(ActionEvent event){
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
