package edu.ucsb.cs.cs48.schedoptim;

import java.util.ArrayList;
import java.util.List;

public class Hour {
    public int time;
    public List<Task> tasksInHour;

    public Hour(int t){
        time = t;
        tasksInHour = new ArrayList<>();
    }

    public String getTime() { return Integer.toString(time); }
    public void setTime(int t) {this.time = t;}

    public List<Task> getTasksInHour() {return tasksInHour;}
    public void setTasksInHour(List<Task> l) {this.tasksInHour = l;}
}
