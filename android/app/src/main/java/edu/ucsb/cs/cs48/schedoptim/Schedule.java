package edu.ucsb.cs.cs48.schedoptim;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static edu.ucsb.cs.cs48.schedoptim.JSONUtils.getObjectFromJSON;
import static edu.ucsb.cs.cs48.schedoptim.JSONUtils.objToJSONString;
import static edu.ucsb.cs.cs48.schedoptim.JSONUtils.storeObjectAsJSON;

class Location{
    private LatLng location;
    private String name;

    public Location(LatLng l, String n){
        this.location=l;
        this.name=n;
    }
    public Location(Location l){
        this(l.location,l.name);
    }
    public boolean equals(Location l1) {
        if(l1==null)
            return false;
        return this.location.equals(l1.location)
                && this.name.equals(l1.name);
    }
    public LatLng getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Route{
    private String line_color;
    private List<LatLng> polylines;
    private Location start;
    private Location end;
    private String travel_mode;
    private float length;
    private float time;
    boolean needsChange=false;
    //private Alarm alarm

    public Route(String line_color, List<LatLng> polylines, Location start,
                 Location end, String travel_mode, float length, float time){
        this.line_color=line_color;
        this.polylines= new ArrayList<LatLng>(polylines);
        this.start=new Location(start);
        this.end=new Location(end);
        this.travel_mode=travel_mode;
        this.length=length;
        this.time=time;
    }
    public Route(Route r){
        this(r.line_color,r.polylines,r.start,r.end,r.travel_mode,r.length,r.time);
    }

    public List<LatLng> getPolylines() {
        return polylines;
    }

    public boolean equals(Route r){
        if(r==null)
            return false;
        return start.equals(r.start) &&
                end.equals(r.end) &&
                travel_mode.equals(r.travel_mode);
    }
}
public class Schedule {

    private ArrayList<Location> locations;
    private ArrayList<Route> routes;
    private LatLng map_bound1;
    private LatLng map_bound2;
    private String day;

    public Schedule(ArrayList<Location> locations, ArrayList<Route> routes,
                    LatLng map_bound1,LatLng map_bound2, String day){
        this.locations=new ArrayList<Location>(locations);
        this.routes=new ArrayList<Route>(routes);
        this.map_bound1=map_bound1;
        this.map_bound2=map_bound2;
        this.day=day;
    }
    //DEFAULT TEST CASE
    public Schedule(){
        locations=new ArrayList<>();
        Location l1=new Location(new LatLng(45.5017123, -73.5672184),"Toronto");
        Location l2=new Location(new LatLng(43.6532565,-79.38303979999999),"Science Center");
        locations.add(l1);
        locations.add(l2);

        routes=new ArrayList<Route>();
        ArrayList<LatLng> polylines = new ArrayList<>();
        polylines.addAll(PolyUtil.decode("vmexE`a}lYQj@QXGR?JHX{@v@c@Xa@PKBcDv@uB\\\\s@FkADMBQESEiIkAyDs@?@A@A@C@E?GEAE@C?AoBc@gDy@cD}A{E}B?@A@A@C@IACK?E@Cq@u@sAcBaDwGU]SYA@ABG?ECAI@C[a@oFiG{BeCg@]eCqAiAs@?@?@A@C@GC?Kk@c@wB{BuHwIm@m@cD_CwB{AA@A@EBEAGI@M?AqGwEy@k@qAmA{@w@[QMMC?C?AAMHMNm@pAe@pAY`@KHKDg@HqBKi@?sCh@qAf@i@ZaCfCwFfEsAcCoAwAa@a@sAkAi@y@_@m@y@mCo@gBEIOIIOWa@Yi@g@{@KEEKAOBKFKHE`AuC|@gCLi@BQFc@NDDDDDJT`@jBl@~B@V?J?K?Mo@iCa@kBGMIMEEOEGb@CPCNa@pAe@nAg@~A?RCd@@@BDDVCLGJTn@v@pARj@n@fBx@lC^l@h@x@rAjA`@`@nAvArAbCvDlInAjCdC~DPb@DZb@hElD|\\\\lBhRJ\\\\Tj@OLc@PcAHUJSPQ\\\\a@~@`At@nFpDbNhJhJlGdAp@dGiFx@s@lGoFjC}BrIqHhK}I~EeEzBsBpDaDfDx@nBb@?A@CBCD?FBBJABxDr@fIfAj@@l@Hx@EvCe@fDw@XM^SnAeAI]?KP]Xw@"
                ));
        Route r1 = new Route("BLUE",polylines,l1,l2, "bicycling", 13.0f, 4.0f);
        routes.add(r1);
        map_bound1= new LatLng(44.5017123, -78.5672184);
        map_bound2 = new LatLng(43.6532565,-79.38303979999999);
        day = "Friday";
    }
    public Schedule (Schedule s){
        this(s.locations, s.routes, s.map_bound1, s.map_bound2, s.day);
    }
    public Schedule(String file_dir, String json_path){
        this((Schedule)getObjectFromJSON(Schedule.class,file_dir,json_path));
    }
    public void storeSchedule(String file_dir, String json_path){
        storeObjectAsJSON(this,file_dir,json_path);
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }
    public ArrayList<Route> getRoutes() {
        return routes;
    }
    public LatLng getMap_bound1() {
        return map_bound1;
    }
    public LatLng getMap_bound2() {
        return map_bound2;
    }
    public String getDay() {
        return day;
    }

    public void setMap_bound1(LatLng map_bound1) {
        this.map_bound1 = map_bound1;
    }
    public void setMap_bound2(LatLng map_bound2) {
        this.map_bound2 = map_bound2;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public String getScheduleJSONString(){
        return objToJSONString(this);
    }

    public void testDefaultConstructorGSONReadAndWrite(String file_dir){
        this.storeSchedule(file_dir, "/test.json");
        //Checks to see if JSON file was created, found, written to, read from, and casted into Schedule successfully.
        Log.d(MapsActivity.class.getName(),this.getScheduleJSONString());
    }
    public void testJSONToGSON(String file_dir, String file_path){
        this.storeSchedule(file_dir, file_path);
        Schedule test = (Schedule)getObjectFromJSON(Schedule.class,file_dir, file_path);
        //Log.d(MapsActivity.class.getName(),test.getClass().toString());
        Log.d(MapsActivity.class.getName(),"TESTGETSTOREDROUTE"+test.getRoutes().get(0).getPolylines().toString());
    }
}
