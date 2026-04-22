package edu.utsa.cs3443.demo.model;

import java.util.ArrayList;

public class Topics {

    private ArrayList<Topic> topics;
    private static Topics instance;

    private Topics(){
        topics = new ArrayList<>();
    }

    public int size(){
        return topics.size();
    }

    public static Topics getInstance(){
        if(instance == null){
            instance = new Topics();
        }
        return instance;

    }

    public void setTopics(ArrayList<Topic> topicList){
        this.topics = topicList;
    }
    public ArrayList<Topic> getTopics(){
        return this.topics;
    }

   public Topic getTopicAt(int i){
        return topics.get(i);
   }



    public void addTopic(String name, int goal){
        Topic topic = new Topic(name, goal);
     topics.add(topic);
    }

    public void updateList(){
        int i = 0;
        for(Topic t : topics){
            if(t.isCompleted()){
                topics.remove(i);
            }
            i++;
        }
    }




}
