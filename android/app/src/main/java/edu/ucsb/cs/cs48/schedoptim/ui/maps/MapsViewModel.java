package edu.ucsb.cs.cs48.schedoptim.ui.maps;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;

//import org.mortbay.jetty.Main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import edu.ucsb.cs.cs48.schedoptim.JSONUtils;
import edu.ucsb.cs.cs48.schedoptim.MainActivity;
import edu.ucsb.cs.cs48.schedoptim.Route;
import edu.ucsb.cs.cs48.schedoptim.RouteDao;
import edu.ucsb.cs.cs48.schedoptim.RouteDatabase;
import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;
import edu.ucsb.cs.cs48.schedoptim.ui.calendar.day.DayFragment;
import edu.ucsb.cs.cs48.schedoptim.ui.calendar.day.DayViewModel;
import edu.ucsb.cs.cs48.schedoptim.ui.calendar.todo.TodoViewModel;

public class MapsViewModel extends ViewModel{
    private final static int BOUNDS_PADDING = 64;
    private static GoogleMap map = null;
    private RouteDatabase rdb = null;
    private TaskDatabase tdb = null;
    private static IconGenerator iconGenerator;
    private static List<String> locations = new ArrayList<String>();
    private static List<String> travel_modes = new ArrayList<String>();
    private static MutableLiveData<ArrayList<Route>> routes = new MutableLiveData(new ArrayList<Route>(0));
    private static LatLngBounds bounds;

    public MapsViewModel(RouteDatabase rdb, TaskDatabase tdb, IconGenerator iconGenerator) {
        this.rdb = rdb;
        this.tdb=tdb;
        MapsViewModel.iconGenerator =iconGenerator;
    }
    public MapsViewModel(){
    }
    public GoogleMap getGoogleMap(){
        return map;
    }
    public RouteDatabase getRouteDatabase(){
        return this.rdb; }
    public MutableLiveData<ArrayList<Route>> getObservableRoutes(){
        if(!routes.getValue().isEmpty()) {
            Log.d(MainActivity.class.getName(), "ROUTEVAL:" + routes.getValue().get(0).getStart_address());
        }
        return routes;
    }
    //TODO:
    public void loadLocationsAndTravelModesFromDatabase(){
        /*List<Task> tasks = taskDatabase.taskDao().getTaskByDate(MainActivity.cal.getDate());
        ArrayList<String> twr = new ArrayList<>();
        ArrayList<String> tm  = new ArrayList<>();

        for (int i = 0; i < tmp_tasks.size(); i++) {
            Task tem = tmp_tasks.get(i);
            if (tem.getCalRoute()){
                twr.add(tem.getLocation());
                tm.add(tem.getTravelMode());
            }
        }
        tasks.setValue(tmp_tasks);
        locations.setValue(twr);
        travel_modes.setValue(tm);

         */
    }

    public void setMap(GoogleMap map){ MapsViewModel.map =map; }
    public void setRdb(RouteDatabase rdb){ this.rdb=rdb; }
    public void setTdb(TaskDatabase tdb) { this.tdb=tdb; }
    public void setIconGenerator(IconGenerator iconGenerator) { MapsViewModel.iconGenerator =iconGenerator;}
    //DRAW ROUTES FUNCTIONS, CALL THESE TO UPDATE MAPS VIEW
    public void drawRoutes(List<String> locations, List<String> travel_modes){
        MapsViewModel.locations =locations;
        MapsViewModel.travel_modes =travel_modes;
        RouteDrawer rd = new RouteDrawer();
        rd.execute();
    }
    public void drawRoutes(){
        RouteDrawer rd = new RouteDrawer();
        rd.execute();
    }
    public static void drawRoutes(ArrayList<Route> routes) {
        if (routes == null || map==null) {
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
    public static void drawMarkers(ArrayList<Route> routes) {
        if (routes==null || map==null) {
            return;
        }
        else if(routes.isEmpty()){
            return;
        }
        //Add starting marker
        Bitmap bitmap = iconGenerator.makeIcon("1");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(routes.get(0).getStart_lat(), routes.get(0).getStart_long()))
                .title(routes.get(0).getStart_address())
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));

