//package edu.ucsb.cs.cs48.schedoptim.ui.calendar;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.room.Room;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import edu.ucsb.cs.cs48.schedoptim.MainActivity;
//import edu.ucsb.cs.cs48.schedoptim.R;
//import edu.ucsb.cs.cs48.schedoptim.Task;
//import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;
//
//public class DayFragment extends Fragment {
//    private static final String TAG = DayFragment.class.getSimpleName();
//    private ImageView previousDay;
//    private ImageView nextDay;
//    private TextView currentDate;
//    private Calendar cal = Calendar.getInstance();
//    private RelativeLayout mLayout;
//    private int eventIndex;
//    private TaskDatabase taskDatabase;
//    private int new_children=0;
//    private static final int CHILDREN = 25;
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View root = inflater.inflate(R.layout.day_fragment, container, false);
//
//        Bundle args = getArguments();
//        cal.setTime(new Date(args.getInt("year"),args.getInt("month"),args.getInt("dayOfMonth")));
//
//        mLayout = root.findViewById(R.id.left_event_column);
//        taskDatabase = Room.databaseBuilder(getContext(),
//                TaskDatabase.class, "database-task")
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build();
//
//        eventIndex = mLayout.getChildCount();
//        currentDate = root.findViewById(R.id.display_current_date);
//        currentDate.setText(displayDateInString(cal.getTime()));
//        displayDailyEvents();
//        previousDay = root.findViewById(R.id.previous_day);
//        nextDay = root.findViewById(R.id.next_day);
//        previousDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                previousCalendarDate();
//            }
//        });
//        nextDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nextCalendarDate();
//            }
//        });
//
//        return root;
//    }
//    private void previousCalendarDate(){
//        mLayout.removeViewAt(eventIndex - 1);
//        cal.add(Calendar.DAY_OF_MONTH, -1);
//        currentDate.setText(displayDateInString(cal.getTime()));
//        displayDailyEvents();
//    }
//    private void nextCalendarDate(){
//        mLayout.removeViewAt(eventIndex - 1);
//        cal.add(Calendar.DAY_OF_MONTH, 1);
//        currentDate.setText(displayDateInString(cal.getTime()));
//        displayDailyEvents();
//    }
//    private String displayDateInString(Date mDate){
//        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH);
//        return formatter.format(mDate);
//    }
//    //TODO: Query tasks from database
//    private void displayDailyEvents(){
//
//        int month = (cal.get(Calendar.MONTH)+1)*1000000;
//        int mDay = cal.get(Calendar.DAY_OF_MONTH)*10000;
//        int year = cal.get(Calendar.YEAR);
//        List<Task> todayTasks = taskDatabase.taskDao().loadAllByDate(month+mDay+year);
//        for(Task task: todayTasks){
//            int eventBlockHeight = getEventTimeFrame(task.getBegin_time(),task.getEnd_time());
//            displayEventSection(task.getBegin_time(),eventBlockHeight,"Test");
//        }
//        Log.d(MainActivity.class.getName(),"CHILDREN:"+mLayout.getChildCount());
//        int eventBlockHeight = getEventTimeFrame("0515", "0615");
//        displayEventSection(new Date(2020,3,6,5,15),eventBlockHeight,"Test");
//        displayEventSection(new Date(2020,3,6,1,30),60,"Here");
//    }
//    //Assume hhss format
//    private int getEventTimeFrame(String start, String end){
//
//        int hours = Integer.parseInt(start.substring(0,2))*3600;
//        int mins = Integer.parseInt(start.substring(2))*60;
//
//        int hours2 = Integer.parseInt(end.substring(0,2))*3600;
//        int mins2 = Integer.parseInt(end.substring(2))*60;
//        int difference_milli = 1000*((hours2+mins2) - (hours+mins));
//
//        int diff_hours = difference_milli/(1000*60*60);
//        int diff_mins = difference_milli/(1000*60);
//        Log.d(MainActivity.class.getName(),""+hours/3600);
//        Log.d(MainActivity.class.getName(),""+(diff_hours*60) + ((diff_mins*60) / 100));
//        return (diff_hours*60) + ((diff_mins*60) / 100);
//    }
//    private void displayEventSection(Task task,String start, int height, String message){
//        int hours = Integer.parseInt(start.substring(0,2));
//        int minutes = Integer.parseInt(start.substring(2));
//        int topViewMargin = (hours * 60) + minutes;
//        createEventView(task,topViewMargin, height, message);
//    }
//    private void displayEventSection(String start, int height, String message){
//        int hours = Integer.parseInt(start.substring(0,2));
//        int minutes = Integer.parseInt(start.substring(2));
//        int topViewMargin = (hours * 60) + minutes;
//        createEventView(topViewMargin, height, message);
//    }
//    private void displayEventSection(Task task, Date eventDate, int height, String message){
//        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
//        String displayValue = timeFormatter.format(eventDate);
//        String[]hourMinutes = displayValue.split(":");
//        int hours = Integer.parseInt(hourMinutes[0]);
//        int minutes = Integer.parseInt(hourMinutes[1]);
//        Log.d(TAG, "Hour value " + hours);
//        Log.d(TAG, "Minutes value " + minutes);
//        int topViewMargin = ((hours) * 60) + minutes;
//        Log.d(TAG, "Margin top " + topViewMargin);
//        createEventView(task, topViewMargin, height, message);
//    }
//    private void displayEventSection(Date eventDate, int height, String message){
//        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
//        String displayValue = timeFormatter.format(eventDate);
//        String[]hourMinutes = displayValue.split(":");
//        int hours = Integer.parseInt(hourMinutes[0]);
//        int minutes = Integer.parseInt(hourMinutes[1]);
//        Log.d(TAG, "Hour value " + hours);
//        Log.d(TAG, "Minutes value " + minutes);
//        int topViewMargin = ((hours) * 60) + minutes;
//        Log.d(TAG, "Margin top " + topViewMargin);
//        createEventView(topViewMargin, height, message);
//    }
//    private void createEventView(Task task,int topMargin, int height, String message){
//        final TextView mEventView = new TextView(this.getContext());
//        RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        lParam.topMargin = topMargin*2;
//        lParam.leftMargin = 24;
//
//        lParam.height=120;
//
//        mEventView.setLayoutParams(lParam);
//        mEventView.setPadding(24, 0, 24, 0);
//        //mEventView.setHeight(height * 2);
//        mEventView.setGravity(0x11);
//        mEventView.setTextColor(Color.parseColor("#ffffff"));
//        mEventView.setText(message);
//        mEventView.setBackgroundColor(Color.BLUE);
//        mEventView.setAlpha(.6f);
//        mEventView.setTag("taskView"+task.getId());
//        new_children++;
//        Log.d(MainActivity.class.getName(),"CHILDREN:"+mLayout.getChildCount());
//        mEventView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                update();
//            }
//        });
//        mLayout.addView(mEventView, eventIndex - 1);
//    }
//    private void createEventView(int topMargin, int height, String message){
//        final TextView mEventView = new TextView(this.getContext());
//        RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        lParam.topMargin = topMargin*2;
//        lParam.leftMargin = 24;
//
//        mEventView.setLayoutParams(lParam);
//        mEventView.setPadding(24, 0, 24, 0);
//        mEventView.setHeight(height * 3);
//        mEventView.setGravity(0x11);
//        mEventView.setTextColor(Color.parseColor("#ffffff"));
//        mEventView.setText(message);
//        mEventView.setBackgroundColor(Color.BLUE);
//        mEventView.setAlpha(.6f);
//        mEventView.setTag("taskView");
//        new_children++;
//        Log.d(MainActivity.class.getName(),"CHILDREN:"+mLayout.getChildCount());
//        mEventView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                update();
//            }
//        });
//        mLayout.addView(mEventView, eventIndex - 1);
//    }
//    private void update(){
//        mLayout.removeViewsInLayout(CHILDREN-1, new_children);
//        new_children=0;
//        displayDailyEvents();
//    }
//}