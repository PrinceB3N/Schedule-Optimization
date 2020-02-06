package edu.ucsb.cs.cs48.schedoptim.ui.schedule;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;

public class ScheduleFragment extends Fragment {

    private ScheduleViewModel scheduleViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scheduleViewModel =
                ViewModelProviders.of(this).get(ScheduleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        final TextView print = root.findViewById(R.id.text_print);
        final EditText idText = root.findViewById(R.id.text_id);
        final EditText titleText = root.findViewById(R.id.text_title);
        Button insert = root.findViewById(R.id.button_insert);
        Button delete = root.findViewById(R.id.button_delete);


        final TaskDatabase db = Room.databaseBuilder(getContext(),
                TaskDatabase.class, "database-task")
                .allowMainThreadQueries()
                .build();

        scheduleViewModel.setAllTasks(db.taskDao().getAll().toString());
        scheduleViewModel.setTaskId("ID(int)");
        scheduleViewModel.setTaskTitle("Title(String)");



        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Task t = new Task();
                    String ids = idText.getText().toString();
                    scheduleViewModel.setTaskId(ids);
                    String title = titleText.getText().toString();
                    scheduleViewModel.setTaskTitle(title);
                    t.setId(Integer.valueOf(ids));
                    t.setTitle(title);
                    db.taskDao().insert(t);
                    scheduleViewModel.setAllTasks(db.taskDao().getAll().toString());
                    Toast.makeText(getContext(), "Successfully inserted Task with id: " + t.getId(), Toast.LENGTH_SHORT).show();
                } catch (java.lang.NumberFormatException e){
                    Toast.makeText(getContext(), "Please enter valid int number", Toast.LENGTH_SHORT).show();
                }

            }

        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.taskDao().deleteAll(db.taskDao().getAll());
                scheduleViewModel.setAllTasks(db.taskDao().getAll().toString());
//                Toast.makeText(getContext(), "Successfully deleted Task with id: " + t.getId(), Toast.LENGTH_SHORT).show();
            }

        });

        scheduleViewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                print.setText(scheduleViewModel.getAllTasks().getValue());
            }
        });


        return root;
    }
}