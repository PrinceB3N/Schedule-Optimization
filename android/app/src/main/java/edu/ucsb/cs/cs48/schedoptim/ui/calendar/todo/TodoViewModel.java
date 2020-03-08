package edu.ucsb.cs.cs48.schedoptim.ui.calendar.todo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDao;

public class TodoViewModel extends ViewModel {
    private static MutableLiveData<ArrayList<Task>> tasks = new MutableLiveData(new ArrayList<Task>(0));

    public MutableLiveData<ArrayList<Task>> getObservableTasks(){
        return tasks;
    }
    public TodoViewModel(){
        Task test1 = new Task("Task1","Freebirds");
        test1.setBegin_time("0515");
        test1.setEnd_time("0615");
        Task test2 = new Task("Task2","Embarcadero Hall");
        test2.setBegin_time("0715");
        test2.setEnd_time("0900");
        tasks.setValue(new ArrayList<Task>(Arrays.asList(test1, test2)));
    }
    public void loadDataFromDatabase(TaskDao taskDao, String day){
        tasks.setValue((ArrayList<Task>)taskDao.loadAllByDate(day));
    }
}
