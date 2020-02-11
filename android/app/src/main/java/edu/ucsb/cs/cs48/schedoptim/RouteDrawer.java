package edu.ucsb.cs.cs48.schedoptim;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class RouteDrawer extends AsyncTask<Void,Void,Schedule> {
    private List<String> locations=null;
    private List<String> travel_modes=null;
    private GoogleMap map;

    private String dir;
    private String path;
    RouteDrawer(List<String> locations, List<String> travel_modes, GoogleMap map){
        this.locations=locations;
        this.travel_modes=travel_modes;
        this.map=map;
    }
    RouteDrawer(String dir, String path, GoogleMap map){
        this.dir=dir;
        this.path=path;
    }
    @Override
    protected Schedule doInBackground(Void...voids){
        if(locations==null || travel_modes==null)
            return new Schedule(dir, path);
        try {
            return JSONUtils.getScheduleFromLocations(locations, travel_modes);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(Schedule result){
        //draw all routes
        for(Route r:result.getRoutes()){
            map.addPolyline(new PolylineOptions()
                    .color(r.getLine_color())
                    .width(12)
                    .clickable(false)// Able to click or not.
                    .addAll(r.getDecoded_polylines()));
        }

    }

}
