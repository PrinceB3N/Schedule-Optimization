package edu.ucsb.cs.cs48.schedoptim;

import android.content.Context;
import android.util.JsonWriter;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;

public class JSONUtils {
    private static String logname= MapsActivity.class.getName();
    //JSON file path which stores json from google directions https request
    private final static String jsonfile_path = "/locations.json";
    //Keeps track of all paths filled
    private static List<String> used_paths;

    /**
     * This gives the route from supposedly new locations. Also, updates locations.json file.
     * DON'T use if locations have not been updated, use "getRouteFromLocations()" instead.
     */
    public static List<LatLng> getRouteFromNewLocations(String file_dir, List<String> locations, String travel_mode){
        List<LatLng> latLngList = new ArrayList<>();
        try {
            //Https request to google directions API, which stores JSON as a JSONObject
            JSONObject jsonobject = getJSONFrom(getStreamFromUrl(locations,travel_mode));
            //Also updates stored json file
            saveToJSONFile(jsonobject, file_dir);
            //grabs encoded route from the json object
            String encoded_route = getKeywordFromJson(jsonobject);
            //adds all the decoded points to the list
            latLngList.addAll((PolyUtil.decode(encoded_route)));

            return latLngList;
        }

        catch(Exception e){
            Log.e(logname,"Error",e);
        }
        return null;
    }
    public static List<LatLng> getStoredRoute(String file_dir){
        List<LatLng> latLngList = new ArrayList<>();
        try {
            File file = new File(file_dir+jsonfile_path);
            if (!file.exists()) {
                file.createNewFile();
            }
            InputStream is = new FileInputStream(file);

            JSONObject jsonobject = getJSONFrom(is);
            is.close();
            //grabs encoded route from the json object
            String encoded_route = getKeywordFromJson(jsonobject);
            //adds all the decoded points to the list
            latLngList.addAll((PolyUtil.decode(encoded_route)));

            return latLngList;
        }
        catch(IOException e){
            Log.e(logname,"IO",e);
        }
        catch(JSONException e){
            Log.d(logname,"JSON reading error",e);
        }
        return latLngList;
    }
    //TODO: go through each route again to get the locations. Also, to find largest dimensions of camera to encompass all routes.

    public Schedule getScheduleFromLocations(List<String> locations, List<String> travel_mode) throws Exception{
        ArrayList<Route> routes=new ArrayList<>();
        for(int i=0;i<travel_mode.size()-1;i++){
            InputStream is = getStreamFromUrl(Arrays.asList(locations.get(i), locations.get(i + 1)),travel_mode.get(i));
            Reader reader = new InputStreamReader(is, "UTF-8");
            routes.add(parseToRoute(new Gson().fromJson(reader,JsonObject.class)));
        }
    }
    //TODO: add camera bounds field to Route
    private Route parseToRoute(JsonObject json){
        JsonObject routes = json.getAsJsonArray("routes").get(0).getAsJsonObject();

        //Get camera bounds
        JsonObject bounds = routes.getAsJsonObject("bounds");
        double ne_lat = bounds.getAsJsonObject("northeast").get("lat").getAsDouble();
        double ne_lng  = bounds.getAsJsonObject("northeast").get("lng").getAsDouble();
        LatLng northeast = new LatLng(ne_lat,ne_lng);
        double sw_lat = bounds.getAsJsonObject("southwest").get("lat").getAsDouble();
        double sw_lng  = bounds.getAsJsonObject("southwest").get("lng").getAsDouble();
        LatLng southwest = new LatLng(sw_lat,sw_lng);

        JsonObject legs = routes.getAsJsonArray("legs").get(0).getAsJsonObject();
        //Get total distance
        int meters = legs.getAsJsonObject("distance").get("value").getAsInt();

        //Get total time
        int seconds = legs.getAsJsonObject("duration").get("value").getAsInt();

        //Get addresses
        JsonObject start_location = legs.getAsJsonObject("start_location");
        double start_lat = start_location.get("lat").getAsDouble();
        double start_lng = start_location.get("lng").getAsDouble();
        LatLng start = new LatLng(start_lat,start_lng);
        String start_address = legs.get("start_address").getAsString();
        Location starting = new Location(start,start_address);

        JsonObject end_location = legs.getAsJsonObject("end_location");
        double end_lat = end_location.get("lat").getAsDouble();
        double end_lng = end_location.get("lng").getAsDouble();
        LatLng end = new LatLng(end_lat,end_lng);
        String end_address = legs.get("end_address").getAsString();
        Location ending = new Location(end, end_address);

        //Get overview polyline encoding
        String encoded = routes.getAsJsonObject("overview_polyline").get("points").getAsString();

        //Get travel_mode
        String travel_mode = legs.getAsJsonArray("steps").get(0).getAsJsonObject().get("travel_mode").getAsString();

        //return Schedule
        return new Route("BLUE",encoded,starting,ending,travel_mode,meters,seconds);
    }

    /***
     *      PRIVATE HELPER METHODS BELOW-----------------------------------------------------------------
     */

    private static String getKeywordFromJson(JSONObject json) throws JSONException{
        JSONArray ja = json.getJSONArray("routes");
        return ja.getJSONObject(0).getJSONObject("overview_polyline").getString("points");
    }
    /**
     * Takes a JSONObject and stores it in jsonfile's path
     */
    private static void saveToJSONFile(JSONObject j, String file_dir) throws IOException, JSONException{
        File file = new File(file_dir+jsonfile_path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter filewriter = new FileWriter(file);
            filewriter.write(j.toString(2));
            filewriter.close();
    }
    //Helper function: gets InputStream from https request JSON output
    private static InputStream getStreamFromUrl(List<String> locations,String mode) throws MalformedURLException, IOException {
        return new URL(placesToUrl(locations, mode)).openStream();
    }
    /**
     * Gets JSON object from inputstream
     */
    private static JSONObject getJSONFrom(InputStream is) throws IOException, JSONException {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);  //reader outpurs full json text
            return new JSONObject(jsonText);
    }
    //Helper function for file reading
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    /**
     *  Helper function that takes locations and travel mode
     *      then formats it into https request string
     *  Format:   json?origin=____&destination=_____&waypoints=4 ______|___|____|___&key=______
     */
    private static String placesToUrl(List<String> locations, String travel_mode){
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
            Log.d(logname,"No String?:"+sb.toString());
            return gson.fromJson(new FileReader(file), c);
        }
        catch(Exception e){
            Log.e(logname, "Can't get object from JSON:",e);
        }
        return null;
    }
    public static void storeObjectAsJSON(Schedule s, String file_dir, String file_path){
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
            Log.d(logname,gson.toJson(s));
        }catch(Exception e){
            Log.e(logname,"",e);
        }
    }
    public static String objToJSONString(Schedule s){
        try{
            return new Gson().toJson(s);
        }catch(Exception e){
            Log.e(logname,"",e);
        }
        return "";
    }
}
