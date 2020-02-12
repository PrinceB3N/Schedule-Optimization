package edu.ucsb.cs.cs48.schedoptim;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.Arrays;
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
    private int line_color;
    private String encoded_polylines;
    private Location start;
    private Location end;
    private LatLngBounds bounds;
    private String travel_mode;
    private float length;
    private float time;

    public Route(int line_color, String encoded_polylines, Location start,
                 Location end, LatLngBounds bounds, String travel_mode, float length, float time){
        this.line_color=line_color;
        this.encoded_polylines= encoded_polylines;
        this.start=new Location(start);
        this.end=new Location(end);
        this.bounds=bounds;
        this.travel_mode=travel_mode;
        this.length=length;
        this.time=time;
    }
    public Route(Route r){
        this(r.line_color,r.encoded_polylines,r.start,r.end,r.bounds,r.travel_mode,r.length,r.time);
    }

    public String getEncoded_polylines() {
        return encoded_polylines;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public LatLngBounds getBounds() {
        return bounds;
    }

    public int getLine_color() {
        return line_color;
    }

    public List<LatLng>getDecoded_polylines(){
        return PolyUtil.decode(encoded_polylines);
    }
    public float getLength() {
        return length;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public float getTime() {
        return time;
    }

    public void setEncoded_polylines(String encoded_polylines) {
        this.encoded_polylines = encoded_polylines;
    }

    public void setStart(Location start){
        this.start=start;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

    public void setLine_color(int line_color) {
        this.line_color = line_color;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public void setTime(float time){
        this.time=time;
    }

    public boolean equals(Route r){
        if(r==null)
            return false;
        return start.equals(r.start) &&
                end.equals(r.end) &&
                travel_mode.equals(r.travel_mode);
    }
}
//TODO: file storage handler class that takes care of pathing by mapping unique ids and keeps track of how many schedules there are.
//TODO: learn about google calender, WARNING: may invalidate everything we did
//TODO: implement delete, swap, add features for locations. + if someone changed this.
public class Schedule {

    //private int id;
    private ArrayList<Location> locations;
    private ArrayList<Route> routes;
    private LatLngBounds bounds;
    private String day;

    public Schedule(ArrayList<Location> locations, ArrayList<Route> routes,
                    LatLngBounds bounds, String day){
        this.locations=new ArrayList<Location>(locations);
        this.routes=new ArrayList<Route>(routes);
        this.bounds=bounds;
        this.day=day;
    }
    //DEFAULT TEST CASE
    /*
    public Schedule(){
        //id=12345;
        locations=new ArrayList<>();
        Location l1=new Location(new LatLng(45.5017123, -73.5672184),"Toronto");
        Location l2=new Location(new LatLng(43.6532565,-79.38303979999999),"Science Center");
        locations.add(l1);
        locations.add(l2);
        LatLng corner1=new LatLng(45.5017123, -73.5672184);
        LatLng corner2=new LatLng(43.6532565,-79.38303979999999);
        LatLngBounds bounds = new LatLngBounds(corner2,corner1);
        routes=new ArrayList<Route>();
        String encoded = "vmexE`a}lYQj@QXGR?JHX{@v@c@Xa@PKBcDv@uB\\\\s@FkADMBQESEiIkAyDs@?@A@A@C@E?GEAE@C?AoBc@gDy@cD}A{E}B?@A@A@C@IACK?E@Cq@u@sAcBaDwGU]SYA@ABG?ECAI@C[a@oFiG{BeCg@]eCqAiAs@?@?@A@C@GC?Kk@c@wB{BuHwIm@m@cD_CwB{AA@A@EBEAGI@M?AqGwEy@k@qAmA{@w@[QMMC?C?AAMHMNm@pAe@pAY`@KHKDg@HqBKi@?sCh@qAf@i@ZaCfCwFfEsAcCoAwAa@a@sAkAi@y@_@m@y@mCo@gBEIOIIOWa@Yi@g@{@KEEKAOBKFKHE`AuC|@gCLi@BQFc@NDDDDDJT`@jBl@~B@V?J?K?Mo@iCa@kBGMIMEEOEGb@CPCNa@pAe@nAg@~A?RCd@@@BDDVCLGJTn@v@pARj@n@fBx@lC^l@h@x@rAjA`@`@nAvArAbCvDlInAjCdC~DPb@DZb@hElD|\\\\lBhRJ\\\\Tj@OLc@PcAHUJSPQ\\\\a@~@`At@nFpDbNhJhJlGdAp@dGiFx@s@lGoFjC}BrIqHhK}I~EeEzBsBpDaDfDx@nBb@?A@CBCD?FBBJABxDr@fIfAj@@l@Hx@EvCe@fDw@XM^SnAeAI]?KP]Xw@";
        Route r1 = new Route(Color.BLUE,encoded,l1,l2, bounds,"bicycling", 13.0f, 4.0f);
        routes.add(r1);
       LatLng map_bound1= new LatLng(44.5017123, -78.5672184);
       LatLng map_bound2 = new LatLng(43.6532565,-79.38303979999999);
       LatLngBounds.Builder build = new LatLngBounds.Builder();
       build.include(map_bound1);
       build.include(map_bound2);
       this.bounds=build.build();
        day = "Friday";
    }

     */
    public Schedule (Schedule s){
        if(s==null){
            this.locations=null;
            this.routes=null;
            this.bounds=null;
            this.day=null;
            return;
        }
        this.locations=s.locations;
        this.routes=s.routes;
        this.bounds=s.bounds;
        this.day=s.day;
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
    public LatLngBounds getBounds(){ return bounds;}
    public String getDay() {
        return day;
    }
    /*public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

     */

    public String getScheduleJSONString(){
        return objToJSONString(this);
    }

    public void testDefaultConstructorGSONReadAndWrite(String file_dir){
        this.storeSchedule(file_dir, "/test.json");
        //Checks to see if JSON file was created, found, written to, read from, and casted into Schedule successfully.
        Log.d(MainActivity.class.getName(),this.getScheduleJSONString());
    }
    public void testJSONToGSON(String file_dir, String file_path){
        this.storeSchedule(file_dir, file_path);
        Schedule test = (Schedule)getObjectFromJSON(Schedule.class,file_dir, file_path);
        //Log.d(MapsActivity.class.getName(),test.getClass().toString());
        Log.d(MainActivity.class.getName(),"TESTGETSTOREDROUTE"+test.getRoutes().get(0).getEncoded_polylines().toString());
    }
}
