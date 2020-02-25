package edu.ucsb.cs.cs48.schedoptim.ui.maps;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import edu.ucsb.cs.cs48.schedoptim.JSONUtils;
import edu.ucsb.cs.cs48.schedoptim.MainActivity;
import edu.ucsb.cs.cs48.schedoptim.Route;
import edu.ucsb.cs.cs48.schedoptim.RouteDao;
import edu.ucsb.cs.cs48.schedoptim.RouteDatabase;

public class MapsViewModel extends ViewModel{
    private GoogleMap map = null;
    private RouteDatabase rdb = null;
    private static List<String> locations = new ArrayList<String>();
    private static List<String> travel_modes = new ArrayList<String>();
    private static ArrayList<Route> routes = new ArrayList<>();
    private static LatLngBounds bounds;
    public MapsViewModel(GoogleMap map, RouteDatabase rdb) {
        this.map = map;
        this.rdb = rdb;
    }
    public MapsViewModel(){

    }
    public GoogleMap getGoogleMap(){
        return this.map;
    }
    public RouteDatabase getRouteDatabase(){
        return this.rdb;
    }
    //DRAW ROUTES FUNCTIONS, CALL THESE TO UPDATE MAPS VIEW
    public void drawRoutes(List<String> locations, List<String> travel_modes){
        this.locations=locations;
        this.travel_modes=travel_modes;
        RouteDrawer rd = new RouteDrawer();
        rd.execute();
    }
    public void drawRoutes(){
        RouteDrawer rd = new RouteDrawer();
        rd.execute();
    }
    public void drawRoutes(ArrayList<Route> routes) {
        if (routes == null) {
            return;
        }

        for (int i = 0; i < routes.size(); i++) {
            //Draw routes
            map.addPolyline(new PolylineOptions()
                    .color(routes.get(i).getLine_color())
                    .width(12)
                    .clickable(false)// Able to click or not.
                    .addAll(routes.get(i).getDecoded_polylines()));
        }
    }
    public void drawMarkers(ArrayList<Route> routes) {
        if (routes == null) {
            return;
        }
        //Add starting marker
        map.addMarker(new MarkerOptions().position(new LatLng(routes.get(0).getStart_lat(), routes.get(0).getStart_long())));
        for (Route r : routes) {
            //Place location markers
            map.addMarker(new MarkerOptions().position(new LatLng(r.getEnd_lat(), r.getEnd_long())));
        }
    }
    /**
     * Method to move camera to wanted area.
     */
    public void moveCameraToWantedArea(LatLng bound1, LatLng bound2, int bound_padding) {
        final LatLng BOUND1 = bound1;
        final LatLng BOUND2 = bound2;
        final int BOUNDS_PADDING = bound_padding;
        if (map == null)
            return;
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                // Set up the bounds coordinates for the area we want the user's viewpoint to be.
                LatLngBounds bounds = new LatLngBounds.Builder()
                        .include(BOUND1)
                        .include(BOUND2)
                        .build();
                // Move the camera now.
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, BOUNDS_PADDING));//0=BOUNDS_PADDING
            }
        });

    }
    public void clearRequests(){
        locations.clear();
        travel_modes.clear();
    }
    //Location address list manipulating functions
    public static boolean addToRequestList(String location, String travel_mode) {
        if (locations.size() == 0) {
            locations.add(location);
            return true;
        }
        locations.add(location);
        travel_modes.add(travel_mode);
        return true;
    }
    //TODO
    public boolean removeFromRequestList(String location, String travel_mode) {
        return false;
    }
    //TODO
    private int findRequest(String location, String travel_mode){
        return -1;
    }
    //TODO
    public boolean swapRequestOrder(String order1, String order2) {
        if (!locations.contains(order1) || !locations.contains(order2))
            return false;
        int tmp = -1;
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).equals(order1)) {
                locations.set(i, order2);
                if (tmp == -1)
                    tmp = i + 1;
                else {
                    String s = travel_modes.get(i);
                    travel_modes.set(i, travel_modes.get(tmp));
                    travel_modes.set(tmp, s);
                    break;
                }
            } else if (locations.get(i).equals(order2)) {
                locations.set(i, order1);
                if (tmp == -1)
                    tmp = i + 1;
                else {
                    String s = travel_modes.get(i);
                    travel_modes.set(i, travel_modes.get(tmp));
                    travel_modes.set(tmp, s);
                    break;
                }
            }
        }

        return true;
    }

    public boolean swapRequestOrder(int index1, int index2) {
        if (index1 < 0 || index2 < 0 || index1 > locations.size() || index2 > locations.size())
            return false;
        String tmp = locations.get(index1);
        locations.set(index1, locations.get(index2));
        locations.set(index2, tmp);

        String tmp2 = travel_modes.get(index1 + 1);
        travel_modes.set(index1 + 1, travel_modes.get(index2 + 1));
        travel_modes.set(index2 + 1, tmp2);

        return true;
    }
    public static LatLngBounds getCameraBounds(ArrayList<Route> routes){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(Route route: routes) {
            //add corners to camera bounds builder
            builder.include(new LatLng(route.getStart_lat(), route.getStart_long()));
            builder.include(new LatLng(route.getEnd_lat(), route.getEnd_long()));
        }
        //Finally build the bounds
        return builder.build();
    }

    //INNER CLASSES
    private class RouteDrawer extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                routes = JSONUtils.getRoutesFrom(locations,travel_modes,rdb.getrouteDao());
            }catch(Exception e){
                Log.e(MainActivity.class.getName(),"Async Error",e);
            }
            return null;
        }

        protected void onPostExecute(Void voids) {
            if(routes==null){
                return;
            }
            //Clear map
            map.clear();
            //Get camera bounds
            bounds = getCameraBounds(routes);
            //Move camera
            moveCameraToWantedArea(bounds.southwest, bounds.northeast, 16);
            //draw Routes
            drawRoutes(routes);
            //draw Markers
            drawMarkers(routes);
        }
    }
}