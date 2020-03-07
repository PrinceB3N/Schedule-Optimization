package edu.ucsb.cs.cs48.schedoptim;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import edu.ucsb.cs.cs48.schedoptim.adapter.PlaceAutoSuggestAdapter;
import edu.ucsb.cs.cs48.schedoptim.ui.maps.MapsViewModel;

public class TaskViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        final AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(TaskViewActivity.this, android.R.layout.simple_list_item_1));
        final EditText travel_mode_input= findViewById(R.id.travel_mode_input);
        Button cancel = findViewById(R.id.button_cancel);
        Button add = findViewById(R.id.button_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsViewModel.addToRequestList(autoCompleteTextView.getText().toString(), travel_mode_input.getText().toString());
                TaskViewActivity.this.finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskViewActivity.this.finish();
            }
        });

    }
}
