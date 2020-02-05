package edu.ucsb.cs.cs48.schedoptim;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class JSONUtils {
    private static String logname= MapsActivity.class.getName();
    //JSON file path which stores json from google directions https request
    private String jsonfile_path = "locations.json";
    public JSONUtils(){

    }
    /**
     * This gives the route from supposedly new locations. Also, updates locations.json file.
     * DON'T use if locations have not been updated, use "getRouteFromLocations()" instead.
     */
    public List<LatLng> getRouteFromNewLocations(Context context, List<String> locations, String travel_mode){
        List<LatLng> latLngList = new ArrayList<>();
        try {
            //Https request to google directions API, which stores JSON as a JSONObject
            JSONObject jsonobject = getJSONFrom(getStreamFromUrl(locations,travel_mode));
            //Also updates stored json file
            saveToJSONFile(jsonobject, context);
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
    public List<LatLng> getStoredRoute(Context context){
        List<LatLng> latLngList = new ArrayList<>();
        try {
            //InputStream is = new FileInputStream (new File(jsonfile_path));
            //InputStream is = context.openFileInput(jsonfile_path);
            //InputStream is = new FileInputStream(new File(context.getFilesDir(),jsonfile_path));
            File file = new File(context.getFilesDir()+"/"+jsonfile_path);
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
    private void saveToJSONFile(JSONObject j, Context context) throws IOException, JSONException{
        File file = new File(context.getFilesDir()+"/"+jsonfile_path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter filewriter = new FileWriter(file);
            filewriter.write(j.toString(2));
            filewriter.close();
    }
    //Helper function: gets InputStream from https request JSON output
    private InputStream getStreamFromUrl(List<String> locations,String mode) throws MalformedURLException, IOException {
        return new URL(new JSONUtils().placesToUrl(locations, mode)).openStream();
    }
    /**
     * Gets JSON object from inputstream
     */
    private JSONObject getJSONFrom(InputStream is) throws IOException, JSONException {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);  //reader outpurs full json text
            return new JSONObject(jsonText);
    }
    //Helper function for file reading
    private String readAll(Reader rd) throws IOException {
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
    private String placesToUrl(List<String> locations, String travel_mode){
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
}
