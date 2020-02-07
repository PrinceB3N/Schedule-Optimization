package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class DisplayListActivity extends AppCompatActivity {
    ArrayList<String> addArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        addArray = extras.getStringArrayList("List");
        StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, addArray);

        DynamicListView listView = findViewById(R.id.listView);

        listView.setList(addArray);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
