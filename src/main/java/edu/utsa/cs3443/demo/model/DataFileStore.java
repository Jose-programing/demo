package edu.utsa.cs3443.demo.model;

import edu.utsa.cs3443.demo.Task;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class DataFileStore {
    private static final String FilePath = "data/data.csv";
    public static void load() throws IOException {

        File file = new File(FilePath);
        if (!file.exists()) return;

        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            String[] parts = line.split(",");

            if (parts.length != 5) continue;

            LocalDate date = LocalDate.parse(parts[0]);
            String title = parts[1];
            String type = parts[2];
            int priority = Integer.parseInt(parts[3]);
            String time = parts[4];

            Task task = new Task(date, title, type, priority, time);

            DataStore.taskMap.putIfAbsent(date, new ArrayList<>());
            DataStore.taskMap.get(date).add(task);
        }
        sc.close();
    }

    public static void save() throws IOException {
        FileWriter writer = new FileWriter(FilePath);

        for (LocalDate date : DataStore.taskMap.keySet()) {
            for (Task task : DataStore.taskMap.get(date)) {
                writer.write(task.getDay() + "," + task.getTaskTitle() + "," + task.getTaskType() + "," + task.getPriority() + "," + task.getTime());
                writer.write("\n");
            }
        }
        writer.close();
    }


}


