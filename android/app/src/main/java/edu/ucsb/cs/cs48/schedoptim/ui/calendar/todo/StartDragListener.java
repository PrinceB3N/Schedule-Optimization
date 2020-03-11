package edu.ucsb.cs.cs48.schedoptim.ui.calendar.todo;

import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface StartDragListener {
    void requestDrag(RecyclerView.ViewHolder viewHolder);
    void onSingleTapUp(View v, MotionEvent event);
}
