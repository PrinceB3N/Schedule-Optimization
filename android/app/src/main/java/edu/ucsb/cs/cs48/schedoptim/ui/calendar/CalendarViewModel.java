package edu.ucsb.cs.cs48.schedoptim.ui.calendar;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.ucsb.cs.cs48.schedoptim.Task;

public class CalendarViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Task>> tasks;
    private String travelMode; // TODO: how to store travel mode


    public CalendarViewModel() {

        tasks  = new MutableLiveData<>();

    }

    public MutableLiveData<ArrayList<Task>> getTasks() {
        return tasks;
    }
    public void setTasks(ArrayList<Task> t) { tasks.setValue(t); }

}