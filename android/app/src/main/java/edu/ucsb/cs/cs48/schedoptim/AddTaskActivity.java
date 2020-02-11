package edu.ucsb.cs.cs48.schedoptim;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static edu.ucsb.cs.cs48.schedoptim.JSONUtils.getObjectFromJSON;

public class AddTaskActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button cancel = findViewById(R.id.button_cancel);
        Button add = findViewById(R.id.button_add);
        final TextInputEditText input_name = findViewById(R.id.textInput_name);
        final TextInputEditText input_lat= findViewById(R.id.textInput_lat);
        final TextInputEditText input_lng = findViewById(R.id.textInput_lng);

        Intent i = getIntent();
        final String file_dir = i.getStringExtra("path");
        final String file_path = "/test.json";

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = Double.valueOf(input_lat.getText().toString());
                double lng = Double.valueOf(input_lng.getText().toString());
                Location l = new Location(new LatLng(lat, lng),input_name.getText().toString());

                Schedule s = (Schedule)getObjectFromJSON(Schedule.class,file_dir, file_path);
                //s.getLocations().add(l);
                //s.storeSchedule(file_dir, file_path);
                Intent c = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(c);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(c);
            }
        });

    }

}