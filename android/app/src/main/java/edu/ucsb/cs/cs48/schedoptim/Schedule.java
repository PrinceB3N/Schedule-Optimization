package edu.ucsb.cs.cs48.schedoptim;

import android.graphics.Color;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static edu.ucsb.cs.cs48.schedoptim.JSONUtils.getObjectFromJSON;
//import static edu.ucsb.cs.cs48.schedoptim.JSONUtils.objToJSONString;
//import static edu.ucsb.cs.cs48.schedoptim.JSONUtils.storeObjectAsJSON;

@Entity(tableName = "schedule", indices = {@Index(value = {"id"}, unique = true)})
public class Schedule {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "swlat")
    private double swlat;
    @ColumnInfo(name = "swlong")
    private double swlong;
    @ColumnInfo(name = "nelat")
    private double nelat;
    @ColumnInfo(name = "nelong")
    private double nelong;
    @ColumnInfo(name = "day")
    private String day;

    public Schedule(){
        
    }
    @Ignore
    public Schedule(LatLngBounds bounds, String day){
        this.swlat=bounds.southwest.latitude;
        this.swlong=bounds.southwest.longitude;
        this.nelat=bounds.northeast.latitude;
        this.nelong=bounds.northeast.longitude;
        this.day=day;
    }
    @Ignore
    public Schedule (Schedule s){
        if(s==null){
            this.swlat=0.0;
            this.swlong=0.0;
            this.nelat=0.0;
            this.nelong=0.0;            
            this.day=null;
            return;
        }
        this.swlat=0.0;
        this.swlong=0.0;
        this.nelat=0.0;
        this.nelong=0.0;
        this.day=s.day;
    }

    public long getId() {
        return id;
    }
    public double getSwlat() { return swlat; }
    public double getSwlong() {
        return swlong;
    }
    public double getNelat() { return nelat; }
    public double getNelong() {
        return nelong;
    }
    public String getDay() { return day; }

    public void setId(long id) {
        this.id = id;
    }
    public void setSwlat(double swlat) {
        this.swlat = swlat;
    }
    public void setSwlong(double swlong) {
        this.swlong = swlong;
    }
    public void setNelat(double nelat) {
        this.nelat = nelat;
    }
    public void setNelong(double nelong) {
        this.nelong = nelong;
    }
    public void setDay(String day) {
        this.day = day;
    }
        /*
    @Ignore
    public Schedule(String file_dir, String json_path){
        this((Schedule)getObjectFromJSON(Schedule.class,file_dir,json_path));
    }

     */
/*
    @Ignore
    public void storeSchedule(String file_dir, String json_path){
        storeObjectAsJSON(this,file_dir,json_path);
    }
    @Ignore
    public String getScheduleJSONString(){
        return objToJSONString(this);
    }
    @Ignore
    public void testDefaultConstructorGSONReadAndWrite(String file_dir){
        this.storeSchedule(file_dir, "/test.json");
        //Checks to see if JSON file was created, found, written to, read from, and casted into Schedule successfully.
        Log.d(MainActivity.class.getName(),this.getScheduleJSONString());
    }

 */
    /*
    public void testJSONToGSON(String file_dir, String file_path){
        this.storeSchedule(file_dir, file_path);
        Schedule test = (Schedule)getObjectFromJSON(Schedule.class,file_dir, file_path);
        Log.d(MainActivity.class.getName(),"TESTGETSTOREDROUTE"+test.getRoutes().get(0).getEncoded_polylines().toString());
    }
    
     */
}
