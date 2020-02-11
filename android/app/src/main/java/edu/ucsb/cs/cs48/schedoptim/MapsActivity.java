package edu.ucsb.cs.cs48.schedoptim;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    private String file_dir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Sets xml layout view to be used first
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        //store program's internal read/write storage directory path
        file_dir = this.getFilesDir().toString();

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private static final LatLng BOUND1 = new LatLng(44.5017123, -78.5672184);
    private static final LatLng BOUND2 = new LatLng(43.6532565,-79.38303979999999);



    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;


        LatLng ucsb = new LatLng(34.412936, -119.846063);

        // Add a marker in UCSB and move the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ucsb, 15.1f));

        // Add a marker in montreal and toronto, and move the camera
        //TODO: implement dynamic marker placement from JSON file
        LatLng montreal = new LatLng(45.5017123, -73.5672184);
        LatLng toronto = new LatLng(43.6532565,-79.38303979999999);
        mMap.addMarker(new MarkerOptions().position(montreal).title("Marker in Montreal"));
        mMap.addMarker(new MarkerOptions().position(toronto).title("Marker in Toronto"));
        //Enter two LatLng bounds and padding to move the camera there
        moveCameraToWantedArea(BOUND1,BOUND2,16);                            //<--Test camera by changing bounds

        //Set up button to draw routes
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestAndDrawRoutes();
            }
        });
    }
    public void onClickRequestAndDrawRoutes(){
        List<String> locations = new ArrayList<>();
        locations.add("Ontario+Science+Centre+Don+Mills+Road+North+York+ON+Canada");        //<--Test routes by adding locations
        locations.add("Toronto+ON+Canada");

        //Set travel mode
        List<String> travel_modes = new ArrayList<>();
        travel_modes.add("bicycling");                                                   //<--Test travel mode by changing this
        RouteDrawer draw = new RouteDrawer(locations,travel_modes, mMap);
        draw.execute();
    }
    /**
     * Method to move camera to wanted area.
     */
    private void moveCameraToWantedArea(LatLng bound1, LatLng bound2, int bound_padding) {
        final LatLng BOUND1 = bound1;
        final LatLng BOUND2 = bound2;
        final int BOUNDS_PADDING = bound_padding;
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                // Set up the bounds coordinates for the area we want the user's viewpoint to be.
                LatLngBounds bounds = new LatLngBounds.Builder()
                        .include(BOUND1)
                        .include(BOUND2)
                        .build();
                // Move the camera now.
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, BOUNDS_PADDING));//0=BOUNDS_PADDING
            }
        });

    }

}
