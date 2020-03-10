package edu.ucsb.cs.cs48.schedoptim.ui.calendar.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.AddTaskActivity;
import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.Route;
import edu.ucsb.cs.cs48.schedoptim.Task;

public class TaskAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Task> items;
    private Context context;
    public TaskAdapter(List<Task> tasks) {
        if(tasks==null){
            return;
        }
        else if(tasks.isEmpty()){
            items=new ArrayList<>();
            return;
        }
        //initialize
        items=tasks;
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
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_todo_task, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        Task item = items.get(position);
        ((ViewHolder)viewHolder).editView(item, position);
        return;
    }

    @Override
    public int getItemCount() {
        if(items==null)
            return 0;
        return items.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView todo_info;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            todo_info = itemView.findViewById(R.id.todo_info);
        }
        //TODO:Implement EditTodo popup on click
        public void editView(Task item, int position){
            // Set item views based on your views and data model
            todo_info.setText(item.getTitle()+"\n"+item.getLocation()+"\n"+item.getBegin_time()+" "+item.getEnd_time());
            todo_info.setId(item.getId());
            todo_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent addTodo= new Intent(context, AddTaskActivity.class);
                    addTodo.putExtra("TYPE","todo");
                    addTodo.putExtra("ID",todo_info.getId());
                    ((Activity)context).startActivityForResult(addTodo,1);
                }
            });
        }
    }
}

