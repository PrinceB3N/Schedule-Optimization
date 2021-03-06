package edu.ucsb.cs.cs48.schedoptim.ui.calendar.todo;

import android.os.Build;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.MainActivity;
import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDao;

public class TodoViewModel extends ViewModel {
    private static final String TAG = "Check stuff";
    private static MutableLiveData<ArrayList<Task>> todos = new MutableLiveData(new ArrayList<Task>(0));

    public MutableLiveData<ArrayList<Task>> getObservableTasks(){
        return todos;
    }
    public TodoViewModel(){
    }
    public void loadDataFromDatabase(TaskDao taskDao, Date day){
        todos.postValue((ArrayList<Task>)taskDao.loadTodoByDate(Task.formatTaskDate(day)));
    }
    public void moveTodosToTasks(TaskDao taskDao, Date day){
//        List<Task> tasks = taskDao.loadTaskByDateEnd(Task.formatTaskDate(day));
//        taskDao.insertAll(this.addTodos(tasks,todos.getValue(),0,1440));
        taskDao.insertAll(this.addTodos(todos.getValue(), taskDao, day));
        loadDataFromDatabase(taskDao,day);
    }
    
    //Assume:   tasks are all in a given day, are sorted by their endTime()
    //          todos are all in a given day, are sorted by their importance level, high to low
    //          start is the earliest time that a todos item can be inserted, by 24 hour time
    //          end is the latest time that a todos item can be inserted, by 24 hour time
//    private List<Task> addTodos(List<Task> tasks, List<Task> todos, int start, int end) {
    private List<Task> addTodos(List<Task> todos, TaskDao taskDao, Date day) {
        List<Task> updatedTasks = taskDao.loadTaskByDateEnd(Task.formatTaskDate(day));
        List<Task> tempTodo = todos;
        Log.d(TAG, "addTodosTemp: " + updatedTasks.get(0).getBegin_time_int());
        for(int i = 0; i < tempTodo.size(); i++) {
            Log.d(TAG, "addTodos: check i: " + i);
            //updatedTasks = taskDao.loadTaskByDateEnd(Task.formatTaskDate(day));
            Task currentTodo = tempTodo.get(i);
            Log.d(TAG, "addTodos: check title: " + currentTodo.getTitle());
            int start = currentTodo.getBegin_time_int();
            int end = currentTodo.getEnd_time_int();
            int duration = currentTodo.getDuration_int();
            boolean notIncluded = true;
            Log.d(TAG, "addTodos: check boolean: " + notIncluded);
            //if -- Check if updatedTasks is empty
            if(updatedTasks.isEmpty()) {
                //Put current todos in the first slot inside its time constraint
                currentTodo.setEnd_time_int(start + duration);
                currentTodo.setType("task");
                updatedTasks.add(currentTodo);
                notIncluded = false;
            }
            //else -- Check between start and first item in tasks
            if(notIncluded) {
                if((start + duration) <= updatedTasks.get(0).getBegin_time_int()){
                    currentTodo.setEnd_time_int(start + duration);
                    currentTodo.setType("task");
                    updatedTasks.add(currentTodo);
                    notIncluded = false;
                }
            }
            //else -- Check between items in tasks
            if(notIncluded) {
                if(updatedTasks.size() > 1){
                    for(int j = 1; j < updatedTasks.size(); j++) {
                        if(updatedTasks.get(j).getBegin_time_int() - updatedTasks.get(j-1).getEnd_time_int() >= duration){
                            if(start <= updatedTasks.get(j-1).getEnd_time_int()) {
                                if(updatedTasks.get(j-1).getEnd_time_int() + duration <= end) {
                                    currentTodo.setBegin_time_int(updatedTasks.get(j-1).getEnd_time_int());
                                    currentTodo.setEnd_time_int(updatedTasks.get(j-1).getEnd_time_int() + duration);
                                    currentTodo.setType("task");
                                    updatedTasks.add(currentTodo);
                                    j = updatedTasks.size();
                                    notIncluded = false;
                                }
                            }
                            if(start >= updatedTasks.get(j-1).getEnd_time_int()){
                                if(start + duration <= updatedTasks.get(j).getBegin_time_int() && start + duration <= end) {
                                    currentTodo.setBegin_time_int(start);
                                    currentTodo.setEnd_time_int(start + duration);
                                    currentTodo.setType("task");
                                    updatedTasks.add(currentTodo);
                                    j = updatedTasks.size();
                                    notIncluded = false;
                                }
                            }
                        }
                    }
                }
            }
            //else -- Check between last item in tasks and end
            if(notIncluded) {
                if(start <= updatedTasks.get(updatedTasks.size() - 1).getEnd_time_int()) {
                    if(updatedTasks.get(updatedTasks.size() - 1).getEnd_time_int() + duration <= end) {
                        currentTodo.setBegin_time_int(updatedTasks.get(updatedTasks.size() - 1).getEnd_time_int());
                        currentTodo.setEnd_time_int(updatedTasks.get(updatedTasks.size() - 1).getEnd_time_int() +duration);
                        currentTodo.setType("task");
                        updatedTasks.add(currentTodo);
                        notIncluded = false;
                    }
                }
                else {
                    currentTodo.setBegin_time_int(start);
                    currentTodo.setEnd_time_int(start + currentTodo.getDuration_int());
                    currentTodo.setType("task");
                    updatedTasks.add(currentTodo);
                    notIncluded = false;
                }
            }
            class Compare implements Comparator<Task> {

                @Override
                public int compare(Task o1, Task o2) {
                    return o1.getEnd_time_int() - o2.getEnd_time_int();
                }

            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updatedTasks.sort(new Compare());
            }else{
                Collections.sort(updatedTasks, new Compare());
            }
            Log.d(TAG, "addTodos: check boolean at end: " + notIncluded);
        }
        return updatedTasks;
    }
}
