package edu.utsa.cs3443.demo.model;

import edu.utsa.cs3443.demo.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DataStore {
    public static HashMap<LocalDate, ArrayList<Task>> taskMap = new HashMap<>();
}
