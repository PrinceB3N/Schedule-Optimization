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
import java.util.Calendar;
import java.util.Date;
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

import static edu.ucsb.cs.cs48.schedoptim.MainActivity.cal;

public class MapsViewModel extends ViewModel{
    private final static int BOUNDS_PADDING = 128;
    private static GoogleMap map = null;
    private static RouteDatabase rdb = null;
    private static TaskDatabase tdb = null;
    private static IconGenerator iconGenerator;
    private static List<String> locations = new ArrayList<String>();
    private static List<String> travel_modes = new ArrayList<String>();
    private static List<Integer> colors = new ArrayList<>();
    private static List<String> end_times = new ArrayList<>();
    private static MutableLiveData<ArrayList<Route>> routes = new MutableLiveData(new ArrayList<Route>(0));
    private static LatLngBounds bounds;
    //private static Calendar stored_time=Calendar.getInstance();

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
        return this.rdb;
    }
    public void setRdb(RouteDatabase rdb){
        this.rdb=rdb;
    }
    public void setTdb(TaskDatabase tdb){
        this.tdb=tdb;
    }
    public void setIconGenerator(IconGenerator gen){
        this.iconGenerator=gen;
    }
    public MutableLiveData<ArrayList<Route>> getObservableRoutes(){
        if(!routes.getValue().isEmpty()) {
            Log.d(MainActivity.class.getName(), "ROUTEVAL:" + routes.getValue().get(0).getStart_address());
        }
        return routes;
    }
    public void loadAndDraw(){
        //update locations and travel modes
        loadLocationsAndTravelModesFromDatabase();
        //finally update the map and list
        drawRoutes();
    }

    public void setMap(GoogleMap map){ MapsViewModel.map =map; }
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
        if(map==null);
        //else if(bounds==null)
            moveCameraSmoothlyToWantedArea(new LatLng(route.getStart_lat(),route.getStart_long()),
                    new LatLng(route.getEnd_lat(),route.getEnd_long()),BOUNDS_PADDING);
        //else
            //moveCameraSmoothlyToWantedArea(bounds.southwest,bounds.northeast,BOUNDS_PADDING);
    }
    /**
     * Method to move camera to wanted area.
     */
    private static void moveCameraSmoothlyToWantedArea(LatLng bound1, LatLng bound2, int bound_padding) {
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
    private static void moveCameraImmediatelyToWantedArea(LatLng bound1, LatLng bound2, int bound_padding) {
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

    private static LatLngBounds getCameraBounds(ArrayList<Route> routes){
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
    private void loadLocationsAndTravelModesFromDatabase(){
        List<Task> tasks = tdb.taskDao().loadTaskByDate(Task.formatTaskDate(cal.getTime()));
        ArrayList<String> twr = new ArrayList<>();
        ArrayList<String> tm  = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<String> end_times = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task tem = tasks.get(i);
            if (tem.getCalRoute()){
                twr.add(tem.getLocation());
                tm.add(tem.getTravelMode());
                Integer tmp_color = tem.getColor();
                if(tmp_color==0)
                    colors.add(Color.BLUE);//DEFAULT
                else
                    colors.add(tmp_color);
                if(i!=0)
                    end_times.add(tem.getBegin_time());
            }
        }
        this.locations=twr;
        this.travel_modes=tm;
        this.colors=colors;
        this.end_times=end_times;
    }
    private boolean updateMapWithExistingData(){
        if(map==null || routes==null)
            return false;
        else if(locations.isEmpty() || travel_modes.isEmpty())
            return false;
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
        return true;
    }
    @Override
    public void onCleared(){
        if(rdb!=null)
            rdb.close();
        if(tdb!=null)
            tdb.close();
    }
    //INNER CLASSES
    static class RouteDrawer extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                ArrayList<Route> tmp_routes = JSONUtils.getRoutesFrom(locations,travel_modes,colors, end_times,rdb.getrouteDao());
                Log.d(MainActivity.class.getName(),"ASYNC_GET_ROUTES:");
                if(tmp_routes==null) {
                    Log.d(MainActivity.class.getName(),"ASYNC_ROUTES NULL_GET_ROUTES");
                    routes.postValue(new ArrayList<Route>(0));
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