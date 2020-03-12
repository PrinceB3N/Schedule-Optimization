package edu.ucsb.cs.cs48.schedoptim;

// https://github.com/HoldMyOwn/RoomDemo
// Monitoring: https://github.com/amitshekhariitbhu/Android-Debug-Database

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


@Entity(tableName = "task", indices = {})
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "type")
    private String type; // class

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "begin_time")
    private String begin_time;

    @ColumnInfo(name = "end_time")
    private String end_time;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "color")
    private int color;

    @ColumnInfo(name = "importance")
    private String importance;

    @ColumnInfo(name = "notification")
    private String notiTime;

    @ColumnInfo(name = "calRoute")
    private boolean calRoute;

    @ColumnInfo(name = "travelMode")
    private String travelMode;

    @ColumnInfo(name = "duration")
    private String duration;

    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.

    public Task() {}

    public int getId () { return id; }
    public void setId (int id) { this.id = id; }
    public String getTitle () { return title; }
    public void setTitle (String title) { this.title = title; }
    public String getType () { return type; }
    public void setType (String type) { this.type = type; }
    public String getLocation () { return location; }
    public void setLocation (String location) { this.location = location; }
    public String getBegin_time () { return begin_time; }
    public void setBegin_time (String begin_time) { this.begin_time = begin_time; }
    public String getEnd_time () { return  end_time; }
    public void setEnd_time (String end_time) { this.end_time = end_time; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getNote () { return note; }
    public void setNote (String note) {this.note = note; }
    public int getColor () { return color; }
    public void setColor (int color) {this.color = color; }
    public String getImportance () { return  importance; }
    public void setImportance (String importance) { this.importance = importance; }
    public String getNotiTime () { return  notiTime; }
    public void setNotiTime (String time) { this.notiTime = time; }
    public boolean getCalRoute () { return  calRoute; }
    public void setCalRoute (boolean calRoute) { this.calRoute = calRoute; }
    public String getTravelMode () { return travelMode; }
    public void setTravelMode (String travelMode) {this.travelMode = travelMode; }
    public String getDuration() {return this.duration; }
    public void setDuration(String d) {this.duration = d;}

    public int getDuration_int() {
        String durString = getDuration();
        return 44; //place holder;
    }
    public void setDuration_int(int time) {
        //place holder
    }
    public int getBegin_time_int() {
        return 44; //place holder;
    }
    public void setBegin_time_int(int time) {
        //place holder
    }
    public int getEnd_time_int() {
        return 44; //place holder
    }
    public void setEnd_time_int(int time) {
        //place holder
    }


    @Ignore
    public Task(String title, String location) {
        this.title = title;
        this.location = location;
    }
    public static String formatTaskDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("L/d/yyyy", Locale.ENGLISH);
        String format = formatter.format(date);
        int tmp = Integer.parseInt(format.split("/")[0]);
        return tmp+format.substring(format.indexOf("/"));
    }
    //Expects hh:mm format
    public static String formatTaskTime(String time) {
        String[] split = time.split(":");
        String mins = split[1];
        int hours = Integer.parseInt(split[0]);
        if (hours == 0) {
            return "12:" + mins + " AM";
        } else if (hours < 12) {
            return hours + ":" + mins + " AM";
        } else if (hours == 12) {
            return "12:" + mins + " PM";
        } else if (hours > 12) {
            int pm_hours = hours - 12;
            return pm_hours + ":" + mins + " PM";
        }
        return "ERROR";
    }
    public static boolean isSameDate(Calendar c1, Calendar c2){
        if(c1.get(c1.DAY_OF_MONTH)==c2.get(c2.DAY_OF_MONTH) && c1.get(c1.MONTH)==c2.get(c2.MONTH) && c1.get(c1.YEAR)==c2.get(c2.YEAR))
            return true;
        else
            return false;
        //calendar.get(Calendar.YEAR)
    }


}
