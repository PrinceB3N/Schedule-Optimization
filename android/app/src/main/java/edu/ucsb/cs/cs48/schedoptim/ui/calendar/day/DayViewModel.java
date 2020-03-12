package edu.ucsb.cs.cs48.schedoptim.ui.calendar.day;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.MainActivity;
import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;


public class DayViewModel extends AndroidViewModel {
    private ArrayList<Task> tasks;
    private TaskDatabase taskDatabase;

    public DayViewModel(@NonNull Application application){
        super(application);
        tasks  = new ArrayList<>();
        taskDatabase = Room.databaseBuilder(application,
                TaskDatabase.class, "database-task")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

//        tasks.setValue(new ArrayList<Task>(Arrays.asList(test1, test2,test3,test4,test5,test6)));
    }
    public ArrayList<Task> getAndLoadDataFromDatabase(Date date){
        Log.d(MainActivity.class.getName(),"Loop?");
        loadDataFromDatabase(date);
        return tasks;
    }
    public void loadDataFromDatabase(Date date){
        tasks = ((ArrayList<Task>)taskDatabase.taskDao().loadTaskByDate(Task.formatTaskDate(date)));
    }
    public void loadDataFromDatabase(String date){
        tasks=((ArrayList<Task>)taskDatabase.taskDao().loadTaskByDate(date));
    }
    public List<Task> getAndLoadDataFromDatabase(String date){
        loadDataFromDatabase(date);
        return tasks;
    }
    @Override
    public void onCleared(){
        if(taskDatabase!=null)
            taskDatabase.close();
    }

}
