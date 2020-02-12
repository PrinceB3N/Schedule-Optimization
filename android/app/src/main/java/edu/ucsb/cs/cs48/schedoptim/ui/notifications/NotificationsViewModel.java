package edu.ucsb.cs.cs48.schedoptim.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Friend feature is coming soon!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}