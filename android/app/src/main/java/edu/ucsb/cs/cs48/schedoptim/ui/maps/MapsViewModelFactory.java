package edu.ucsb.cs.cs48.schedoptim.ui.maps;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;

import edu.ucsb.cs.cs48.schedoptim.RouteDatabase;

public class MapsViewModelFactory implements ViewModelProvider.Factory {
    GoogleMap map;
    private RouteDatabase rdb;
    public MapsViewModelFactory(GoogleMap map, RouteDatabase rdb) {
        this.map=map;
        this.rdb=rdb;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MapsViewModel(map,rdb);
    }
}
