package edu.ucsb.cs.cs48.schedoptim.ui.calendar.todo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDao;

public class TodoViewModel extends ViewModel {
    private static MutableLiveData<ArrayList<Task>> tasks = new MutableLiveData(new ArrayList<Task>(0));

    public MutableLiveData<ArrayList<Task>> getObservableTasks(){
        return tasks;
    }
    public TodoViewModel(){
    }
    public void loadDataFromDatabase(TaskDao taskDao, Date day){
        tasks.setValue((ArrayList<Task>)taskDao.loadTodoByDate(Task.formatTaskDate(day)));
    }
}
