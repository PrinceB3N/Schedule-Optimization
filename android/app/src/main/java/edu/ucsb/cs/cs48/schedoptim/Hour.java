package edu.ucsb.cs.cs48.schedoptim;

import java.util.ArrayList;
import java.util.List;

public class Hour {
    public int timeBegin;
    public int timeEnd;
    public List<Task> tasksInHour;

    public Hour(int tb, int te){
        timeBegin = tb;
        timeEnd   = te;
        tasksInHour = new ArrayList<>();
    }

    public String getTimeBegin() { return Integer.toString(timeBegin); }
    public void setTimeBegin(int t) {this.timeBegin = t;}
    public String getTimeEnd(){return  Integer.toString(timeEnd);}

    public List<Task> getTasksInHour() {return tasksInHour;}
    public void setTasksInHour(List<Task> l) {this.tasksInHour = l;}
}
