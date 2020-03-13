package edu.ucsb.cs.cs48.schedoptim.ui.maps;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;

import edu.ucsb.cs.cs48.schedoptim.AddTaskActivity;
import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.Route;
import edu.ucsb.cs.cs48.schedoptim.RouteDatabase;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;
import edu.ucsb.cs.cs48.schedoptim.ui.calendar.day.DayViewModel;


public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private RouteDatabase routeDatabase;
    private TaskDatabase taskDatabase;
    private IconGenerator iconGenerator;
    private MapsViewModel mapsViewModel;
    private RecyclerView rvRoutes;
    private RoutesAdapter adapter;

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_maps, container, false);
        routeDatabase = Room.databaseBuilder(getContext(),
                RouteDatabase.class, "database-route")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        taskDatabase = Room.databaseBuilder(getContext(),
                TaskDatabase.class, "database-task")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        //Setup IconGenerator for markers
        iconGenerator=new IconGenerator(this.getContext());

        mapsViewModel = new ViewModelProvider(this, new MapsViewModelFactory(routeDatabase,taskDatabase,iconGenerator)).get(MapsViewModel.class);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Obtain RecyclerView for routes
        rvRoutes = root.findViewById(R.id.mapList);
        // Create adapter passing in the sample user data
        adapter = new RoutesAdapter(mapsViewModel.getObservableRoutes().getValue());
        rvRoutes.setAdapter(adapter);

        // Set layout manager to position the items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rvRoutes.setLayoutManager(layoutManager);

        //Set dividers between elements in list
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvRoutes.getContext(),
                layoutManager.getOrientation());
        rvRoutes.addItemDecoration(dividerItemDecoration);

        // Create the observer which updates the UI.
//        final Observer<ArrayList<Route>> nameObserver =
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mapsViewModel.getObservableRoutes().observe(getViewLifecycleOwner(),  new Observer<ArrayList<Route>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Route> routes) {
                adapter.update(routes);
            }
        });

        //Create buttons
        //Load locations and travel modes based on global Calendar instance
        Button updateMap = root.findViewById(R.id.updatemap);
        //获取80dp转换后的设备pix值。
        int mTextViewWidth = dip2px(getContext(), 80);
        //计算所有文本占有的屏幕宽度(pix)
        float textWidth = updateMap.getPaint().measureText(updateMap.getText().toString());

        while (textWidth > mTextViewWidth) {

            textWidth = updateMap.getPaint().measureText(updateMap.getText().toString());

            //如果所有文本的宽度超过TextView自身限定的宽度，那么就尝试迭代的减小字体的textSize，直到不超过TextView的宽度为止。
            if (textWidth > mTextViewWidth) {
                int textSize = (int) updateMap.getTextSize();
                textSize = textSize - 2;
                updateMap.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }

        }
        updateMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestAndDrawRoutes();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
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

    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap=googleMap;
        mapsViewModel.setMap(mMap);
        //re-initalize map from pre-existing mapsViewModel if same date, else reset
        mapsViewModel.loadAndDraw();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
    }
    public void onClickRequestAndDrawRoutes(){
        mapsViewModel.drawRoutes();
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mapsViewModel.getObservableRoutes().removeObservers(this);
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
