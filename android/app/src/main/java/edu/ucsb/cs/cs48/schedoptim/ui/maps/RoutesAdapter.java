package edu.ucsb.cs.cs48.schedoptim.ui.maps;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.MainActivity;
import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.Route;

public class RoutesAdapter extends
        RecyclerView.Adapter<RoutesAdapter.ViewHolder>{
    private List<Route> routes;
    private int selected_route = -1;
    public RoutesAdapter(List<Route> routes) {
            this.routes=routes;
    }
    public void setSelectedRoute(int position) {
        selected_route=position;
    }
    public void setBackgroundColor(int position){
        int oldposition = this.selected_route;
        this.setSelectedRoute(position);
        this.notifyItemChanged(position);
        this.notifyItemChanged(oldposition);
    }
    public void update(ArrayList<Route> routes){
        if(routes==null){
            Log.d(MainActivity.class.getName(),"Update: routes=null");
            return;
        }
        else if(this.routes==null){
            this.routes=routes;
            this.notifyDataSetChanged();
            Log.d(MainActivity.class.getName(),"Update: this.routes=null"+routes.get(0).getStart_address());
            return;
        }
        this.routes=routes;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoutesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_route, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final Route route = routes.get(position);
        // Set item views based on your views and data model
        final TextView textView = viewHolder.routeTextView;

        if(position == selected_route) {
            textView.setTextColor(Color.BLUE);

        }
        else {
            // Here, you must restore the color because the view is reused.. so, you may receive a reused view with wrong colors
            textView.setTextColor(Color.BLACK);
        }
        textView.setText(route.getStart_address()+" -> "+route.getEnd_address()+"\n"+
                            "Travel time: "+route.getTime()+"Distance: "+route.getLength()+"Travel mode: "+route.getTravel_mode());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsViewModel.resetMap();
                MapsViewModel.drawRoute(route);
                MapsViewModel.drawMarkers(route,position+1);
                MapsViewModel.moveCameraToWantedArea(route);
                setBackgroundColor(position);
            }
        });


/*
        final CheckBox checkBox = viewHolder.showRoute;
        checkBox.setActivated(true);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkBox.isActivated()) {
                    MapsViewModel.resetMap();
                    MapsViewModel.drawRoute(route);
                    MapsViewModel.drawMarkers(route);
                    MapsViewModel.moveCameraToWantedArea(route);
                    checkBox.setActivated(true);
                }
                else if(checkBox.isActivated()){
                    MapsViewModel.resetMap();
                    MapsViewModel.drawRoutes(routes);
                    MapsViewModel.drawMarkers(routes);
                    MapsViewModel.moveCameraToWantedArea();
                    checkBox.setActivated(false);
                }

            }
        });

 */

    }

    @Override
    public int getItemCount() {
        if(routes==null)
            return 0;
        return routes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView routeTextView;
        public CheckBox showRoute;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            routeTextView = (TextView) itemView.findViewById(R.id.contact_name);
            showRoute = (CheckBox) itemView.findViewById(R.id.showRoute);
        }
    }
}
