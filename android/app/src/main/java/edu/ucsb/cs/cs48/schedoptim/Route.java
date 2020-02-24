package edu.ucsb.cs.cs48.schedoptim;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.PolyUtil;

import java.util.List;

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

    public List<LatLng> getDecoded_polylines(){
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