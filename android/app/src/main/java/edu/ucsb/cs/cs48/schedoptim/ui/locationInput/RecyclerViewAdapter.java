package edu.ucsb.cs.cs48.schedoptim.ui.locationInput;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<String> mDataset;
    Context mContext;
    View mView;
    ViewHolder mViewHolder;

    public RecyclerViewAdapter(Context context, List<String> data){
        mDataset = data;
        mContext = context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button removeButton;

        public ViewHolder(View v){
            super(v);
            textView = (TextView)v.findViewById(R.id.location_textview);
            removeButton = (Button) v.findViewById(R.id.removeLoc);
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        mView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_items,parent,false);
        mViewHolder = new ViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        holder.textView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }
}
