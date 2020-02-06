package edu.ucsb.cs.cs48.schedoptim;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface TaskDao {

    //所有的CURD根据primary key进行匹配
    //------------------------query------------------------
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE id IN (:taskIds)")
    List<Task> loadAllByIds(int[] taskIds);

//    @Query("SELECT * FROM task WHERE first_name LIKE :first AND "
//            + "last_name LIKE :last LIMIT 1")
//    Task findByName(String first, String last);

    @Query("SELECT * FROM task WHERE id = :id")
    Task findById(int id);

    //-----------------------insert----------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Task task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(Task... tasks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Task> tasks);

    //---------------------update------------------------
    @Update()
    int update(Task task);

    @Update()
    int updateAll(Task... task);

    @Update()
    int updateAll(List<Task> task);

    //-------------------delete-------------------
    @Delete
    int delete(Task task);

    @Delete
    int deleteAll(List<Task> tasks);

    @Delete
    int deleteAll(Task... tasks);
}