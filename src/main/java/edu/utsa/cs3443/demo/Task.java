package edu.utsa.cs3443.demo;

import java.time.LocalDate;

public class Task {

    //day has been changed to LocalDate type
    private String taskTitle;
    private String taskType;
    private int priority;
    private String time;
    private LocalDate day;


    public Task(LocalDate day, String taskTitle, String taskType, int priority, String time) {
        this.day = day;
        this.taskTitle = taskTitle;
        this.taskType = taskType;
        this.priority = priority;
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%-24s | %-19s | Priority: %d | Time: %s\n", taskTitle, taskType, priority, time);
}


    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }
    public LocalDate getDay() {
        return day;
    }


}

