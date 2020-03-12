package edu.ucsb.cs.cs48.schedoptim.ui.setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SettingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Calendar feature is coming soon !");
    }

    public LiveData<String> getText() {
        return mText;
    }
}