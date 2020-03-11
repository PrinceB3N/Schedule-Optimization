package edu.ucsb.cs.cs48.schedoptim.ui.calendar.todo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.AddTaskActivity;
import edu.ucsb.cs.cs48.schedoptim.MainActivity;
import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.Route;
import edu.ucsb.cs.cs48.schedoptim.Task;

public class TaskAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemMoveCallback.ItemTouchHelperContract{
    private List<Task> items;
    private Context context;
    private StartDragListener listener;
    private GestureDetector gestureDetector;
    public TaskAdapter(List<Task> tasks, StartDragListener listener) {
        if(tasks==null){
            this.listener=listener;
            return;
        }
        else if(tasks.isEmpty()){
            items=new ArrayList<>();
            this.listener=listener;
            return;
        }
        //initialize
        items=tasks;
        this.listener=listener;
    }
    public void update(ArrayList<Task> tasks){
        if(tasks==null){
            return;
        }
        else if(tasks.isEmpty()){
            items=new ArrayList<>();
            return;
        }
        this.items = tasks;
        //finally notify change
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_todo_task, parent, false);
        if(gestureDetector==null)
            gestureDetector=new GestureDetector(context,new SingleTapConfirm());
        return new ViewHolder(contactView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        Task item = items.get(position);
        ((ViewHolder)viewHolder).editView(item, position);
    }

    @Override
    public int getItemCount() {
        if(items==null)
            return 0;
        return items.size();
    }
    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(this.items, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(this.items, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(ViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);

    }

    @Override
    public void onRowClear(ViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);

    }
    class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView todo_info;
        public View rowView;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            todo_info = itemView.findViewById(R.id.todo_info);
            this.rowView=itemView;
        }
        @SuppressLint("ClickableViewAccessibility")
        public void editView(Task item, int position){
            // Set item views based on your views and data model
            todo_info.setText(item.getTitle()+"\n"+item.getLocation()+
                    "\n"+Task.formatTaskTime(item.getBegin_time())+" "+Task.formatTaskTime(item.getEnd_time()));
            todo_info.setId(item.getId());
            todo_info.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(gestureDetector.onTouchEvent(event)){
                        listener.onSingleTapUp(v, event);
                        return true;
                    }
                    else if(event.getAction()==MotionEvent.ACTION_DOWN) {
                        listener.requestDrag(ViewHolder.this);
                        return true;
                    }
                    return false;
                }
            });
        }
    }
    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            return true;
        }
        @Override
        public void onLongPress(MotionEvent event){
            super.onLongPress(event);
        }
    }

}

