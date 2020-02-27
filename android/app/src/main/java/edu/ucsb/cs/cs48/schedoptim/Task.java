package edu.ucsb.cs.cs48.schedoptim;

// https://github.com/HoldMyOwn/RoomDemo

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "task", indices = {})
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "begin_time")
    private int begin_time;

    @ColumnInfo(name = "end_time")
    private int end_time;

    @ColumnInfo(name = "days")
    private String days;

    @ColumnInfo(name = "note")
    private String note;

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
    public int getBegin_time () { return begin_time; }
    public void setBegin_time (int begin_time) { this.begin_time = begin_time; }
    public int getEnd_time () { return  end_time; }
    public void setEnd_time (int end_time) { this.end_time = end_time; }
    public String getDays () { return days; }
    public void setDays (String days) { this.days = days; }
    public String getNote () { return note; }
    public void setNote (String note) {this.note = note; }

    @Ignore
    public Task(String title, String location) {
        this.title = title;
        this.location = location;
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
    @Override
    public String toString() {
        return "\n" + "Task{ " +
                "id = " + id +
                ", title = " + title  +
                ", type = " + type  +
                ", location = " + location +
                ", begin_time = " + begin_time +
                ", end_time = " + end_time +
                ", days = " + days +
                ", note = " + note +
                " }";
    }

}
