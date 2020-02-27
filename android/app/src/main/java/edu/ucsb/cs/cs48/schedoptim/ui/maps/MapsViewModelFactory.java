package edu.ucsb.cs.cs48.schedoptim.ui.maps;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.ui.IconGenerator;

import edu.ucsb.cs.cs48.schedoptim.RouteDatabase;

public class MapsViewModelFactory implements ViewModelProvider.Factory {
    GoogleMap map;
    private RouteDatabase rdb;
    private IconGenerator iconGenerator;
    public MapsViewModelFactory(GoogleMap map, RouteDatabase rdb, IconGenerator iconGenerator) {
        this.map=map;
        this.rdb=rdb;
        this.iconGenerator=iconGenerator;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MapsViewModel(map,rdb,iconGenerator);
    }
}
