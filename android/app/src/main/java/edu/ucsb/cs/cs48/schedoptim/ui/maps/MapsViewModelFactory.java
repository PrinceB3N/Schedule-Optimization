package edu.ucsb.cs.cs48.schedoptim.ui.maps;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.ui.IconGenerator;

import edu.ucsb.cs.cs48.schedoptim.RouteDatabase;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;

public class MapsViewModelFactory implements ViewModelProvider.Factory {
    private RouteDatabase rdb;
    private IconGenerator iconGenerator;
    private TaskDatabase tdb;
    public MapsViewModelFactory(RouteDatabase rdb, TaskDatabase tdb, IconGenerator iconGenerator) {
        this.rdb=rdb;
        this.tdb=tdb;
        this.iconGenerator=iconGenerator;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MapsViewModel(rdb,tdb,iconGenerator);
    }
}
