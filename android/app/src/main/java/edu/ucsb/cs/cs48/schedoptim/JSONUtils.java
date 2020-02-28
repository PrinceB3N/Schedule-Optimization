package edu.ucsb.cs.cs48.schedoptim;

import android.content.Context;
import android.graphics.Color;
import android.util.JsonWriter;
import android.util.Log;

import androidx.room.Database;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;


public class JSONUtils {
    private static String logname = MainActivity.class.getName();

    public static ArrayList<Route> getRoutesFrom(List<String> locations, List<String> travel_modes, RouteDao rDao) throws Exception {
        if (locations.size() < 2 || travel_modes.size() == 0 || locations.size() != travel_modes.size()) {
            return null;
        }
        Locale loc = new Locale("en", "US");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, loc);
        String date = dateFormat.format(new Date());

        ArrayList<Route> routes = new ArrayList<>();

        for (int i = 0; i < travel_modes.size()-1; i++) {
            //Format the location String
            String start_address = formatLocation(locations.get(i));
            String end_address = formatLocation(locations.get(i+1));
            //Format travel mode string
            String travel_mode = formatTravelMode(travel_modes.get(i));
            //Try getting exisiting route
            Route route = rDao.findRouteByFields(start_address,end_address,travel_mode);
            if(route!=null){
                routes.add(route);
                continue;
            }
            else {
                InputStream is = new URL(placesToUrl(Arrays.asList(
                        start_address, end_address), travel_mode))
                        .openStream();
                Reader reader = new InputStreamReader(is, "UTF-8");
                //Get and store new route in database and list
                route = parseToRoute(new Gson().fromJson(reader, JsonObject.class));
                rDao.insert(route);
                routes.add(route);
                //Finish up
                is.close();
                reader.close();
            }
        }

        //return schedule
        return routes;
    }
    private static Route parseToRoute(JsonObject json){
        JsonObject routes = json.getAsJsonArray("routes").get(0).getAsJsonObject();

        JsonObject legs = routes.getAsJsonArray("legs").get(0).getAsJsonObject();

        //Get total distance
        int meters = legs.getAsJsonObject("distance").get("value").getAsInt();

        //Get total time
        int seconds = legs.getAsJsonObject("duration").get("value").getAsInt();

        //Get addresses
        String start_address = legs.get("start_address").getAsString();

        JsonObject start_location = legs.getAsJsonObject("start_location");
        double start_lat = start_location.get("lat").getAsDouble();
        double start_lng = start_location.get("lng").getAsDouble();
        LatLng start = new LatLng(start_lat,start_lng);

        String end_address = legs.get("end_address").getAsString();

        JsonObject end_location = legs.getAsJsonObject("end_location");
        double end_lat = end_location.get("lat").getAsDouble();
        double end_lng = end_location.get("lng").getAsDouble();
        LatLng end = new LatLng(end_lat,end_lng);

        //Get overview polyline encoding
        String encoded = routes.getAsJsonObject("overview_polyline").get("points").getAsString();

        //Get travel_mode
        String travel_mode = legs.getAsJsonArray("steps").get(0).getAsJsonObject().get("travel_mode").getAsString();

        //return Schedule
        return new Route(Color.BLUE,encoded,start,start_address,end,end_address,travel_mode,meters,seconds);
    }

    public static String formatLocation(String location){
        return location.replaceAll(" ","+").replaceAll(",","");
    }
    //Formats a travel mode string into correct output, otherwise default to driving
    public static String formatTravelMode(String travel_mode){
        String mode=travel_mode.toLowerCase();
        switch(mode) {
            case "walking":
            case "bicycling":
            case "transit":
            case "driving":
                return mode;
            default:
                return "walking";
        }
    }
    public static String placesToUrl(List<String> locations, String travel_mode){
        //Formats String for URL input
        for(int i=0;i<locations.size();i++){
            locations.set(i,formatLocation(locations.get(i)));
        }
        if(locations.size()<2 || travel_mode==null)
            return "";
        StringBuilder sb = new StringBuilder();
        String locs_url="https://maps.googleapis.com/maps/api/directions/json?origin="+locations.get(0)+"&destination="+locations.get(1);
        String end_url="&mode="+travel_mode+"&key=AIzaSyDxNa3D3bhhY_-3IhwIixcHEUpuR-yJvm4";
        sb.append(locs_url);
        if(locations.size()==2)
            return sb.append(end_url).toString();

        sb.append("&waypoints=").append(locations.get(2));
        if(locations.size()==3)
            return sb.append(end_url).toString();

        //else if more than 3, add additional waypoints
        for(int i=3;i<locations.size();i++){
            sb.append("|").append(locations.get(i));
        }
        return sb.append(end_url).toString();
    }
    public static Object getObjectFromJSON(Class c, String file_dir,String file_path){
        Gson gson = new Gson();
        try {
            File file = new File(file_dir+file_path);
            FileReader rd = new FileReader(file);
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            rd.close();
            return gson.fromJson(new FileReader(file), c);
        }
        catch(Exception e){
            Log.e(logname, "Can't get object from JSON:",e);
        }
        return null;
    }
    public static void storeObjectAsJSON(Object s, String file_dir, String file_path){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(file_dir+file_path);
        try{
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter filewriter = new FileWriter(file);
            filewriter.write(gson.toJson(s));
            filewriter.flush();
            filewriter.close();
        }catch(Exception e){
            Log.e(logname,"",e);
        }
    }
    public static String objToJSONString(Object s){
        try{
            return new Gson().toJson(s);
        }catch(Exception e){
            Log.e(logname,"",e);
        }
        return "";
    }
}

