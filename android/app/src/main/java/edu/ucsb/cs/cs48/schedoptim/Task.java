package edu.ucsb.cs.cs48.schedoptim;

// https://github.com/HoldMyOwn/RoomDemo
// Monitoring: https://github.com/amitshekhariitbhu/Android-Debug-Database

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
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
    private String color;

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
    public String getColor () { return color; }
    public void setColor (String color) {this.color = color; }
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

    @Ignore
    public Task(String title, String location) {
        this.title = title;
        this.location = location;
    }
    public static String formatTaskDate(int month, int day, int year){
        return month+"/"+day+"/"+year;
    }
    public static String formatTaskDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("L/d/yyyy", Locale.ENGLISH);
        return formatter.format(date);
    }
//
//    @Ignore
//    public User(int uid) {
//        this.uid = uid;
//    }
//
//    @Ignore
//    public User(int uid, String firstName, String lastName) {
//        this.uid = uid;
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//    @Ignore
//    public User(String firstName, String lastName, int age) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.age = age;
//    }
//
//    @Ignore
//    public User(int uid, String firstName, String lastName, int age) {
//        this.uid = uid;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.age = age;
//    }

}
