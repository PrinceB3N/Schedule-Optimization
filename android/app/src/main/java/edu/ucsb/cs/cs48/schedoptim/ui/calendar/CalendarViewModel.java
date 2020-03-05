package edu.ucsb.cs.cs48.schedoptim.ui.calendar;

import android.app.Application;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import edu.ucsb.cs.cs48.schedoptim.Task;

import java.util.ArrayList;

import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;

public class CalendarViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Task>> tasks;
    private MutableLiveData<ArrayList<String>> tasksWithRoute;
    private MutableLiveData<ArrayList<String>> travelMode;




    public CalendarViewModel() {
//        allTasks = new MutableLiveData<>();
//        taskId = new MutableLiveData<>();
//        taskTitle = new MutableLiveData<>();

        tasks  = new MutableLiveData<>();
        tasksWithRoute  = new MutableLiveData<>();
        travelMode  = new MutableLiveData<>();
    }


//    public void setTasks(ArrayList<Task> t) { tasks.setValue(t); }
//    public void setTaskTitle(String s) { taskTitle.setValue(s); }

    public MutableLiveData<ArrayList<Task>> getTasks(){ return tasks; }
    public void setTasks(ArrayList<Task> t) { tasks.setValue(t);}
    public ArrayList<String> getTasksWithRoute() {return tasksWithRoute.getValue();}
//    public MutableLiveData<ArrayList<String>> getTasksWithRoute() {return tasksWithRoute;}
    public void setTasksWithRoute(ArrayList<String> t){tasksWithRoute.setValue(t);}
//    public MutableLiveData<ArrayList<String>> getTravelMode() { return travelMode; }
    public ArrayList<String> getTravelMode() { return travelMode.getValue(); }
    public void setTravelMode(ArrayList<String> t) { travelMode.setValue(t); }

    public void updateRoute(){
        ArrayList<Task> allTasks = tasks.getValue();
        ArrayList<String> twr = new ArrayList<>();
        ArrayList<String> tm  = new ArrayList<>();

        for (int i = 0; i < allTasks.size(); i++) {
            Task tem = allTasks.get(i);
            if (tem.getCalRoute()){
                twr.add(tem.getLocation());
                tm.add(tem.getTravelMode());
            }
        }

        setTasksWithRoute(twr);
        setTravelMode(tm);

    }


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