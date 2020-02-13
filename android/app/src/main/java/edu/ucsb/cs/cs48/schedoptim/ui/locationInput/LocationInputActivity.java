package edu.ucsb.cs.cs48.schedoptim.ui.locationInput;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.MapsController;
import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.ui.locationInput.helper.OnStartDragListener;
import edu.ucsb.cs.cs48.schedoptim.ui.locationInput.helper.SimpleItemTouchHelperCallback;

public class LocationInputActivity extends FragmentActivity implements OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_input_fragment);

        final String travel_mode = "walking";
        final String file_dir = this.getFilesDir().toString();
        final String file_path = "/test.json";
        final MapsController control = new MapsController(file_dir,file_path);
        final List<String> locations = control.getRequestList();

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, locations, this);

        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        Button add = findViewById(R.id.addLoc);
        Button route = findViewById(R.id.calculate);
        Button clear = findViewById(R.id.clear);
        final EditText location = findViewById(R.id.location);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locA = location.getText().toString();
                if (TextUtils.isEmpty(locA)) {
                    location.setError("Enter a location");
                    return;
                }
                //locations.add(locA);
                control.addToRequestList(locA,travel_mode);
                adapter.notifyItemInserted(adapter.getItemCount());
                location.getText().clear();
            }
        });
        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for(String loc:locations) {
//                    control.addToRequestList(loc,travel_mode);
//                }
                LocationInputActivity.this.finish();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.clearRequestList();
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    // TODO: Use the ViewModel
    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InputViewModel.class);

    }*/
}