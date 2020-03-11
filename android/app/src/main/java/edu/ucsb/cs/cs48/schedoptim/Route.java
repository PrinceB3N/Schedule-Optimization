package edu.ucsb.cs.cs48.schedoptim;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.PolyUtil;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "route", indices = {})
public class Route{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "route_id")
    private long route_id;
    @ColumnInfo(name = "line_color")
    private int line_color;
    @ColumnInfo(name = "encoded_polylines")
    private String encoded_polylines;
    @ColumnInfo(name = "start_lat")
    private double start_lat;
    @ColumnInfo(name = "start_long")
    private double start_long;
    @ColumnInfo (name = "start_address")
    private String start_address;
    @ColumnInfo(name = "end_lat")
    private double end_lat;
    @ColumnInfo(name = "end_long")
    private double end_long;
    @ColumnInfo (name = "end_address")
    private String end_address;
    @ColumnInfo(name = "travel_mode")
    private String travel_mode;
    @ColumnInfo(name = "length")
    private float length;
    @ColumnInfo(name = "time")
    private float time;

    public Route(){

    }
    @Ignore
    public Route(String start_address, String end_address,String travel_mode, float length, float time){
        this.start_address=start_address;
        this.end_address=end_address;
        this.travel_mode=travel_mode;
        this.length=length;
        this.time=time;
    }
    @Ignore
    public Route(int line_color, String encoded_polylines, LatLng start,
                 String start_address, LatLng end, String end_address, String travel_mode, float length, float time){
        this.line_color=line_color;
        this.encoded_polylines= encoded_polylines;
        this.start_lat=start.latitude;
        this.start_long=start.longitude;
        this.start_address=start_address;
        this.end_lat=end.latitude;
        this.end_long=end.longitude;
        this.end_address=end_address;
        this.travel_mode=travel_mode;
        this.length=length;
        this.time=time;
    }
    @Ignore
    public Route(Route r){
        this.line_color=r.line_color;
        this.encoded_polylines= r.encoded_polylines;
        this.start_lat=r.start_lat;
        this.start_long=r.start_long;
        this.start_address=r.start_address;
        this.end_lat=r.end_lat;
        this.end_long=r.end_long;
        this.end_address=r.end_address;
        this.travel_mode=r.travel_mode;
        this.length=r.length;
        this.time=r.time;
    }

    public long getRoute_id() { return route_id; }
    public String getEncoded_polylines() {
        return encoded_polylines;
    }
    public double getStart_lat() { return start_lat; }
    public double getStart_long() { return start_long; }
    public String getStart_address() { return start_address; }
    public double getEnd_lat() { return end_lat; }
    public double getEnd_long() { return end_long; }
    public String getEnd_address() { return end_address; }
    public int getLine_color() {
        return line_color;
    }
    public List<LatLng> getDecoded_polylines(){ return PolyUtil.decode(encoded_polylines); }
    public float getLength() {
        return length;
    }
    public String getTravel_mode() {
        return travel_mode;
    }
    public float getTime() {
        return time;
    }
    public String getFormattedTime(){
        long rounded_time = (long) time;
        long secs_in_min=60;
        long secs_in_hour=3600;
        if(secs_in_min>rounded_time){
            return Float.toString(rounded_time)+" secs";
        }
        else if(secs_in_hour>rounded_time){
            return Float.toString(Math.round(rounded_time/secs_in_min))+" mins";
        }
        else{
            String hours = Float.toString(Math.round(rounded_time/secs_in_hour))+" hrs";
            long extra_mins = (rounded_time%secs_in_hour)/secs_in_min;
            if(extra_mins!=0){
                return hours + " "+ extra_mins +" mins";
            }
            else{
                return hours;
            }
        }
    }
    public String getFormattedLength(){
        float meters_in_mile = 1609.34f;
        if(161f>length){
            return length +" meters";
        }
        else{
            return Math.round(length*100.0 / meters_in_mile)/100.0 + "miles";
        }
    }
    //Formats a travel mode string into correct output, otherwise default to driving
    public static String formatTravelModeForURL(String travel_mode){
        String mode=travel_mode.toLowerCase();
        switch(mode) {
            case "walking":
            case "bicycling":
            case "transit":
            case "driving":
                return mode;
            default:
                return "walking";
        }
    }

    public void setRoute_id(long route_id) { this.route_id = route_id; }
    public void setEncoded_polylines(String encoded_polylines) { this.encoded_polylines = encoded_polylines; }
    public void setStart_lat(double start_lat) { this.start_lat = start_lat; }
    public void setStart_long(double start_long) { this.start_long = start_long; }
    public void setStart_address(String start_address) { this.start_address= start_address; }
    public void setEnd_lat(double end_lat) { this.end_lat = end_lat; }
    public void setEnd_long(double end_long) { this.end_long = end_long; }
    public void setEnd_address(String end_address) { this.end_address = end_address; }
    public void setTravel_mode(String travel_mode) { this.travel_mode = travel_mode; }
    public void setLine_color(int line_color) { this.line_color = line_color; }
    public void setLength(float length) { this.length = length; }
    public void setTime(float time){
        this.time=time;
    }
}
