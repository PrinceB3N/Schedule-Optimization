package edu.ucsb.cs.cs48.schedoptim.ui.maps;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.Route;

public class RoutesAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Object> items;
    private int selected_route = -1;
    public RoutesAdapter(List<Route> routes) {
        if(routes==null){
            return;
        }
        else if(routes.isEmpty()){
            items=new ArrayList<>();
            return;
        }
        //initialize
        items=new ArrayList<>();
        //add first address
        items.add(routes.get(0).getStart_address());
        //loop through by adding addresses and routes
        for(int i=0;i<routes.size();i++){
            items.add(routes.get(i));
            items.add(routes.get(i).getEnd_address());
        }
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
            return;
        }
        else if(routes.isEmpty()){
            items=new ArrayList<>();
            return;
        }
        //initialize
        items=new ArrayList<>(routes.size()*2);
        //add first address
        items.add(routes.get(0).getStart_address());
        //loop through by adding addresses and routes
        for(int i=0;i<routes.size();i++){
            items.add(routes.get(i));
            items.add(routes.get(i).getEnd_address());
        }
        //finally notify change
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView;
        switch(viewType){
            case 0:
                contactView = inflater.inflate(R.layout.item_address, parent, false);
                return new ViewHolder0(contactView);
            case 1:
                contactView = inflater.inflate(R.layout.item_route, parent, false);
                return new ViewHolder1(contactView);
        }
        return null;
    }
    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final Object item = items.get(position);
        switch(viewHolder.getItemViewType()){
            case 0:
                ((ViewHolder0) viewHolder).editView((String) item, position);
                return;
            case 1:
                ((ViewHolder1) viewHolder).editView((Route) item, position);
                return;
        }


    }

    @Override
    public int getItemCount() {
        if(items==null)
            return 0;
        return items.size();
    }
    public class ViewHolder0 extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView route_number;
        public TextView route_address;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder0(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            route_number = itemView.findViewById(R.id.route_number);
            route_address = itemView.findViewById(R.id.route_address);
        }
        public void editView(String address, int position){
            // Set item views based on your views and data model
            String route_number = ""+(position/2+1);

            this.route_number.setText(route_number);
            this.route_address.setText(address);
        }
    }
    public class ViewHolder1 extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView route_info;
        public ImageView travel_mode_img;
        public ImageView route_color_img;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder1(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            route_info = itemView.findViewById(R.id.route_info);
            travel_mode_img = itemView.findViewById(R.id.travel_mode_img);
            route_color_img = itemView.findViewById(R.id.route_color);
        }
        public void editView(final Route route, final int position){
            // Set item views based on your views and data model

            if(position == selected_route) {
                route_info.setTextColor(Color.RED);
            }
            else {
                // Here, you must restore the color because the view is reused.. so, you may receive a reused view with wrong colors
                route_info.setTextColor(Color.BLUE);
            }
            String text = route.getFormattedTime()+"------"+route.getFormattedLength();
            route_info.setText(text);
            //Sets Image based on travel mode in route
            setTravelModeImg(route,travel_mode_img);
            //Set route color box
            route_color_img.setColorFilter(route.getLine_color());

            //Update map and item text color when recyclerview item is clicked
            route_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapsViewModel.resetMap();
                    MapsViewModel.drawRoute(route);
                    MapsViewModel.drawMarkers(route,position/2+1);
                    MapsViewModel.moveCameraToWantedArea(route);
                    setBackgroundColor(position);
                }
            });
        }
        private void setTravelModeImg(Route route,ImageView travel_mode_img){
            switch(route.getTravel_mode()){
                case "BICYCING":
                    travel_mode_img.setImageResource(R.drawable.bicycling);
                    return;
                case "WALKING":
                    travel_mode_img.setImageResource(R.drawable.walking);
                    return;
                case "TRANSIT":
                    travel_mode_img.setImageResource(R.drawable.transit);
                    return;
                case "DRIVING":
                    travel_mode_img.setImageResource(R.drawable.driving);
                    return;
            }
        }
    }

}
