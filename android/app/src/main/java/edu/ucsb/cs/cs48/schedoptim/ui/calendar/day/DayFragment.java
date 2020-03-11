package edu.ucsb.cs.cs48.schedoptim.ui.calendar.day;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;
import android.text.Html;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.ucsb.cs.cs48.schedoptim.AddTaskActivity;
import edu.ucsb.cs.cs48.schedoptim.MainActivity;
import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;
import edu.ucsb.cs.cs48.schedoptim.ui.calendar.day.DayViewModel;
import edu.ucsb.cs.cs48.schedoptim.ui.calendar.todo.TodoFragment;
import edu.ucsb.cs.cs48.schedoptim.ui.calendar.todo.TodoViewModel;

import static androidx.constraintlayout.widget.ConstraintSet.WRAP_CONTENT;
import static edu.ucsb.cs.cs48.schedoptim.MainActivity.cal;
public class DayFragment extends Fragment {
    private final int EXTRA_PADDING = 5;
    private ImageView previousDay;
    private ImageView nextDay;
    private TextView currentDate;
    private ConstraintLayout mLayout;
    private int eventIndex;
    private DayViewModel dayViewModel;
    private int child_views = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.dayview, container, false);
        dayViewModel = new ViewModelProvider(DayFragment.this).get(DayViewModel.class);
        mLayout = root.findViewById(R.id.left_event_column);
        eventIndex = mLayout.getChildCount();
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

        FloatingActionButton fab = root.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
//                navController.navigate(R.id.action_navigation_left_to_todoFragment);
//            }
//        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTask = new Intent(getContext(), AddTaskActivity.class);
                startActivityForResult(addTask, 1);
            }
        });
        TextView date = root.findViewById(R.id.display_current_date);
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
                        update();
                    }
                }, mYear[0], mMonth[0], mDay[0]);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        ImageView gettodo = root.findViewById(R.id.todoList);
        gettodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.todoFragment);
            }
        });


        //Set screen
        update();
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            update();
        }
    }

    private void previousCalendarDate() {
        cal.add(Calendar.DAY_OF_MONTH, -1);
        currentDate.setText(displayDateInString(cal.getTime()));
        update();
    }

    private void nextCalendarDate() {
        cal.add(Calendar.DAY_OF_MONTH, 1);
        currentDate.setText(displayDateInString(cal.getTime()));
        update();
    }

    private String displayDateInString(Date mDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH);
        return formatter.format(mDate);
    }

    /**
     * Grabs Tasks from database by Calendar's current date.
     * Then, will turn them into Views.
     */
    public void displayDailyTasks() {
        List<Task> todayTasks = dayViewModel.getAndLoadDataFromDatabase(cal.getTime());
        Log.d(MainActivity.class.getName(), "Tasks" + todayTasks.toString());
        for (Task task : todayTasks) {
            displayTask(task);
        }
    }

    /**
     * Displays Task as a TextView in the Constraint Layout with
     * proper formatting. Assumes task's time is in hhmm form
     * and the task has required fields filled.
     *
     * @param task Task object from Database
     */
    public void displayTask(Task task) {
        int height = getEventTimeFrame(task.getBegin_time(), task.getEnd_time());
        int topmargin = getMarginFromTop(task.getBegin_time());
        createEventView(task, topmargin, height);
    }

    //Assume hhss format, returns height of task in pixels
    private int getEventTimeFrame(String start, String end) {

        int hours = Integer.parseInt(start.substring(0, 2)) * 60;
        int mins = Integer.parseInt(start.substring(3));

        int hours2 = Integer.parseInt(end.substring(0, 2)) * 60;
        int mins2 = Integer.parseInt(end.substring(3));
        int difference_min = (hours2 + mins2) - (hours + mins);

        int diff_hours = difference_min / 60;
        int diff_mins = difference_min - diff_hours * 60;
        return ((diff_hours) * 60) + diff_mins;
    }

    //returns distance from top view in pixels
    private int getMarginFromTop(String start) {
        int hours = Integer.parseInt(start.substring(0, 2));
        int minutes = Integer.parseInt(start.substring(3));
        int topViewMargin = (hours * 60) + minutes;
        return topViewMargin;
    }

    private void createEventView(Task task, int topMargin, int height) {
        int dp_height = pixels_to_dp(height);
        int dp_margin = pixels_to_dp(topMargin);
        //Set constraints on textview
        ConstraintSet set = new ConstraintSet();
        TextView taskView = new TextView(getContext());
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topToTop = mLayout.getId();
        layoutParams.leftToLeft = R.id.vguideline20;

        taskView.setLayoutParams(layoutParams);
        //set additional attributes of textview
        taskView.setLines(3);
        taskView.setText(Html.fromHtml("<font color=\"green\"><b>Title<b><font>" + task.getTitle() + "</font color=\"blue\"></small></small></font>" + "<br/>"
                + "<font color=\"blue\"><small>Begin Time:</small></font>" + formatTime(task.getBegin_time()) + "<font color=\"blue\"><small>End time</small></font>" + formatTime(task.getEnd_time()) + "<br/>"
                + "<font color=\"purple\"><i>Location:<i><font>" + task.getLocation()));
        taskView.setId(100 + child_views);
        child_views++;
        taskView.setTextColor(Color.parseColor("#ffffff"));
        taskView.setBackgroundColor(Color.LTGRAY);    //TODO: replace with task color
        taskView.setAlpha(.6f);
        taskView.setTag(task.getId());              //TODO: feed id into edittask
        if (dp_height > 5)
            taskView.setHeight(dp_height - 5);
        else
            taskView.setHeight(dp_height);
        taskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        //add view to layout and format constraint layout
        mLayout.addView(taskView, eventIndex - 1);
        set.clone(mLayout);
        set.connect(taskView.getId(), ConstraintSet.TOP, mLayout.getId(), ConstraintSet.TOP, dp_margin + EXTRA_PADDING);//TODO: CHANGE
        set.connect(taskView.getId(), ConstraintSet.LEFT, mLayout.getId(), ConstraintSet.LEFT, 24);
        set.applyTo(mLayout);
    }

    //Takes pixels and return rounded length in dp
    private int pixels_to_dp(int pixels) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }

    //Expects hhmm format
    private String formatTime(String time) {
        String mins = time.substring(2);
        int hours = Integer.parseInt(time.substring(0, 2));
        if (hours == 0) {
            return "12:" + mins + " AM";
        } else if (hours < 12) {
            return hours + ":" + mins + " AM";
        } else if (hours == 12) {
            return "12:" + mins + " PM";
        } else if (hours > 12) {
            int pm_hours = hours - 12;
            return pm_hours + ":" + mins + " PM";
        }
        return "ERROR";
    }

    //Reset tasks in view
    private void update() {
        mLayout.removeAllViewsInLayout();
        child_views = 0;
        displayDailyTasks();
    }
}