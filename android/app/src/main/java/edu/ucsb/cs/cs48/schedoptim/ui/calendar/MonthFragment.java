package edu.ucsb.cs.cs48.schedoptim.ui.calendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.ui.calendar.day.DayFragment;

public class MonthFragment extends Fragment {

    CalendarView simpleCalendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_month, container, false);
        simpleCalendarView = (CalendarView) root.findViewById(R.id.calendar); // get the reference of CalendarView
        // perform setOnDateChangeListener event on CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                DayFragment newFragment = new DayFragment();
                Bundle args = new Bundle();
                args.putInt("year", year-1900);
                args.putInt("month", month);
                args.putInt("dayOfMonth", dayOfMonth);
                newFragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.calendarContainer, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return root;
    }
}