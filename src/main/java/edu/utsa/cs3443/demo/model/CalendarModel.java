package edu.utsa.cs3443.demo.model;

import edu.utsa.cs3443.demo.Task;
//import edu.utsa.cs3443.demo.Task;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

import static edu.utsa.cs3443.demo.model.DataStore.taskMap;

public class CalendarModel {

    private YearMonth currentYearMonth;
    private LocalDate selectedDate;

    // removed taskmap from calander model


    public CalendarModel() {
        this.currentYearMonth = YearMonth.now();
        this.selectedDate = null;
    }




    public YearMonth getCurrentYearMonth() {
        return currentYearMonth;
    }

    public void setCurrentYearMonth(YearMonth yearMonth) {
        this.currentYearMonth = yearMonth;
    }



    //Select date

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDate date) {
        this.selectedDate = date;
    }





    /**
     * Returns the number of tasks on a given date.
     * USed to show number of tasks on a day (before having to go to day screen)
     */
    public int getTaskCount(LocalDate date) {
        return DataStore.taskMap.getOrDefault(date, new ArrayList<Task>()).size();
    }




    //Some helpers for fixing up the calendar

    /**
     * How many days are in the currently displayed month.
     */
    public int getDaysInMonth() {
        return currentYearMonth.lengthOfMonth();
    }

    /**
     * Checks for which day of the week to start on
     * Used to offset the grid
     */
    public int getStartOffset() {
        return currentYearMonth.atDay(1).getDayOfWeek().getValue() - 1; //say it with me now //cause array
    }

    public boolean isToday(LocalDate date) {
        return date.equals(LocalDate.now());
    }

    public boolean isSelected(LocalDate date) {
        return date.equals(selectedDate);
    }
}
