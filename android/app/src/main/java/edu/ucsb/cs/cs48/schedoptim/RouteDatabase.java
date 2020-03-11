package edu.ucsb.cs.cs48.schedoptim;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = { Route.class}, version = 4,exportSchema = false)
public abstract class RouteDatabase extends RoomDatabase {

    //    private static final String DB_NAME = "RouteDatabase.db";
//    private static volatile RouteDatabase instance;
    public abstract RouteDao getrouteDao();
    private static RouteDatabase INSTANCE;//创建单例

    public static RouteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RouteDatabase.class) {
                if (INSTANCE == null)    {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            RouteDatabase.class, "route_database")
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

//    static synchronized RouteDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = create(context);
//        }
//        return instance;
//    }
//
//    private static RouteDatabase create(final Context context) {
//        return Room.databaseBuilder(
//                context,
//                RouteDatabase.class,
//                DB_NAME).build();
//    }

}