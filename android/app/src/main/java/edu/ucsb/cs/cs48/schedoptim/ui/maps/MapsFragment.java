package edu.ucsb.cs.cs48.schedoptim.ui.maps;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.ucsb.cs.cs48.schedoptim.AddTaskActivity;
import edu.ucsb.cs.cs48.schedoptim.MapsController;
import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.ui.locationInput.LocationInputActivity;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private MapsViewModel mViewModel;

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }

    private GoogleMap mMap;
    private String file_dir;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_maps, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //store program's internal read/write storage directory path
        file_dir = getContext().getFilesDir().toString();

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addLoc = new Intent(getContext(), AddTaskActivity.class);
                addLoc.putExtra("path", file_dir);
                startActivity(addLoc);
            }
        });

        Button b = root.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestAndDrawRoutes();
            }
        });

        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MapsViewModel.class);
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
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ucsb, 15.1f));

        // Add a marker in montreal and toronto, and move the camera
        //TODO: implement dynamic marker placement from JSON file
        LatLng montreal = new LatLng(45.5017123, -73.5672184);
        LatLng toronto = new LatLng(43.6532565,-79.38303979999999);
        //mMap.addMarker(new MarkerOptions().position(montreal).title("Marker in Montreal"));
        //mMap.addMarker(new MarkerOptions().position(toronto).title("Marker in Toronto"));

        //Set up button to draw routes
        Button b = (Button) getView().findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestAndDrawRoutes();
            }
        });

        //Schedule s = new Schedule();
        //s.testJSONToGSON(file_dir, "/test.json");
    }
    public void onClickRequestAndDrawRoutes(){         //<--Test travel mode by changing this

        //Controller class with drawing
        MapsController control = new MapsController(mMap,file_dir,"/test.json");
        control.drawRoutes();
    }
    /**
     * Method to move camera to wanted area.
     */
    public void moveCameraToWantedArea(LatLng bound1, LatLng bound2, int bound_padding) {
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
