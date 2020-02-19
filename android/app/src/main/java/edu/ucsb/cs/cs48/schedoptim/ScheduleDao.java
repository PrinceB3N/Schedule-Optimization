package edu.ucsb.cs.cs48.schedoptim;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface ScheduleDao {

    //-----------------------insert----------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Schedule schedule);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(Schedule... schedules);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Schedule> schedules);

    //---------------------update------------------------
    @Update()
    int update(Schedule schedule);

    @Update()
    int updateAll(Schedule... schedule);

    @Update()
    int updateAll(List<Schedule> schedule);

    //-------------------delete-------------------
    @Delete
    int delete(Schedule schedule);

    @Delete
    int deleteAll(List<Schedule> schedules);

    @Delete
    int deleteAll(Schedule... schedules);
}