package edu.ucsb.cs.cs48.schedoptim.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Calendar feature is coming soon !");
    }

    public LiveData<String> getText() {
        return mText;
    }
}