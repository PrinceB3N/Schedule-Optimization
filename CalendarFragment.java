package edu.ucsb.cs.cs48.schedoptim.ui.calendar;

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
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);


        final TaskDatabase db = Room.databaseBuilder(getContext(),
                TaskDatabase.class, "database-task")
                .allowMainThreadQueries()
                .build();

        calendarViewModel.setAllTasks(db.taskDao().getAll().toString());
        calendarViewModel.setTaskId("ID(int)");
        calendarViewModel.setTaskTitle("Title(String)");



        return root;
    }
}