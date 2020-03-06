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
        int t = HourList.get(position).time;
        if(t<10) { holder.hour.setText("0" + HourList.get(position).getTime() + ":00"); }
        else {holder.hour.setText(HourList.get(position).getTime() + ":00");}
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
        TextView hour;
        RecyclerView listOfTask;

        private HourHolder(View itemView) {
            super(itemView);
            hour = itemView.findViewById(R.id.textView_hour);
            listOfTask = itemView.findViewById((R.id.recyclerView_tasks));
        }
    }
}
