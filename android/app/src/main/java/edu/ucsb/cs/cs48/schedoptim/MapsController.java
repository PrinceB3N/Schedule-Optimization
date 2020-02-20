package edu.ucsb.cs.cs48.schedoptim;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsController {
    private GoogleMap map = null;
    private ScheduleDao sDao = null;
    private RouteDao rDao = null;
    private static List<String> locations = new ArrayList<String>();
    private static List<String> travel_modes = new ArrayList<String>();

    public MapsController(GoogleMap map, ScheduleDao sDao, RouteDao rDao) {
        this.map = map;
        this.sDao = sDao;
        this.rDao = rDao;
    }

    //DRAW ROUTES FUNCTIONS, CALL THESE TO UPDATE MAPS VIEW
    public void drawRoutes(List<String> locations, List<String> travel_modes){
        this.locations=locations;
        this.travel_modes=travel_modes;
        RouteDrawer rd = new RouteDrawer();
        rd.execute();
    }
    public void drawRoutes(Long schedule_id){
        RouteDrawer rd = new RouteDrawer();
        rd.execute(schedule_id);
    }
    public void drawRoutes(){
        RouteDrawer rd = new RouteDrawer();
        rd.execute();
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

    //Location address list manipulating functions
    public boolean addToRequestList(String location, String travel_mode) {
        if (locations.size() == 0) {
            locations.add(location);
            return true;
        }
        locations.add(location);
        travel_modes.add(travel_mode);

        ;
        return true;
    }

    public boolean removeFromRequestList() {
        ;
        return false;
    }

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
    /*
    public List<String> getRequestList(){
        return locations;
    }
    //Helper for storing only addresses
    private List<String> getLocationsNames(Schedule s){
        ArrayList<Location> locations = s.getLocations();
        ArrayList<String> arr= new ArrayList<>();
        for(Location l:locations){
            arr.add(l.getName());
        }
        return arr;
    }

     */
    /*
    //Helper for getting route travel_modes
    private List<String> getTravelModes(Schedule s){
        ArrayList<String> travel= new ArrayList<>();
        for(Route r:s.getRoutes()){
            travel.add(r.getTravel_mode());
        }
        return travel;
    }

     */

    //INNER CLASSES
    private class RouteDrawer extends AsyncTask<Long, Void, Schedule> {

        @Override
        protected Schedule doInBackground(Long... longs) {
            try {
                return sDao.findByScheduleId(longs[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                return JSONUtils.getScheduleFromLocations(locations,travel_modes,rDao,sDao);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Schedule result) {
            if (result == null) {
                Log.d(MainActivity.class.getName(), "SCHEDULE IS NULL IN DRAW");
                return;
            }
            List<Route> routes = rDao.findRoutesForSchedule(result.getId());
            //Add starting marker
            map.addMarker(new MarkerOptions().position(new LatLng(routes.get(0).getStart_lat(), routes.get(0).getStart_long())));

            for (int i = 0; i < routes.size(); i++) {
                //Draw routes
                map.addPolyline(new PolylineOptions()
                        .color(routes.get(i).getLine_color())
                        .width(12)
                        .clickable(false)// Able to click or not.
                        .addAll(routes.get(i).getDecoded_polylines()));
                //Place location markers
                map.addMarker(new MarkerOptions().position(new LatLng(routes.get(i).getEnd_lat(), routes.get(i).getEnd_long())));
            }
            //move camera
            moveCameraToWantedArea(new LatLng(result.getSwlat(), result.getSwlong()), new LatLng(result.getNelat(), result.getNelong()), 16);
        }
    }
}




