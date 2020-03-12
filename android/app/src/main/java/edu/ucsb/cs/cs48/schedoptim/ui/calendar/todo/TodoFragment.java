package edu.ucsb.cs.cs48.schedoptim.ui.calendar.todo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.ucsb.cs.cs48.schedoptim.AddTaskActivity;
import edu.ucsb.cs.cs48.schedoptim.MainActivity;
import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;
import edu.ucsb.cs.cs48.schedoptim.TaskViewActivity;

import static edu.ucsb.cs.cs48.schedoptim.MainActivity.cal;

public class TodoFragment extends Fragment {
    private RecyclerView rvTasks;
    private TaskAdapter adapter;
    private TaskDatabase db;
    private TodoViewModel todoViewModel;
    private ItemTouchHelper touchHelper;
    private ImageView previousDay;
    private ImageView nextDay;
    private TextView currentDate;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_todo, container, false);
        todoViewModel = new ViewModelProvider(TodoFragment.this).get(TodoViewModel.class);
        //Obtain RecyclerView for routes
        rvTasks = root.findViewById(R.id.todo_list);
        // Create adapter passing in the sample user data
        db = Room.databaseBuilder(getContext(),
                TaskDatabase.class, "database-task")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();


        adapter = new TaskAdapter(todoViewModel.getObservableTasks().getValue(), new StartDragListener() {
            public void requestDrag(RecyclerView.ViewHolder viewHolder) {
                touchHelper.startDrag(viewHolder);
            }

            @Override
            public void onSingleTapUp(View v, MotionEvent event) {
                Intent editTodo = new Intent(getContext(), TaskViewActivity.class);
                editTodo.putExtra("TYPE", "todo");
                editTodo.putExtra("ID", v.getId());
                startActivityForResult(editTodo, 1);
            }
        });
        //Add drag-drop helper
        ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvTasks);
        //finally add adapter to recyclerview
        rvTasks.setAdapter(adapter);
        //Attach observer
        todoViewModel.getObservableTasks().observe(getViewLifecycleOwner(),  new Observer<ArrayList<Task>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Task> update_tasks) {
                adapter.update( update_tasks);
            }
        });
        todoViewModel.loadDataFromDatabase(db.taskDao(), MainActivity.cal.getTime());

        // Set layout manager to position the items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rvTasks.setLayoutManager(layoutManager);

        //Set dividers between elements in list
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTasks.getContext(),
                layoutManager.getOrientation());
        rvTasks.addItemDecoration(dividerItemDecoration);
        //TODO: Button for setting to-do's into free slots for the day.
        Button add_todo_list = (Button) root.findViewById(R.id.add_todo_list);
        add_todo_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTodo= new Intent(getContext(), AddTaskActivity.class);
                addTodo.putExtra("TYPE","todo");
                startActivityForResult(addTodo, 1);
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("To Do List");

        currentDate = root.findViewById(R.id.display_current_date);
        currentDate.setText(displayDateInString(cal.getTime()));
        previousDay = root.findViewById(R.id.previous_day);
        nextDay = root.findViewById(R.id.next_day);
        previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousCalendarDate();
            }
        });
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCalendarDate();
            }
        });
        final int[] mYear = {cal.get(Calendar.YEAR)};
        final int[] mMonth = {cal.get(Calendar.MONTH)};
        final int[] mDay = {cal.get(Calendar.DAY_OF_MONTH)};
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.MyDatePickerDialogTheme2,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear[0] = year;
                        mMonth[0] = month;
                        mDay[0] = dayOfMonth;
                        cal.set(year, month, dayOfMonth);
                        currentDate.setText(displayDateInString(cal.getTime()));
                        todoViewModel.loadDataFromDatabase(db.taskDao(), MainActivity.cal.getTime());
                    }
                }, mYear[0], mMonth[0], mDay[0]);
        currentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        ImageView gettodo = root.findViewById(R.id.todoList);
        gettodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigation_left);
            }
        });
        return root;
    }
    private void previousCalendarDate() {
        cal.add(Calendar.DAY_OF_MONTH, -1);
        currentDate.setText(displayDateInString(cal.getTime()));
        todoViewModel.loadDataFromDatabase(db.taskDao(), MainActivity.cal.getTime());
    }

    private void nextCalendarDate() {
        cal.add(Calendar.DAY_OF_MONTH, 1);
        currentDate.setText(displayDateInString(cal.getTime()));
        todoViewModel.loadDataFromDatabase(db.taskDao(), MainActivity.cal.getTime());
    }

    private String displayDateInString(Date mDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH);
        return formatter.format(mDate);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { todoViewModel.loadDataFromDatabase(db.taskDao(),MainActivity.cal.getTime()); }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(db!=null){
            db.close();
        }
    }


    //Assume:   tasks are all in a given day, are sorted by their endTime()
    //          todos are all in a given day, are sorted by their importance level, high to low
    //          start is the earliest time that a todos item can be inserted, by 24 hour time
    //          end is the latest time that a todos item can be inserted, by 24 hour time
    public static List<Task> addTodos(List<Task> tasks, List<Task> todos, int start, int end) {
        List<Task> updatedTasks = tasks;
        for(int i = 0; i < todos.size(); i++) {
            Task currentTodo = todos.get(i);
            boolean notIncluded = true;
            //if -- Check if updatedTasks is empty
            if(updatedTasks.isEmpty()) {
                if(currentTodo.getDuration_int() < end - start) {
                    currentTodo.setBegin_time_int(start);
                    currentTodo.setEnd_time_int(start + currentTodo.getDuration_int());
                    updatedTasks.add(currentTodo);
                    notIncluded = false;
                }
            }
            //else -- Check between start and first item in tasks
            if(notIncluded) {
                if(start + currentTodo.getDuration_int() <= updatedTasks.get(0).getBegin_time_int()){
                    currentTodo.setBegin_time_int(start);
                    currentTodo.setEnd_time_int(start + currentTodo.getDuration_int());
                    updatedTasks.add(currentTodo);
                    notIncluded = false;
                }
            }
            //else -- Check between items in tasks
            if(notIncluded) {
                if(updatedTasks.size() > 1){
                    for(int j = 1; j < updatedTasks.size(); j++) {
                        if(currentTodo.getDuration_int() <= updatedTasks.get(j).getBegin_time_int() - updatedTasks.get(j-1).getEnd_time_int()) {
                            currentTodo.setBegin_time_int(updatedTasks.get(j-1).getEnd_time_int());
                            currentTodo.setEnd_time_int(updatedTasks.get(j).getBegin_time_int());
                            updatedTasks.add(currentTodo);
                            j = updatedTasks.size();
                            notIncluded = false;
                        }
                    }
                }
            }
            //else -- Check between last item in tasks and end
            if(notIncluded) {
                if(updatedTasks.get(updatedTasks.size() - 1).getEnd_time_int() + currentTodo.getDuration_int() <= end){
                    //System.out.println(updatedTasks.size() - 1);
                    currentTodo.setBegin_time_int(updatedTasks.get(updatedTasks.size() - 1).getEnd_time_int());
                    currentTodo.setEnd_time_int(updatedTasks.get(updatedTasks.size() - 1).getEnd_time_int() + currentTodo.getDuration_int());
                    updatedTasks.add(currentTodo);
                }
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

        return updatedTasks;
    }
}
