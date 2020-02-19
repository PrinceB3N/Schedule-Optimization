package com.example.notificationservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextInput;
    private TimePicker editTimeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = findViewById(R.id.edit_text_input);
        editTimeInput = findViewById(R.id.time_picker);
    }

    public void enqueueWork(View v) {
        String stringInput = editTextInput.getText().toString();
        int hour = editTimeInput.getCurrentHour();
        int minute = editTimeInput.getCurrentMinute();

        Intent serviceIntent = new Intent(this, ExampleJobIntentService.class);
        serviceIntent.putExtra("inputText", stringInput);
        serviceIntent.putExtra("inputHour", hour);
        serviceIntent.putExtra("inputMinute", minute);

        ExampleJobIntentService.enqueueWork(this, serviceIntent);
    }

    public void startService(View v) {
        String stringInput = editTextInput.getText().toString();
        int hour = editTimeInput.getCurrentHour();
        int minute = editTimeInput.getCurrentMinute();

        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputText", stringInput);
        serviceIntent.putExtra("inputHour", hour);
        serviceIntent.putExtra("inputMinute", minute);

        startService(serviceIntent);
    }

    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }
}
