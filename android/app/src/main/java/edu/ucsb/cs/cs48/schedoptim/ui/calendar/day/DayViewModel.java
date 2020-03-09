package edu.ucsb.cs.cs48.schedoptim.ui.calendar.day;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import edu.ucsb.cs.cs48.schedoptim.MainActivity;
import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;

public class DayViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Task>> tasks;
    private TaskDatabase taskDatabase;

    public DayViewModel(@NonNull Application application){
        super(application);
        tasks  = new MutableLiveData<>();
        taskDatabase = Room.databaseBuilder(application,
                TaskDatabase.class, "database-task")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        Task test1 = new Task("Task1","Freebirds");
        test1.setBegin_time("0315");
        test1.setEnd_time("0515");


        Task test2 = new Task("Task2","Embarcadero Hall");
        test2.setBegin_time("0715");
        test2.setEnd_time("1300");

        Task test3 = new Task("Task3","UCEN");
        test3.setBegin_time("0000");
        test3.setEnd_time("0130");

        Task test4 = new Task("Task4","UCEN");
        test4.setBegin_time("0000");
        test4.setEnd_time("0045");

        Task test5 = new Task("Task5","UCEN");
        test5.setBegin_time("0300");
        test5.setEnd_time("0400");

        Task test6 = new Task("Task6","UCEN");
        test6.setBegin_time("0200");
        test6.setEnd_time("0300");

//        tasks.setValue(new ArrayList<Task>(Arrays.asList(test1, test2,test3,test4,test5,test6)));
        loadDataFromDatabase("3/8/2020");
    }
    public MutableLiveData<ArrayList<Task>> getObservableTasks(){
        return tasks;
    }
    public void loadDataFromDatabase(Date date){
        tasks.postValue((ArrayList<Task>)taskDatabase.taskDao().loadTaskByDate(Task.formatTaskDate(date)));
    }
    public ArrayList<Task> getAndLoadDataFromDatabase(Date date){
        loadDataFromDatabase(date);
        return tasks.getValue();
    }
    public void loadDataFromDatabase(String date){
        tasks.postValue((ArrayList<Task>)taskDatabase.taskDao().loadTaskByDate(date));
    }
    public ArrayList<Task> getAndLoadDataFromDatabase(String date){
        loadDataFromDatabase(date);
        return tasks.getValue();
    }
}
