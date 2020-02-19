package com.example.notificationservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import static androidx.core.app.NotificationCompat.PRIORITY_MAX;
import static com.example.notificationservice.App.CHANNEL_ID;


public class ExampleJobIntentService extends JobIntentService {
    private static final String TAG = "ExampleJobIntentService";

    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, ExampleJobIntentService.class, 123, work);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String input = intent.getStringExtra("inputText");
        int hour = intent.getIntExtra("inputHour", 0);
        int minute = intent.getIntExtra("inputMinute", 0);

        int currentHour = Calendar.getInstance().getTime().getHours();
        int currentMinute = Calendar.getInstance().getTime().getMinutes();
        Log.d(TAG, Integer.toString(currentHour));
        Log.d(TAG, Integer.toString(currentMinute));
        int waitHour = hour - currentHour;
        int waitMinute = minute - currentMinute;
        int totalTime = waitHour * 3600 + waitMinute * 60;
        int sleepTime = totalTime * 1000;
        //SystemClock.sleep(sleepTime);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(input)
                .setContentText(hour + ":" + minute)
                .setSmallIcon(R.drawable.ic_android)
                .setAutoCancel(true)
                .setPriority(PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .build();

        Log.d(TAG, "onHandleWork");
        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG, "onStopCurrentWork");
        return super.onStopCurrentWork();
    }
}