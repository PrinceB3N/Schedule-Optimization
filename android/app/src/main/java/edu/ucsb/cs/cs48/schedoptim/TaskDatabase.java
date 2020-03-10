package edu.ucsb.cs.cs48.schedoptim;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = { Task.class }, version = 8,exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

//    private static final String DB_NAME = "TaskDatabase.db";
//    private static volatile TaskDatabase instance;
    public abstract TaskDao taskDao();
    private static TaskDatabase INSTANCE;//创建单例


    static TaskDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null)    {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            TaskDatabase.class, "task_database")
//                            .addCallback(sOnOpenCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;}
//
//    private static RoomDatabase.Callback sOnOpenCallback =
//            new RoomDatabase.Callback(){
//                @Override
//                public void onOpen (@NonNull SupportSQLiteDatabase db){
//                    super.onOpen(db);
//                    initializeData();
//                }};

//    static synchronized TaskDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = create(context);
//        }
//        return instance;
//    }
//
//    private static TaskDatabase create(final Context context) {
//        return Room.databaseBuilder(
//                context,
//                TaskDatabase.class,
//                DB_NAME).build();
//    }

}