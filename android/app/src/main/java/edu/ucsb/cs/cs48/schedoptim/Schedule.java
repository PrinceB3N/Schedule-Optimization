package edu.ucsb.cs.cs48.schedoptim;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Schedule {
    private class Location{
        private LatLng location;
        private String name;

        public boolean equals(Location l1) {
            if(l1==null)
                return false;
            return this.location.equals(l1.location)
                    && this.name.equals(l1.name);
        }
    }
    private class Route{
        private String line_color;
        private ArrayList<String> polylines;
        private Location start;
        private Location end;
        private String travel_mode;
        private float length;
        private float time;
        //private Alarm alarm

        public boolean equals(Route r){
            if(r==null)
                return false;
            return start.equals(r.start) &&
                    end.equals(r.end) &&
                    travel_mode.equals(r.travel_mode);
        }
    }
    private ArrayList<Location> locations;
    private ArrayList<Route> routes;
    private LatLng map_bound1;
    private LatLng map_bound2;

    public void update(Schedule s){
        if(s==null)
            return;
        this.routes=s.routes;
    }

}
