package edu.ucsb.cs.cs48.schedoptim;

// DatePicker: https://www.jianshu.com/p/d3744c2b480a

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.room.Room;

import java.util.Calendar;

import edu.ucsb.cs.cs48.schedoptim.adapter.PlaceAutoSuggestAdapter;


import static edu.ucsb.cs.cs48.schedoptim.ui.notifications.AlarmCreator.createAlarm;

//import static edu.ucsb.cs.cs48.schedoptim.JSONUtils.getObjectFromJSON;

public class AddTaskActivity extends Activity {

    Boolean isTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_task);

        final Switch mode = findViewById(R.id.switch_mode);
        Button cancel = findViewById(R.id.button_cancel);
        Button save = findViewById(R.id.button_add);
        // Title
        final EditText title = findViewById(R.id.textinput_title);
        // Location
        final AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(AddTaskActivity.this, android.R.layout.simple_list_item_1));
        final CheckBox addToRoute = findViewById(R.id.add_to_route);
        final Spinner travelMode = findViewById(R.id.spinner_travel_mode);
        // Time
        final TextView beginTime= findViewById(R.id.textView_time_begin);
        final TextView endTime= findViewById(R.id.textView_time_end);
        final TextView duration= findViewById(R.id.textView_duration);
        // Date
        final TextView date= findViewById(R.id.textView_date);
        // Notification
        final Switch needNotification= findViewById(R.id.switch_notification);
        final TextView timeBefore= findViewById(R.id.textView_time_before);
        // Importance
        final Spinner importance = findViewById(R.id.spinner_importance);
        // Note
        final EditText note  = findViewById(R.id.editText_note);


        Calendar ca = Calendar.getInstance();
        final int[] mYear = {ca.get(Calendar.YEAR)};
        final int[] mMonth = {ca.get(Calendar.MONTH)};
        final int[] mDay = {ca.get(Calendar.DAY_OF_MONTH)};
        final int[] mHour = {ca.get(Calendar.HOUR_OF_DAY)}; // current 0; begin 1; end 2;
        final int[] mMinute = {ca.get(Calendar.MINUTE)};    // duration 3; minuteBefore 4


        final DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, R.style.MyDatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear[0] = year;
                        mMonth[0] = month;
                        mDay[0] = dayOfMonth;
                        date.setText(month+"/"+dayOfMonth+"/"+year);
                    }
                },
                mYear[0], mMonth[0], mDay[0]);

        final TimePickerDialog beginTimePickerDialog = new TimePickerDialog(AddTaskActivity.this, R.style.MyDatePickerDialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour[1] = hourOfDay;
                        mMinute[1] = minute;
                        beginTime.setText(hourOfDay+":"+minute);
                    }
                }, mHour[0], mMinute[0], true);

        final TimePickerDialog endTimePickerDialog = new TimePickerDialog(AddTaskActivity.this, R.style.MyDatePickerDialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour[2] = hourOfDay;
                        mMinute[2] = minute;
                        endTime.setText(hourOfDay+":"+minute);
                    }
                }, mHour[0], mMinute[0], true);

        final TimePickerDialog durationPickerDialog = new TimePickerDialog(AddTaskActivity.this, R.style.MyDatePickerDialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour[3] = hourOfDay;
                        mMinute[3] = minute;
                        duration.setText(hourOfDay+":"+minute);
                    }
                }, mHour[0], mMinute[0], true);
        final TimePickerDialog timeBeforePickerDialog = new TimePickerDialog(AddTaskActivity.this, R.style.MyDatePickerDialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour[4] = hourOfDay;
                        mMinute[4] = minute;
                        timeBefore.setText(hourOfDay+":"+minute);
                    }
                }, mHour[0], mMinute[0], true);


        final TaskDatabase db = Room.databaseBuilder(this,
                TaskDatabase.class, "database-task")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        beginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginTimePickerDialog.show();
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimePickerDialog.show();
            }
        });
        duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                durationPickerDialog.show();
            }
        });
        timeBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeBeforePickerDialog.show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
                Toast.makeText(getApplicationContext(), travelMode.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Task object
                Task t = new Task();
                int hour, minute, month, day, year, minuteBefore;
                String location;

                // Set title
                if(title.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Please enter a title!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    t.setTitle(title.getText().toString());
                }

                // Set location
                location = autoCompleteTextView.getText().toString();
                t.setLocation(location);

                // Set add to route
                if(addToRoute.isChecked()){ t.setCalRoute(true); }
                else { t.setCalRoute(false); }

                // Set travel mode
                t.setTravelMode(travelMode.getSelectedItem().toString().toUpperCase());

                // Set begin time
                try{
                t.setBegin_time(mHour[1]+""+mMinute[1]);}catch (Exception e){}


                // Set date
                // For now, we assume valid input. I will change to date picker later
//                month = Integer.parseInt(dateMonth.getText().toString());
//                day = Integer.parseInt(dateDay.getText().toString());
//                year = Integer.parseInt(dateYear.getText().toString());
//                t.setDate(Integer.parseInt(dateMonth.getText().toString() + dateDay.getText().toString() + dateYear.getText().toString()));
//
//                // Set notification
//                minuteBefore = Integer.parseInt(timeBeforeNotification.getText().toString());
//                if (notification_need.isChecked())
//                { createAlarm(getApplicationContext(), t.getId(), hour, minute, minuteBefore, location, "This is location"); }
////                { createAlarm(getApplicationContext(), t.getId(), month, day, year, hour, minute, minuteBefore, location, "This is location"); }

                db.taskDao().insert(t);

                AddTaskActivity.this.finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskActivity.this.finish();
            }
        });

    }


}
