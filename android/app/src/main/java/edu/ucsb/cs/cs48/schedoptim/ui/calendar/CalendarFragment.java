package edu.ucsb.cs.cs48.schedoptim.ui.calendar;
//https://github.com/navi25/nestedRecycler

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.ucsb.cs.cs48.schedoptim.AddTaskActivity;
import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;

public class CalendarFragment extends Fragment {

    CalendarViewModel calendarViewModel;
    TaskDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);


        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTask = new Intent(getContext(), AddTaskActivity.class);
                startActivityForResult(addTask, 1);
            }
        });


         db = Room.databaseBuilder(getContext(),
                TaskDatabase.class, "database-task")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

         updateTasks();




//        calendarViewModel.getTasks().observe(getViewLifecycleOwner(), new Observer<ArrayList<Task>>() {
//            @Override
//            public void onChanged(@Nullable ArrayList<Task> t) {
//                // TODO: Define Behaviours
//            }
//        });


        return root;
    }

    // TODO: for now, this method re-read all Tasks every time, later should dynamically add new Task
    public void updateTasks(){
        calendarViewModel.setTasks((ArrayList<Task>) db.taskDao().loadAllTasks());
        calendarViewModel.updateRoute();
        Toast.makeText(getActivity(), calendarViewModel.getTasks().getValue().get(1).getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { if (requestCode == 1) { updateTasks(); } }


}