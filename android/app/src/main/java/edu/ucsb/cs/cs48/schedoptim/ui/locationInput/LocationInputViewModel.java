package edu.ucsb.cs.cs48.schedoptim.ui.locationInput;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationInputViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public LocationInputViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is input fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
