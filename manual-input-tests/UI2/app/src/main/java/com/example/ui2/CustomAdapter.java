package com.example.ui2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<String> listLoc;
    private List<String> listLocFilter;

    private List<String> listLocSelected;
    private List<View> listSelectedRows;

    public CustomAdapter(Context context, List<String> list){
        this.context = context;
        this.listLoc = list;
        listLocFilter = new ArrayList<>(list);
        listLocSelected = new ArrayList<>();
        listSelectedRows = new ArrayList<>();
    }

    public void addItem(String location){
        listLoc.add(location);
        listLocFilter.add(location);
    }

    public void handleLongPress(int position, View view){
        if(listSelectedRows.contains(view)){
            listSelectedRows.remove(view);
            listLocSelected.remove(listLoc.get(position));
            view.setBackgroundResource(R.color.colorWhite);
        }else{
            listLocSelected.add(listLoc.get(position));
            listSelectedRows.add(view);
            view.setBackgroundResource(R.color.colorDarkGray);
        }
    }

    public List<String> getListLocationSelected(){
        return listLocSelected;
    }

    public void removeSelectedLocations(){
        listLoc.removeAll(listLocSelected);
        listLocFilter.removeAll(listLocSelected);
        listLocSelected.clear();
        for(View view : listSelectedRows)
            view.setBackgroundResource(R.color.colorWhite);
        listSelectedRows.clear();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    class ViewHolder{
        TextView locationName;
    }
}
