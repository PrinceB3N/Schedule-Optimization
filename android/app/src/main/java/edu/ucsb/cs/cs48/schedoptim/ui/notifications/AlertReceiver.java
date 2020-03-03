package edu.ucsb.cs.cs48.schedoptim.ui.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);

        int ID = intent.getIntExtra("ID", 1);
        String time = intent.getStringExtra("Time");
        String loc = intent.getStringExtra("Location");
        String desc = intent.getStringExtra("Description");

        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(time, loc, desc);
        notificationHelper.getManager().notify(ID, nb.build());
    }
}