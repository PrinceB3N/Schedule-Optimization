package edu.ucsb.cs.cs48.schedoptim;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.room.Room;
import edu.ucsb.cs.cs48.schedoptim.adapter.PlaceAutoSuggestAdapter;
import edu.ucsb.cs.cs48.schedoptim.ui.maps.MapsViewModel;

import static edu.ucsb.cs.cs48.schedoptim.ui.notifications.AlarmCreator.createAlarm;

//import static edu.ucsb.cs.cs48.schedoptim.JSONUtils.getObjectFromJSON;

public class AddTaskActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_task);

        final AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(AddTaskActivity.this, android.R.layout.simple_list_item_1));

        final EditText title = findViewById(R.id.textinput_title);
        final EditText timeBeginHour = findViewById(R.id.time_begin_hour);
        final EditText timeBeginMinute = findViewById(R.id.time_begin_minute);
        final EditText dateMonth = findViewById(R.id.date_month);
        final EditText dateDay = findViewById(R.id.date_day);
        final EditText dateYear = findViewById(R.id.date_year);
        final EditText timeBeforeNotification = findViewById(R.id.time_notification_minute);


        Button cancel = findViewById(R.id.button_cancel);
        Button save = findViewById(R.id.button_add);

        final Switch travel_mode = findViewById(R.id.switch_travel_mode);
        final Switch notification_need = findViewById(R.id.switch_notification);

        final CheckBox addToRoute = findViewById(R.id.add_to_route);


        final TaskDatabase db = Room.databaseBuilder(this,
                TaskDatabase.class, "database-task")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();


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
                }

                // Set location
                location = autoCompleteTextView.getText().toString();
                t.setLocation(location);

                // Set travel mode TODO: Integrate with map
                if(travel_mode.isChecked()){
                    t.setTravelMode("bicycling");
                    MapsViewModel.addToRequestList(autoCompleteTextView.getText().toString(), "bicycling");
                }
                else {
                    t.setTravelMode("walking");
                    MapsViewModel.addToRequestList(autoCompleteTextView.getText().toString(), "walking");
                }

                // Set add to route
                if(addToRoute.isChecked()){ t.setCalRoute(true); }
                else { t.setCalRoute(false); }

                // Set begin time
                // For now, we assume valid input. I will change to time picker later
                hour = Integer.parseInt(timeBeginHour.getText().toString());
                minute = Integer.parseInt(timeBeginMinute.getText().toString());
                t.setBegin_time(Integer.parseInt(timeBeginHour.getText().toString() + timeBeginMinute.getText().toString()));


                // Set date
                // For now, we assume valid input. I will change to date picker later
                month = Integer.parseInt(dateMonth.getText().toString());
                day = Integer.parseInt(dateDay.getText().toString());
                year = Integer.parseInt(dateYear.getText().toString());
                t.setDate(Integer.parseInt(dateMonth.getText().toString() + dateDay.getText().toString() + dateYear.getText().toString()));

                // Set notification
                minuteBefore = Integer.parseInt(timeBeforeNotification.getText().toString());
                if (notification_need.isChecked())
                { createAlarm(getApplicationContext(), t.getId(), hour, minute, minuteBefore, location, "This is location"); }
//                { createAlarm(getApplicationContext(), t.getId(), month, day, year, hour, minute, minuteBefore, location, "This is location"); }

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
