package edu.ucsb.cs.cs48.schedoptim;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.room.Room;
import edu.ucsb.cs.cs48.schedoptim.adapter.PlaceAutoSuggestAdapter;
import edu.ucsb.cs.cs48.schedoptim.ui.maps.MapsViewModel;

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

        Button cancel = findViewById(R.id.button_cancel);
        Button save = findViewById(R.id.button_add);

        final Switch travel_mode = findViewById(R.id.switch_travel_mode);

        final TaskDatabase db = Room.databaseBuilder(this,
                TaskDatabase.class, "database-task")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task t = new Task();

                if(title.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Please enter a title!", Toast.LENGTH_SHORT).show();
                    return;
                }
                try{ t.setBegin_time(Integer.parseInt(timeBeginHour.getText().toString())); }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Please enter a valid time (number) !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //t.setBegin_time(Integer.parseInt(timeBeginHour.getText().toString() + timeBeginMinute.getText().toString()));
                // Below is test version
                try{ t.setDate(Integer.parseInt(dateMonth.getText().toString() + dateDay.getText().toString() + dateYear.getText().toString())); }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Please enter a valid date (number) !", Toast.LENGTH_SHORT).show();
                    return;
                }

                // TODO: Integrate with map
                if(travel_mode.isChecked()){ MapsViewModel.addToRequestList(autoCompleteTextView.getText().toString(), "bicycling"); }
                else { MapsViewModel.addToRequestList(autoCompleteTextView.getText().toString(), "walking");}

                // TODO: add notification

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
