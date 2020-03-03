package edu.ucsb.cs.cs48.schedoptim.ui.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class AlarmCreator {

    /*A static function that sets an alarm that will create a notification at a specified time
    *@param context -- the context calling this function. Use this.getApplicationContext()
    *@param ID -- the ID of the alarm. Using an ID of an existing alarm will override the old one. Use the Task ID for this.
    *@param hour -- the hour that the event occurs at in 24 hour notation, ex. 4 pm --> 16
    *@param minute -- the minute that the event event occurs at
    *@param loc -- the location of the event, this is additional information that will appear in the notification
    *@param desc -- the description of the task, this is additional information that will appear in the notification
    *@param time -- the amount of time in minutes before the event to alert the user, ex. if time is set to 10, and event is at 10:45, will send notification at 10:35
     */
    public static void createAlarm(Context context, int ID, int hour, int minute, String loc, String desc, int time) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);

        intent.putExtra("ID", ID);
        intent.putExtra("Time", hour + ":" + minute);
        intent.putExtra("Location", loc);
        intent.putExtra("Description", desc);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID, intent, 0);

        Calendar calendar = Calendar.getInstance();
        if(minute - 15 < 0) {
            hour = hour - 1;
            minute = minute + 45;
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }


    /*A static function that deletes any set alarms with id == ID
     *@param context -- the context calling this function. Use this.getApplicationContext()
     *@param ID -- the ID of the alarm to be deleted.
     */
    public static void cancelAlarm(Context context, int ID) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID, intent, 0);

        alarmManager.cancel(pendingIntent);
    }
}
