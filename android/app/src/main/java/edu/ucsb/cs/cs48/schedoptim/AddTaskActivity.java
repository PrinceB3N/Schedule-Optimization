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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.room.Room;

import java.security.PublicKey;
import java.util.Calendar;

import edu.ucsb.cs.cs48.schedoptim.adapter.PlaceAutoSuggestAdapter;
import petrov.kristiyan.colorpicker.ColorPicker;


import static edu.ucsb.cs.cs48.schedoptim.ui.notifications.AlarmCreator.createAlarm;

//import static edu.ucsb.cs.cs48.schedoptim.JSONUtils.getObjectFromJSON;

public class AddTaskActivity extends Activity {

    String isTask = "task";
    int taskId = -1;
    int colorNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTask = (String)savedInstanceState.get("type");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_task);

        final Switch mode = findViewById(R.id.switch_mode);
        Button cancel = findViewById(R.id.button_cancel);
        Button save = findViewById(R.id.button_add);
        // Title
        final EditText title = findViewById(R.id.textinput_title);
        // Location
        final AutoCompleteTextView textinput_location = findViewById(R.id.autocomplete);
        textinput_location.setAdapter(new PlaceAutoSuggestAdapter(AddTaskActivity.this, android.R.layout.simple_list_item_1));
        final CheckBox addToRoute = findViewById(R.id.add_to_route);
        final Spinner travelMode = findViewById(R.id.spinner_travel_mode);
        // Time
        final TextView beginTime = findViewById(R.id.textView_time_begin);
        final TextView endTime = findViewById(R.id.textView_time_end);
        final TextView duration = findViewById(R.id.textView_duration);
        // Date
        final TextView date = findViewById(R.id.textView_date);
        // Notification
        final Switch needNotification = findViewById(R.id.switch_notification);
        final TextView timeBefore = findViewById(R.id.textView_time_before);
        // Importance
        final Spinner importance = findViewById(R.id.spinner_importance);
        importance.setSelection(1);
        // Color
        final Button colorButton = findViewById(R.id.button_color);
        // Note
        final EditText note = findViewById(R.id.editText_note);

        final TaskDatabase db = Room.databaseBuilder(this,
                TaskDatabase.class, "database-task")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        Calendar ca = Calendar.getInstance();
        int ch = ca.get(Calendar.HOUR_OF_DAY);
        int cm = ca.get(Calendar.MINUTE);
        final int[] mYear = {ca.get(Calendar.YEAR)};
        final int[] mMonth = {ca.get(Calendar.MONTH)};
        final int[] mDay = {ca.get(Calendar.DAY_OF_MONTH)};
        final int[] mHour = {ch, ch + 1, 1, 0}; // begin 0; end 1; duration 2; minuteBefore 3
        final int[] mMinute = {cm, cm, 0, 30};

        beginTime.setText("Begin Time: " + String.format("%02d", mHour[0]) + ":" + String.format("%02d", mMinute[0]));
        endTime.setText("End Time: " + String.format("%02d", mHour[1]) + ":" + String.format("%02d", mMinute[1]));
        duration.setText("Duration: " + String.format("%02d", mHour[2]) + ":" + String.format("%02d", mMinute[2]));
        timeBefore.setText("Time Before: " + String.format("%02d", mHour[3]) + ":" + String.format("%02d", mMinute[3]));
        date.setText("Date: " + (mMonth[0] + 1) + "/" + mDay[0] + "/" + mYear[0]);

        if (taskId != -1) {
            Task e = db.taskDao().findById(taskId);
            if (e.getType().matches("task")) {
                mode.setChecked(false);
            } else {
                mode.setChecked(true);
            }
            title.setText(e.getTitle());
            if (!e.getLocation().matches("")) {
                textinput_location.setText(e.getLocation());
            }
            addToRoute.setChecked(e.getCalRoute());
            // TODO: set travel mode
            duration.setText("Duration: " + e.getDuration());
            if (!e.getNotiTime().matches("")) {
                needNotification.setChecked(true);
                timeBefore.setText("Time Before: " + e.getNotiTime());
            }
            // TODO:set importance
            if (!e.getNote().matches("")) {
                note.setText(e.getNote());
            }
        }


        final DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, R.style.MyDatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear[0] = year;
                        mMonth[0] = month;
                        mDay[0] = dayOfMonth;
                        date.setText("Date: " + (month + 1) + "/" + dayOfMonth + "/" + year);
                    }
                },
                mYear[0], mMonth[0], mDay[0]);

        final TimePickerDialog beginTimePickerDialog = new TimePickerDialog(AddTaskActivity.this, R.style.MyDatePickerDialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour[0] = hourOfDay;
                        mMinute[0] = minute;
                        beginTime.setText("Begin Time: " + String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                    }
                }, ch, cm, false);

        final TimePickerDialog endTimePickerDialog = new TimePickerDialog(AddTaskActivity.this, R.style.MyDatePickerDialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour[1] = hourOfDay;
                        mMinute[1] = minute;
                        endTime.setText("End Time: " + String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                    }
                }, mHour[1], mMinute[1], false);

        final TimePickerDialog durationPickerDialog = new TimePickerDialog(AddTaskActivity.this, R.style.MyDatePickerDialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour[2] = hourOfDay;
                        mMinute[2] = minute;
                        duration.setText("Duration: " + String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                    }
                }, mHour[2], mMinute[2], false);

        final TimePickerDialog timeBeforePickerDialog = new TimePickerDialog(AddTaskActivity.this, R.style.MyDatePickerDialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour[3] = hourOfDay;
                        mMinute[3] = minute;
                        timeBefore.setText("Time Before: " + String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                    }
                }, mHour[3], mMinute[3], false);

        mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isTask = "todo";
                } else {
                    isTask = "task";
                }
            }
        });


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

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker colorPicker = new ColorPicker(AddTaskActivity.this);
                colorPicker.show();
                colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        colorNumber = color;
                        colorButton.setBackgroundColor(color);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Task object
                Task t = new Task();
                String location;

                // Set type
                t.setType(isTask);

                // Set title
                if (title.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a title!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    t.setTitle(title.getText().toString());
                }

                // Set location
                location = textinput_location.getText().toString();
                t.setLocation(location);

                // Set add to route
                if (addToRoute.isChecked()) {
                    t.setCalRoute(true);
                } else {
                    t.setCalRoute(false);
                }

                // Set travel mode
                t.setTravelMode(travelMode.getSelectedItem().toString().toUpperCase());

                // Set times
                if (mHour[0] > mHour[1]) {
                    Toast.makeText(getApplicationContext(), "The task ends before begin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mHour[0] == mHour[1] && mMinute[0] > mMinute[1]) {
                    Toast.makeText(getApplicationContext(), "The task ends before it begins!", Toast.LENGTH_SHORT).show();
                    return;
                }
                t.setBegin_time(String.format("%02d", mHour[0]) + ":" + String.format("%02d", mMinute[0]));
                t.setEnd_time(String.format("%02d", mHour[1]) + ":" + String.format("%02d", mMinute[1]));
                t.setDuration(String.format("%02d", mHour[2]) + ":" + String.format("%02d", mMinute[2]));
                t.setNotiTime(String.format("%02d", mHour[3]) + ":" + String.format("%02d", mMinute[3]));

                // Set date
                t.setDate(mMonth[0] + "/" + mDay[0] + "/" + mYear[0]);

                // Set notification
                if (needNotification.isChecked()) {
                    createAlarm(getApplicationContext(), t.getId(), mHour[0], mMinute[0],
                            mHour[3] + mMinute[3], location, "This is location");
                    t.setNotiTime(String.format("%02d", mHour[3]) + ":" + String.format("%02d", mMinute[3]));
                }
//                { createAlarm(getApplicationContext(), t.getId(), mMonth[0],mDay[0],mYear[0], mHour[0], mMinute[0], mHour[3]+mMinute[3], location, "This is location"); }

                // Set importance
                t.setImportance(importance.getSelectedItem().toString());

                // Set color
                t.setColor(colorNumber);

                // Set note
                t.setNote(note.getText().toString());

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
