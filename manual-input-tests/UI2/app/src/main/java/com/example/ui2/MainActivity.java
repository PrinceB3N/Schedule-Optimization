package com.example.ui2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> addArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addArray = new ArrayList<>();
    }

    public void add(View view) {
        EditText location = findViewById(R.id.p);
        String loc = location.getText().toString();
        if (TextUtils.isEmpty(loc)) {
            location.setError("Please enter a location");
            return;
        }
        addArray.add(loc);

        StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, addArray);

        DynamicListView listView = findViewById(R.id.listView);

        listView.setList(addArray);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
