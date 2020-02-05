package edu.ucsb.cs.cs48.schedoptim.ui.schedule;

import android.content.Intent;
import android.os.Bundle;
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
import edu.ucsb.cs.cs48.schedoptim.TaskDao;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;

public class ScheduleFragment extends Fragment {

    private ScheduleViewModel scheduleViewModel;
    private TaskDao md;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scheduleViewModel =
                ViewModelProviders.of(this).get(ScheduleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final EditText editText = root.findViewById(R.id.editText);
        final EditText edit = root.findViewById(R.id.editText2);
        Button b = root.findViewById(R.id.button);
        Button bt = root.findViewById(R.id.button2);

        final TaskDatabase db = Room.databaseBuilder(getContext(),
                TaskDatabase.class, "database-name")
                .allowMainThreadQueries()
                .build();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ids = editText.getText().toString();
                int id = Integer.valueOf(ids);
                String title = edit.getText().toString();

                Task t = new Task();
                t.setId(id);
                t.setTitle(title);
                db.taskDao().insert(t);

            }

        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ids = editText.getText().toString();
                int id = Integer.valueOf(ids);
                scheduleViewModel.setmText(db.taskDao().findById(id).getTitle());
            }

        });
        scheduleViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}