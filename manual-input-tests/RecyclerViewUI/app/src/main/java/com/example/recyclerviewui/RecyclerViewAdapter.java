package com.example.recyclerviewui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
            public ViewHolder(View v){
                super(v);
                textView = (TextView)v.findViewById(R.id.location_textview);
            }
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            mView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_items,parent,false);
            mViewHolder = new ViewHolder(mView);
            return mViewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position){
            holder.textView.setText(mDataset.get(position));
        }

        @Override
        public int getItemCount(){
            return mDataset.size();
        }
}
