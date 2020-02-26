package edu.ucsb.cs.cs48.schedoptim.ui.maps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> allRoutes;
    private MutableLiveData<String> routeId;
    private MutableLiveData<String> routeTitle;

    public MapsViewModel() {
        allRoutes = new MutableLiveData<>();
        routeId = new MutableLiveData<>();
        routeTitle = new MutableLiveData<>();
    }

    public LiveData<String> getAllRoutes() {
        return allRoutes;
    }
    public void setAllRoutes(String s) { allRoutes.setValue(s); }
    public LiveData<String> getRouteId() {
        return routeId;
    }
    public void setRouteId(String s) { routeId.setValue(s); }
    public LiveData<String> getRouteTitle() {
        return routeTitle;
    }
    public void setRouteTitle(String s) { routeTitle.setValue(s); }
}
