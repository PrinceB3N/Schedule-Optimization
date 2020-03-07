package edu.ucsb.cs.cs48.schedoptim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.Task;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private Context mContext;
    private List<Task> TaskList = new ArrayList<>();

    public TaskAdapter(RecyclerView recyclerView, List<Task> lt) {
        this.mContext = recyclerView.getContext();
        this.TaskList = lt;
    }

    public void setData(List<Task> dataList) {
        if (null != dataList) {
            this.TaskList.clear();
            this.TaskList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    // TODO: onClickListener(){}


    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_calendar_task, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        int t1 = Integer.valueOf(TaskList.get(position).getBegin_time().substring(0,2));
        int t2 = Integer.valueOf(TaskList.get(position).getBegin_time().substring(3,5));
        int t3 = Integer.valueOf(TaskList.get(position).getEnd_time().substring(0,2));
        int t4 = Integer.valueOf(TaskList.get(position).getEnd_time().substring(3,5));
        int length = (t3*60)+t4 - (t1*60)-t2;
        holder.title.setText(TaskList.get(position).getTitle());
        holder.location.setText(TaskList.get(position).getLocation());
        holder.title.setHeight(length*5);
    }

    @Override
    public int getItemCount() {
        return TaskList.size();
    }

    class TaskHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView location;

        private TaskHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textView_title);
            location = (TextView) itemView.findViewById(R.id.textView_location);
        }
    }
}
