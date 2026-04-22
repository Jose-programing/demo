package edu.utsa.cs3443.demo.model;

public class Topic {
    private String name;
    private int sessionGoal;
    private int currentSessions;

    public Topic(String name, int sessionGoal){
        this.name = name;
        this.sessionGoal = sessionGoal;
        currentSessions = 0;
    }

    public String getName(){
        return this.name;
    }
    public int getCurrentSessions(){
        return this.currentSessions;
    }

    public boolean isCompleted(){
        if(currentSessions >= sessionGoal){
            return true;
        }
        else{
            return false;
        }
    }

    public void increment(){
        this.currentSessions++;
    }

    @Override
    public String toString(){
        return name;
    }
}
