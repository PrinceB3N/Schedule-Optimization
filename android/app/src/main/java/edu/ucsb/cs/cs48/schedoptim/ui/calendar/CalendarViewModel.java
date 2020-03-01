package edu.ucsb.cs.cs48.schedoptim.ui.calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import edu.ucsb.cs.cs48.schedoptim.Task;

public class CalendarViewModel extends ViewModel {

    private MutableLiveData<String> allTasks;
    private MutableLiveData<String> taskId;
    private MutableLiveData<String> taskTitle;

    private MutableLiveData<ArrayList<Task>> tasks;

    public CalendarViewModel() {
        allTasks = new MutableLiveData<>();
        taskId = new MutableLiveData<>();
        taskTitle = new MutableLiveData<>();

        tasks = new MutableLiveData<>();
    }

    public LiveData<String> getAllTasks() {
        return allTasks;
    }
    public void setAllTasks(String s) { allTasks.setValue(s); }
    public LiveData<String> getTaskId() {
        return taskId;
    }
    public void setTaskId(String s) { taskId.setValue(s); }
    public LiveData<String> getTaskTitle() {
        return taskTitle;
    }
    public void setTaskTitle(String s) { taskTitle.setValue(s); }

    public LiveData<ArrayList<Task>> getTasks(){ return tasks; }
    public void setTasks(ArrayList<Task> t) { tasks.setValue(t);}


    public ArrayList<ArrayList<Task>> sortByHour() {
        ArrayList<ArrayList<Task>> sbh = new ArrayList<>();
        ArrayList<Task> all = tasks.getValue();

        for (int h = 0; h < 24; h++) {
            sbh.add(new ArrayList<Task>());
        }

        for (int i = 0; i < all.size(); i ++) {
            Task t = all.get(i);
            sbh.get(t.getBegin_time()).add(t);
        }

        return  sbh;
    }


}