package edu.ucsb.cs.cs48.schedoptim.ui.schedule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScheduleViewModel extends ViewModel {

    private MutableLiveData<String> allTasks;
    private MutableLiveData<String> taskId;
    private MutableLiveData<String> taskTitle;

    public ScheduleViewModel() {
        allTasks = new MutableLiveData<>();
        taskId = new MutableLiveData<>();
        taskTitle = new MutableLiveData<>();
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
}