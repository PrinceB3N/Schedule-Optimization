package edu.ucsb.cs.cs48.schedoptim;

// DatePicker: https://www.jianshu.com/p/d3744c2b480a

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
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

public class TaskViewActivity extends Activity {

    // Top bar
    ImageButton button_leave;
    TextView type;
    ImageButton button_edit;
    // Title part
    ImageView icon_title;
    TextView  text_title;
    TextView  text_importance;
    // Time part
    ImageView icon_time;
    TextView  text_time;
    // Location part
    ImageView icon_location;
    TextView  text_location;
    ImageView icon_travel_mode;
    TextView  text_travel_mode;
    // Notification part
    ImageView icon_notification;
    TextView  text_notification;
    // Note part
    ImageView icon_note;
    TextView  text_note;
    // Delete
    ImageButton button_delete;

    boolean isTask;
    boolean hasLoc;
    TaskDatabase db;
    Task t;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_task);

        // Top bar
        button_leave = findViewById(R.id.button_leave);
        type = findViewById(R.id.textView_type);
        button_edit  = findViewById(R.id.button_edit);
        // Title part
        icon_title = findViewById(R.id.imageView_title);
        text_title = findViewById(R.id.textView_view_title);
        text_importance = findViewById(R.id.textView_view_importance);
        // Time part
        icon_time = findViewById(R.id.imageView_time);
        text_time = findViewById(R.id.textView_view_time);
        // Location part
        icon_location = findViewById(R.id.imageView_location);
        text_location = findViewById(R.id.textView_view_location);
        icon_travel_mode = findViewById(R.id.imageView_travel_mode);
        text_travel_mode = findViewById(R.id.textView_view_travel_mode);
        // Notification part
        icon_notification = findViewById(R.id.imageView_notification);
        text_notification = findViewById(R.id.textView_notification);
        // Note part
        icon_note = findViewById(R.id.imageView_note);
        text_note = findViewById(R.id.textView_view_notes);
        // Delete
        button_delete = findViewById(R.id.button_delete);

        db = Room.databaseBuilder(this,
                TaskDatabase.class, "database-task")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        id = getIntent().getIntExtra("ID",-1);

        update();

        button_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), travelMode.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                TaskViewActivity.this.finish();
            }
        });

        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editTask= new Intent(getApplicationContext(), AddTaskActivity.class);
                editTask.putExtra("ID",id);
                startActivityForResult(editTask, 1);
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(TaskViewActivity.this)
                        .setTitle("Are you sure?")
                        .setMessage("Do you really want to delete this " + t.getType() + "?")
                        .setIcon(R.drawable.outline_notification_important_24)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                db.taskDao().delete(t);
                                TaskViewActivity.this.finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

    }


    private void update(){
        t = db.taskDao().findById(id);
        isTask = t.getType().matches("task");
        hasLoc = !t.getLocation().matches("");
        if (isTask){ type.setText("Task");}
        else { type.setText("To-Do");}
        text_title.setText(t.getTitle());
        text_importance.setVisibility(View.GONE);
        if (!isTask){
            String im = t.getImportance();
            text_importance.setText(im);
            if (im.matches("High")){ text_importance.setBackgroundResource(R.drawable.card_red); }
            if (im.matches("Medium")){ text_importance.setBackgroundResource(R.drawable.card_green); }
            if (im.matches("Low")){ text_importance.setBackgroundResource(R.drawable.card_blue); }
        }
        text_time.setText( t.getDate() + " · " + t.formatTaskTime(t.getBegin_time()) + " - " + t.formatTaskTime(t.getEnd_time()));

        if (!hasLoc){
            icon_location.setImageResource(R.drawable.outline_location_off_24);
            text_location.setText("No specified location");
            icon_travel_mode.setVisibility(View.GONE);
            text_travel_mode.setVisibility(View.GONE);
        }else {
            text_location.setText(t.getLocation().split(",")[0]);
            String tm = t.getTravelMode();
            if (tm.matches("WALKING")){
                icon_travel_mode.setImageResource(R.drawable.outline_directions_walk_24);
                text_travel_mode.setText(tm);
            }else if (tm.matches("BICYCLING")){
                icon_travel_mode.setImageResource(R.drawable.outline_directions_bike_24);
                text_travel_mode.setText(tm);
            }else if (tm.matches("DRIVING")){
                icon_travel_mode.setImageResource(R.drawable.outline_directions_car_24);
                text_travel_mode.setText(tm);
            }else if (tm.matches("TRANSIT")){
                icon_travel_mode.setImageResource(R.drawable.outline_directions_bus_24);
                text_travel_mode.setText(tm);
            }
        }
        if (t.getNotiTime() == null){
            icon_notification.setImageResource(R.drawable.outline_notifications_off_24);
            text_notification.setText("Don't notify");
        }else {
            text_notification.setText("Notify " + t.getNotiTime() + " minutes before");
        }

        text_note.setText(t.getNote());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            update();
        }
    }

}
