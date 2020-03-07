package edu.ucsb.cs.cs48.schedoptim;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class RouteDatabase_Impl extends RouteDatabase {
  private volatile RouteDao _routeDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `route` (`route_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `line_color` INTEGER NOT NULL, `encoded_polylines` TEXT, `start_lat` REAL NOT NULL, `start_long` REAL NOT NULL, `start_address` TEXT, `end_lat` REAL NOT NULL, `end_long` REAL NOT NULL, `end_address` TEXT, `travel_mode` TEXT, `length` REAL NOT NULL, `time` REAL NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b246b66df4974d412064b19c0fa6f3db')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `route`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsRoute = new HashMap<String, TableInfo.Column>(12);
        _columnsRoute.put("route_id", new TableInfo.Column("route_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoute.put("line_color", new TableInfo.Column("line_color", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoute.put("encoded_polylines", new TableInfo.Column("encoded_polylines", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoute.put("start_lat", new TableInfo.Column("start_lat", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoute.put("start_long", new TableInfo.Column("start_long", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoute.put("start_address", new TableInfo.Column("start_address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoute.put("end_lat", new TableInfo.Column("end_lat", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoute.put("end_long", new TableInfo.Column("end_long", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoute.put("end_address", new TableInfo.Column("end_address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoute.put("travel_mode", new TableInfo.Column("travel_mode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoute.put("length", new TableInfo.Column("length", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoute.put("time", new TableInfo.Column("time", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRoute = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRoute = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRoute = new TableInfo("route", _columnsRoute, _foreignKeysRoute, _indicesRoute);
        final TableInfo _existingRoute = TableInfo.read(_db, "route");
        if (! _infoRoute.equals(_existingRoute)) {
          return new RoomOpenHelper.ValidationResult(false, "route(edu.ucsb.cs.cs48.schedoptim.Route).\n"
                  + " Expected:\n" + _infoRoute + "\n"
                  + " Found:\n" + _existingRoute);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b246b66df4974d412064b19c0fa6f3db", "fdbbf701205f930aa5fbb94cf6aa30fd");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "route");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `route`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public RouteDao getrouteDao() {
    if (_routeDao != null) {
      return _routeDao;
    } else {
      synchronized(this) {
        if(_routeDao == null) {
          _routeDao = new RouteDao_Impl(this);
        }
        return _routeDao;
      }
    }
  }
}
