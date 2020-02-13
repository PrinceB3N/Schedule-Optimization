package edu.ucsb.cs.cs48.schedoptim.ui.locationInput;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.ui.locationInput.helper.OnStartDragListener;
import edu.ucsb.cs.cs48.schedoptim.ui.locationInput.helper.SimpleItemTouchHelperCallback;

public class LocationInputFragment extends Fragment implements OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;

    // private LocationInputViewModel mViewModel;

    public static LocationInputFragment newInstance() {
        return new LocationInputFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.location_input_fragment, container, false);

        final ArrayList<String> locations = new ArrayList<>();

        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), locations, this);

        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        Button add = root.findViewById(R.id.addLoc);
        final EditText location = root.findViewById(R.id.location);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locA = location.getText().toString();
                if (TextUtils.isEmpty(locA)) {
                    location.setError("Enter a location");
                    return;
                }
                locations.add(locA);
                adapter.notifyItemInserted(adapter.getItemCount());
                location.getText().clear();
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        return root;
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
