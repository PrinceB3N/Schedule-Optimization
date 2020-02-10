package edu.ucsb.cs.cs48.schedoptim.ui.locationInput;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<String> mDataset;
    Context mContext;
    View mView;
    ViewHolder mViewHolder;

    public RecyclerViewAdapter(Context context1, List<String> data){
        mDataset = data;
        mContext = context1;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
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
    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.textView.setText(mDataset.get(position));
        // Set a click listener for item remove button
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemLabel = mDataset.get(position);
                mDataset.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mDataset.size());
                Toast.makeText(mContext,"Removed : " + itemLabel,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }
}