        for (int i=0;i<routes.size();i++) {
            //Place location markers
            bitmap = iconGenerator.makeIcon(""+(i+2));
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(routes.get(i).getEnd_lat(), routes.get(i).getEnd_long()))
                    .title(routes.get(i).getEnd_address())
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
        }
    }
    public static void drawRoute(Route route){
        if(map==null)
            return;

        map.addPolyline(new PolylineOptions()
                .color(route.getLine_color())
                .width(12)
                .clickable(false)// Able to click or not.
                .addAll(route.getDecoded_polylines()));
    }
    public static void resetMap(){
        if(map==null)
            return;
        map.clear();
    }
    public static void drawMarkers(Route route){
        if(map==null)
            return;

        map.addMarker(new MarkerOptions().position(new LatLng(route.getStart_lat(), route.getStart_long())));
        map.addMarker(new MarkerOptions().position(new LatLng(route.getEnd_lat(), route.getEnd_long())));
    }
    public static void drawMarkers(Route route, int markernumber){
        if(map==null)
            return;
        Bitmap bitmap;
        bitmap = iconGenerator.makeIcon(""+(markernumber));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(route.getStart_lat(), route.getStart_long()))
                .title(route.getStart_address())
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
        bitmap = iconGenerator.makeIcon(""+(markernumber+1));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(route.getEnd_lat(), route.getEnd_long()))
                .title(route.getEnd_address())
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
    }
    public static void moveCameraToWantedArea(Route route){
        if(map==null)
            return;
        else if(bounds==null)
            moveCameraSmoothlyToWantedArea(new LatLng(route.getStart_lat(),route.getStart_long()),
                    new LatLng(route.getEnd_lat(),route.getEnd_long()),BOUNDS_PADDING);
        else
            moveCameraSmoothlyToWantedArea(bounds.southwest,bounds.northeast,BOUNDS_PADDING);
    }
    /**
     * Method to move camera to wanted area.
     */
    public static void moveCameraSmoothlyToWantedArea(LatLng bound1, LatLng bound2, int bound_padding) {
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
    public static void moveCameraImmediatelyToWantedArea(LatLng bound1, LatLng bound2, int bound_padding) {
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
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, BOUNDS_PADDING));
            }
        });

    }
    public void clearRequests(){
        locations.clear();
        travel_modes.clear();
    }
    //Location address list manipulating functions
    public static boolean addToRequestList(String location, String travel_mode) {
        locations.add(location);
        travel_modes.add(travel_mode);
        Log.d(MainActivity.class.getName(),"LOCATION ADDED:"+locations.get(0));
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
        if(routes==null){
            return null;
        }
        else if(routes.isEmpty()){
            return null;
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(Route route: routes) {
            //add corners to camera bounds builder
            builder.include(new LatLng(route.getStart_lat(), route.getStart_long()));
            builder.include(new LatLng(route.getEnd_lat(), route.getEnd_long()));
        }
        //Finally build the bounds
        return builder.build();
    }
    public static void updateMapWithExistingData(){
        if(map==null || routes==null)
            return;
        //Get camera bounds
        bounds = getCameraBounds(routes.getValue());
        //Move camera
        if(bounds!=null) {
            moveCameraImmediatelyToWantedArea(bounds.southwest, bounds.northeast, BOUNDS_PADDING);
        }
        //draw Routes
        drawRoutes(routes.getValue());
        //draw Markers
        drawMarkers(routes.getValue());
    }
    //INNER CLASSES
    private class RouteDrawer extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                ArrayList<Route> tmp_routes = JSONUtils.getRoutesFrom(locations,travel_modes,rdb.getrouteDao());
                Log.d(MainActivity.class.getName(),"ASYNC_GET_ROUTES:");
                if(tmp_routes==null) {
                    Log.d(MainActivity.class.getName(),"ASYNC_ROUTES NULL_GET_ROUTES");
                    return null;
                }
                Log.d(MainActivity.class.getName(),"ASYNC_ROUTES:"+tmp_routes.get(0).getStart_address());
                routes.postValue(tmp_routes);
            }catch(Exception e){
                Log.e(MainActivity.class.getName(),"Async Error",e);
            }
            return null;
        }

        protected void onPostExecute(Void voids) {
            if(routes==null){
                Log.d(MainActivity.class.getName(),"ASYNC_ROUTES NULL");
                return;
            }
            if(routes.getValue()==null) {
                Log.d(MainActivity.class.getName(),"ASYNC_ROUTES: NULL VALUE?!?!");
                return;
            }
            if(routes.getValue().isEmpty()){
                Log.d(MainActivity.class.getName(),"ASYNC_ROUTES: EMPTY LIST");
                return;
            }
            //Clear map
            resetMap();
            //Get camera bounds
            bounds = getCameraBounds(routes.getValue());
            //Move camera
            if(bounds!=null){
                moveCameraSmoothlyToWantedArea(bounds.southwest, bounds.northeast, BOUNDS_PADDING);
            }
            //draw Routes
            drawRoutes(routes.getValue());
            //draw Markers
            drawMarkers(routes.getValue());
            Log.d(MainActivity.class.getName(),"ASYNC_ROUTES SUCCESS:"+routes.getValue().get(0).getStart_address());
        }
    }
}