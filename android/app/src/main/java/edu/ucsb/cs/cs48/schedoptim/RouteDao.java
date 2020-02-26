package edu.ucsb.cs.cs48.schedoptim;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface RouteDao {

    //所有的CURD根据primary key进行匹配
    //------------------------query------------------------
    @Query("SELECT * FROM route")
    List<Route> getAll();

    @Query("SELECT * FROM route WHERE route_id = :route_id")
    Route findByRouteId(long route_id);

    @Query("SELECT * FROM route WHERE start_address = :start_address AND end_address=:end_address AND travel_mode =:travel_mode")
    Route findRouteByFields(String start_address, String end_address, String travel_mode);

    //-----------------------insert----------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Route route);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(Route... routes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Route> routes);

    //---------------------update------------------------
    @Update()
    int update(Route route);

    @Update()
    int updateAll(Route... routes);

    @Update()
    int updateAll(List<Route> route);

    //-------------------delete-------------------
    @Delete
    int delete(Route route);

    @Delete
    int deleteAll(List<Route> routes);

    @Delete
    int deleteAll(Route... routes);
}
