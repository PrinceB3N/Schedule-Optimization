package edu.ucsb.cs.cs48.schedoptim.ui.locationInput;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.MapsController;
import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.ui.locationInput.helper.ItemTouchHelperAdapter;
import edu.ucsb.cs.cs48.schedoptim.ui.locationInput.helper.ItemTouchHelperViewHolder;
import edu.ucsb.cs.cs48.schedoptim.ui.locationInput.helper.OnStartDragListener;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private List<String> mDataset;
    Context mContext;
    View mView;
    ViewHolder mViewHolder;
    //MapsController mControl;

    private final OnStartDragListener mDragStartListener;

    public RecyclerViewAdapter(Context context, List<String> data, OnStartDragListener dragStartListener){
        mDragStartListener = dragStartListener;
        mDataset = data;
        mContext = context;
        //mControl = control;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public TextView textView;
        public Button removeButton;

        public ViewHolder(View v){
            super(v);
            textView = (TextView)v.findViewById(R.id.location_textview);
            removeButton = (Button) v.findViewById(R.id.removeLoc);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
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
        // Start a drag whenever the view is touched
        holder.textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

    @Override
    public void onItemDismiss(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mDataset, fromPosition, toPosition);
        notifyDataSetChanged();
        return true;
    }
}
