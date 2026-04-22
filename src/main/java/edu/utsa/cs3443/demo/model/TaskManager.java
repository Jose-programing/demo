package edu.utsa.cs3443.demo.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {
    private ArrayList<Task> Tasks = new ArrayList<>();

    public ArrayList<Task> getTasks() {
        return Tasks;
    }

    public void addTask(Task task) {
        Tasks.add(task);
        sortByPriority();
    }

    public boolean taskExists(String title, String time) {
        for (Task t : Tasks) {
            if (t.getTaskTitle().equalsIgnoreCase(title.trim()) && t.getTime().equalsIgnoreCase(time)) {
                return true;
            }
        }
        return false;
    }

    public boolean priorityExists(int priority) { // this is temporary. Hoping to implement a sorting priority
        for (Task t : Tasks) {
            if (t.getPriority() == priority) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteTask(Task task) throws IOException {
        Tasks.remove(task);
        sortByPriority();
        SaveDataToFile();
        return true;
    }

    public void SaveDataToFile() throws IOException {
        FileWriter writer = new FileWriter("data/data.csv"); //Still need to choose the file to save the data"

        for (Task task : Tasks) {
            writer.write(convertTaskToLine(task));
            writer.write("\n");
        }
        writer.close();
    }

    public String convertTaskToLine(Task task) {
        return task.getDay() + "," + task.getTaskTitle() + "," + task.getTaskType() + "," + task.getPriority() + "," + task.getTime();
    }

    public void loadTasks() throws IOException {
        File file = new File("data/data.csv");//I still have to specify the path to the data file
        Scanner scnr = new Scanner(file);
        while (scnr.hasNextLine()) {
            String line = scnr.nextLine();
            Task task = convertLineToTask(line);
            if (task != null) {
                addTask(task);
            }
        }
        scnr.close();
    }

    private Task convertLineToTask(String line) {
        if (line == null || line.isEmpty()) {
            return null;
        }
        String[] parts = line.split(",");
        if (parts.length != 5) {
            System.out.println("invalid line format");
            return null;
        }
        String day = parts[0].trim();
        String title = parts[1].trim();
        String type = parts[2].trim();
        int priority = Integer.parseInt(parts[3].trim());
        String time = parts[4].trim();

        return new Task(day, title, type, priority, time);
    }


    public void sortByPriority() {
        Tasks.sort((t1, t2) -> Integer.compare(t1.getPriority(), t2.getPriority()));
    }

}

