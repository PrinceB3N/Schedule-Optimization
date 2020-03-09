package edu.ucsb.cs.cs48.schedoptim.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.Hour;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourHolder> {
    private Context mContext;
    private List<Hour> HourList = new ArrayList<>();
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public HourAdapter(RecyclerView recyclerView, List<Hour> lh) {
        this.mContext = recyclerView.getContext();
        this.HourList = lh;
    }

    public void setData(List<Hour> dataList) {
        if (null != dataList) {
            this.HourList.clear();
            this.HourList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public HourHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_calendar_hour, parent, false);
        return new HourHolder(view);
    }

    @Override
    public void onBindViewHolder(HourHolder holder, int position) {
        String tb = HourList.get(position).getTasksInHour().get(0).getBegin_time();
        String te = HourList.get(position).getTasksInHour().get(0).getEnd_time();


        holder.begin_time.setText(tb);
        holder.end_time.setText(te);
        holder.listOfTask.setLayoutManager(new LinearLayoutManager(holder.listOfTask.getContext(),
                RecyclerView.HORIZONTAL, false));
        holder.listOfTask.setAdapter(new TaskAdapter(holder.listOfTask, HourList.get(position).getTasksInHour()));
        holder.listOfTask.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return HourList.size();
    }

    class HourHolder extends RecyclerView.ViewHolder {
        TextView begin_time;
        TextView end_time;
        RecyclerView listOfTask;

        private HourHolder(View itemView) {
            super(itemView);
            begin_time = itemView.findViewById(R.id.textView_begin);
            end_time = itemView.findViewById(R.id.textView_end);
            listOfTask = itemView.findViewById((R.id.recyclerView_tasks));
        }
    }
}
