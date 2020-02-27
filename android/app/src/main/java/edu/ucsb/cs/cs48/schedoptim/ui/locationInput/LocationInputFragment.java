package edu.ucsb.cs.cs48.schedoptim.ui.locationInput;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.ucsb.cs.cs48.schedoptim.R;

public class LocationInputFragment extends Fragment {


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
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), locations);

        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        return root;
    }

    // TODO: Use the ViewModel
    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InputViewModel.class);

    }*/
}
