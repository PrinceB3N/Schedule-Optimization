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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.ucsb.cs.cs48.schedoptim.AddTaskActivity;
import edu.ucsb.cs.cs48.schedoptim.Hour;
import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;
import edu.ucsb.cs.cs48.schedoptim.adapter.HourAdapter;
import edu.ucsb.cs.cs48.schedoptim.adapter.TaskAdapter;

public class CalendarFragment extends Fragment {

    CalendarViewModel calendarViewModel;
    TaskDatabase db;
    FloatingActionButton fab;
    RecyclerView listOfHours;
    ArrayList<Hour> hours;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.calendarContainer, new MonthFragment());
        ft.addToBackStack(null);
        ft.commit();

        fab = root.findViewById(R.id.fab);
        //listOfHours = root.findViewById(R.id.recyclerView_hours);
        hours = new ArrayList<>();


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

//         for (int i = 0; i < 24; i ++){
//             Hour h = new Hour(i);
//             for (int j = 0; j < 1; j++){
//                 Task t = new Task();
//                 t.setTitle("Test"+i+j);
//                 t.setLocation("Lib");
//                 h.getTasksInHour().add(t);
//             }
//             hours.add(h);
//         }

//         Hour h1 = new Hour(1,2);
//        Task t1 = new Task();
//        t1.setTitle("Test1");
//        t1.setLocation("Lib");
//        t1.setBegin_time("01:00");
//        t1.setEnd_time("01:30");
//        h1.getTasksInHour().add(t1);
//         Hour h2 = new Hour(3,6);
//        Task t2 = new Task();
//        t2.setTitle("Test2");
//        t2.setLocation("Lib");
//        t2.setBegin_time("02:00");
//        t2.setEnd_time("04:00");
//        h2.getTasksInHour().add(t2);
//        hours.add(h1);
//        hours.add(h2);
//
//
//         listOfHours.setLayoutManager( new LinearLayoutManager(getContext(),
//                 LinearLayoutManager.VERTICAL, false));
//         listOfHours.setAdapter(new HourAdapter(listOfHours, hours));
//
//         updateTasks();




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
//        Toast.makeText(getActivity(), calendarViewModel.getTasks().getValue().get(1).getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { if (requestCode == 1) { updateTasks(); } }


}