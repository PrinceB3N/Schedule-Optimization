package edu.ucsb.cs.cs48.schedoptim;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

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

import edu.ucsb.cs.cs48.schedoptim.adapter.PlaceAutoSuggestAdapter;
import edu.ucsb.cs.cs48.schedoptim.ui.maps.MapsViewModel;

//import static edu.ucsb.cs.cs48.schedoptim.JSONUtils.getObjectFromJSON;

public class AddTaskActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        final AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(AddTaskActivity.this, android.R.layout.simple_list_item_1));
        final EditText travel_mode_input= (EditText) findViewById(R.id.travel_mode_input);
        Button cancel = findViewById(R.id.button_cancel);
        Button add = findViewById(R.id.button_add);
        final String default_travel_mode = "bicycling";

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!travel_mode_input.getText().toString().equals("bicycling") || !travel_mode_input.getText().toString().equals("walking")){
                    MapsViewModel.addToRequestList(autoCompleteTextView.getText().toString(),default_travel_mode);
                    AddTaskActivity.this.finish();
                }
                else {
                    MapsViewModel.addToRequestList(autoCompleteTextView.getText().toString(), travel_mode_input.getText().toString());
                    AddTaskActivity.this.finish();
                }
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
