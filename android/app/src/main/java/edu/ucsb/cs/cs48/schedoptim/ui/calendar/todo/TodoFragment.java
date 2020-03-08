package edu.ucsb.cs.cs48.schedoptim.ui.calendar.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;

public class TodoFragment extends Fragment {
    private RecyclerView rvTasks;
    private TaskAdapter adapter;
    private TaskDatabase db;
    private TodoViewModel todoViewModel;
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

        //Set up recyclerview intial and update process
        adapter = new TaskAdapter(todoViewModel.getObservableTasks().getValue());
        rvTasks.setAdapter(adapter);
        todoViewModel.getObservableTasks().observe(getViewLifecycleOwner(),  new Observer<ArrayList<Task>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Task> update_tasks) {
                adapter.update( update_tasks);
            }
        });
        //TODO: call loaded tasks
        //todoViewModel.loadDataFromDatabase(db.taskDao(), 03072020);

        // Set layout manager to position the items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rvTasks.setLayoutManager(layoutManager);

        //Set dividers between elements in list
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTasks.getContext(),
                layoutManager.getOrientation());
        rvTasks.addItemDecoration(dividerItemDecoration);
        //TODO:
        //Button for setting to-do's into free slots for the day.
        Button add_todo_list = (Button) root.findViewById(R.id.add_todo_list);
        add_todo_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return root;
    }
}