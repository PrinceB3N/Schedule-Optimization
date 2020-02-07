package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> addArray;
    int numWP = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
/*
    public void addLoc(View view) {
        numWP++;
        ConstraintLayout rootView = findViewById(R.id.rootLayout);

        EditText myEditText = new EditText(rootView.getContext());
        myEditText.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        myEditText.setHint("Waypoint "+numWP);
        myEditText.setId(numWP);
        rootView.addView(myEditText);
        fixSpaces();
    }*/

    public void send(View view) {
        addArray = new ArrayList<>();
        EditText locationA = findViewById(R.id.p0);
        EditText locationB = findViewById(R.id.p1);
        String locA = locationA.getText().toString();
        String locB = locationB.getText().toString();
        addArray.add(locA);
        addArray.add(locB);

        Bundle extras = new Bundle();
        extras.putStringArrayList("List", addArray);
        Intent intent = new Intent(this, DisplayListActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
/*
    public void fixSpaces() {
        ConstraintLayout rootView = findViewById(R.id.rootLayout);

        ConstraintSet set = new ConstraintSet();
        set.clone(rootView);

        set.connect(R.id.numWP,ConstraintSet.TOP,R.id.p0, ConstraintSet.BOTTOM,16);
        set.applyTo(rootView);
    }*/
}
