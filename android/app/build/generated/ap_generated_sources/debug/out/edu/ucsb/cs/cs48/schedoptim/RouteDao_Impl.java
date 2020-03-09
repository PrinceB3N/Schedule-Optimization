package edu.ucsb.cs.cs48.schedoptim;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class RouteDao_Impl implements RouteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Route> __insertionAdapterOfRoute;

  private final EntityDeletionOrUpdateAdapter<Route> __deletionAdapterOfRoute;

  private final EntityDeletionOrUpdateAdapter<Route> __updateAdapterOfRoute;

  public RouteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRoute = new EntityInsertionAdapter<Route>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `route` (`route_id`,`line_color`,`encoded_polylines`,`start_lat`,`start_long`,`start_address`,`end_lat`,`end_long`,`end_address`,`travel_mode`,`length`,`time`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Route value) {
        stmt.bindLong(1, value.getRoute_id());
        stmt.bindLong(2, value.getLine_color());
        if (value.getEncoded_polylines() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getEncoded_polylines());
        }
        stmt.bindDouble(4, value.getStart_lat());
        stmt.bindDouble(5, value.getStart_long());
        if (value.getStart_address() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getStart_address());
        }
        stmt.bindDouble(7, value.getEnd_lat());
        stmt.bindDouble(8, value.getEnd_long());
        if (value.getEnd_address() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getEnd_address());
        }
        if (value.getTravel_mode() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getTravel_mode());
        }
        stmt.bindDouble(11, value.getLength());
        stmt.bindDouble(12, value.getTime());
      }
    };
    this.__deletionAdapterOfRoute = new EntityDeletionOrUpdateAdapter<Route>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `route` WHERE `route_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Route value) {
        stmt.bindLong(1, value.getRoute_id());
      }
    };
    this.__updateAdapterOfRoute = new EntityDeletionOrUpdateAdapter<Route>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `route` SET `route_id` = ?,`line_color` = ?,`encoded_polylines` = ?,`start_lat` = ?,`start_long` = ?,`start_address` = ?,`end_lat` = ?,`end_long` = ?,`end_address` = ?,`travel_mode` = ?,`length` = ?,`time` = ? WHERE `route_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Route value) {
        stmt.bindLong(1, value.getRoute_id());
        stmt.bindLong(2, value.getLine_color());
        if (value.getEncoded_polylines() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getEncoded_polylines());
        }
        stmt.bindDouble(4, value.getStart_lat());
        stmt.bindDouble(5, value.getStart_long());
        if (value.getStart_address() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getStart_address());
        }
        stmt.bindDouble(7, value.getEnd_lat());
        stmt.bindDouble(8, value.getEnd_long());
        if (value.getEnd_address() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getEnd_address());
        }
        if (value.getTravel_mode() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getTravel_mode());
        }
        stmt.bindDouble(11, value.getLength());
        stmt.bindDouble(12, value.getTime());
        stmt.bindLong(13, value.getRoute_id());
      }
    };
  }

  @Override
  public Long insert(final Route route) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfRoute.insertAndReturnId(route);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Long> insertAll(final Route... routes) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      List<Long> _result = __insertionAdapterOfRoute.insertAndReturnIdsList(routes);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Long> insertAll(final List<Route> routes) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      List<Long> _result = __insertionAdapterOfRoute.insertAndReturnIdsList(routes);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(final Route route) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfRoute.handle(route);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAll(final List<Route> routes) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfRoute.handleMultiple(routes);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAll(final Route... routes) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfRoute.handleMultiple(routes);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int update(final Route route) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfRoute.handle(route);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateAll(final Route... routes) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfRoute.handleMultiple(routes);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateAll(final List<Route> route) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfRoute.handleMultiple(route);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Route> getAll() {
    final String _sql = "SELECT * FROM route";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfRouteId = CursorUtil.getColumnIndexOrThrow(_cursor, "route_id");
      final int _cursorIndexOfLineColor = CursorUtil.getColumnIndexOrThrow(_cursor, "line_color");
      final int _cursorIndexOfEncodedPolylines = CursorUtil.getColumnIndexOrThrow(_cursor, "encoded_polylines");
      final int _cursorIndexOfStartLat = CursorUtil.getColumnIndexOrThrow(_cursor, "start_lat");
      final int _cursorIndexOfStartLong = CursorUtil.getColumnIndexOrThrow(_cursor, "start_long");
      final int _cursorIndexOfStartAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "start_address");
      final int _cursorIndexOfEndLat = CursorUtil.getColumnIndexOrThrow(_cursor, "end_lat");
      final int _cursorIndexOfEndLong = CursorUtil.getColumnIndexOrThrow(_cursor, "end_long");
      final int _cursorIndexOfEndAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "end_address");
      final int _cursorIndexOfTravelMode = CursorUtil.getColumnIndexOrThrow(_cursor, "travel_mode");
      final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "length");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final List<Route> _result = new ArrayList<Route>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Route _item;
        _item = new Route();
        final long _tmpRoute_id;
        _tmpRoute_id = _cursor.getLong(_cursorIndexOfRouteId);
        _item.setRoute_id(_tmpRoute_id);
        final int _tmpLine_color;
        _tmpLine_color = _cursor.getInt(_cursorIndexOfLineColor);
        _item.setLine_color(_tmpLine_color);
        final String _tmpEncoded_polylines;
        _tmpEncoded_polylines = _cursor.getString(_cursorIndexOfEncodedPolylines);
        _item.setEncoded_polylines(_tmpEncoded_polylines);
        final double _tmpStart_lat;
        _tmpStart_lat = _cursor.getDouble(_cursorIndexOfStartLat);
        _item.setStart_lat(_tmpStart_lat);
        final double _tmpStart_long;
        _tmpStart_long = _cursor.getDouble(_cursorIndexOfStartLong);
        _item.setStart_long(_tmpStart_long);
        final String _tmpStart_address;
        _tmpStart_address = _cursor.getString(_cursorIndexOfStartAddress);
        _item.setStart_address(_tmpStart_address);
        final double _tmpEnd_lat;
        _tmpEnd_lat = _cursor.getDouble(_cursorIndexOfEndLat);
        _item.setEnd_lat(_tmpEnd_lat);
        final double _tmpEnd_long;
        _tmpEnd_long = _cursor.getDouble(_cursorIndexOfEndLong);
        _item.setEnd_long(_tmpEnd_long);
        final String _tmpEnd_address;
        _tmpEnd_address = _cursor.getString(_cursorIndexOfEndAddress);
        _item.setEnd_address(_tmpEnd_address);
        final String _tmpTravel_mode;
        _tmpTravel_mode = _cursor.getString(_cursorIndexOfTravelMode);
        _item.setTravel_mode(_tmpTravel_mode);
        final float _tmpLength;
        _tmpLength = _cursor.getFloat(_cursorIndexOfLength);
        _item.setLength(_tmpLength);
        final float _tmpTime;
        _tmpTime = _cursor.getFloat(_cursorIndexOfTime);
        _item.setTime(_tmpTime);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Route findByRouteId(final long route_id) {
    final String _sql = "SELECT * FROM route WHERE route_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, route_id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfRouteId = CursorUtil.getColumnIndexOrThrow(_cursor, "route_id");
      final int _cursorIndexOfLineColor = CursorUtil.getColumnIndexOrThrow(_cursor, "line_color");
      final int _cursorIndexOfEncodedPolylines = CursorUtil.getColumnIndexOrThrow(_cursor, "encoded_polylines");
      final int _cursorIndexOfStartLat = CursorUtil.getColumnIndexOrThrow(_cursor, "start_lat");
      final int _cursorIndexOfStartLong = CursorUtil.getColumnIndexOrThrow(_cursor, "start_long");
      final int _cursorIndexOfStartAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "start_address");
      final int _cursorIndexOfEndLat = CursorUtil.getColumnIndexOrThrow(_cursor, "end_lat");
      final int _cursorIndexOfEndLong = CursorUtil.getColumnIndexOrThrow(_cursor, "end_long");
      final int _cursorIndexOfEndAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "end_address");
      final int _cursorIndexOfTravelMode = CursorUtil.getColumnIndexOrThrow(_cursor, "travel_mode");
      final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "length");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final Route _result;
      if(_cursor.moveToFirst()) {
        _result = new Route();
        final long _tmpRoute_id;
        _tmpRoute_id = _cursor.getLong(_cursorIndexOfRouteId);
        _result.setRoute_id(_tmpRoute_id);
        final int _tmpLine_color;
        _tmpLine_color = _cursor.getInt(_cursorIndexOfLineColor);
        _result.setLine_color(_tmpLine_color);
        final String _tmpEncoded_polylines;
        _tmpEncoded_polylines = _cursor.getString(_cursorIndexOfEncodedPolylines);
        _result.setEncoded_polylines(_tmpEncoded_polylines);
        final double _tmpStart_lat;
        _tmpStart_lat = _cursor.getDouble(_cursorIndexOfStartLat);
        _result.setStart_lat(_tmpStart_lat);
        final double _tmpStart_long;
        _tmpStart_long = _cursor.getDouble(_cursorIndexOfStartLong);
        _result.setStart_long(_tmpStart_long);
        final String _tmpStart_address;
        _tmpStart_address = _cursor.getString(_cursorIndexOfStartAddress);
        _result.setStart_address(_tmpStart_address);
        final double _tmpEnd_lat;
        _tmpEnd_lat = _cursor.getDouble(_cursorIndexOfEndLat);
        _result.setEnd_lat(_tmpEnd_lat);
        final double _tmpEnd_long;
        _tmpEnd_long = _cursor.getDouble(_cursorIndexOfEndLong);
        _result.setEnd_long(_tmpEnd_long);
        final String _tmpEnd_address;
        _tmpEnd_address = _cursor.getString(_cursorIndexOfEndAddress);
        _result.setEnd_address(_tmpEnd_address);
        final String _tmpTravel_mode;
        _tmpTravel_mode = _cursor.getString(_cursorIndexOfTravelMode);
        _result.setTravel_mode(_tmpTravel_mode);
        final float _tmpLength;
        _tmpLength = _cursor.getFloat(_cursorIndexOfLength);
        _result.setLength(_tmpLength);
        final float _tmpTime;
        _tmpTime = _cursor.getFloat(_cursorIndexOfTime);
        _result.setTime(_tmpTime);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Route findRouteByFields(final String start_address, final String end_address,
      final String travel_mode) {
    final String _sql = "SELECT * FROM route WHERE start_address = ? AND end_address=? AND travel_mode =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (start_address == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, start_address);
    }
    _argIndex = 2;
    if (end_address == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, end_address);
    }
    _argIndex = 3;
    if (travel_mode == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, travel_mode);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfRouteId = CursorUtil.getColumnIndexOrThrow(_cursor, "route_id");
      final int _cursorIndexOfLineColor = CursorUtil.getColumnIndexOrThrow(_cursor, "line_color");
      final int _cursorIndexOfEncodedPolylines = CursorUtil.getColumnIndexOrThrow(_cursor, "encoded_polylines");
      final int _cursorIndexOfStartLat = CursorUtil.getColumnIndexOrThrow(_cursor, "start_lat");
      final int _cursorIndexOfStartLong = CursorUtil.getColumnIndexOrThrow(_cursor, "start_long");
      final int _cursorIndexOfStartAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "start_address");
      final int _cursorIndexOfEndLat = CursorUtil.getColumnIndexOrThrow(_cursor, "end_lat");
      final int _cursorIndexOfEndLong = CursorUtil.getColumnIndexOrThrow(_cursor, "end_long");
      final int _cursorIndexOfEndAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "end_address");
      final int _cursorIndexOfTravelMode = CursorUtil.getColumnIndexOrThrow(_cursor, "travel_mode");
      final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "length");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final Route _result;
      if(_cursor.moveToFirst()) {
        _result = new Route();
        final long _tmpRoute_id;
        _tmpRoute_id = _cursor.getLong(_cursorIndexOfRouteId);
        _result.setRoute_id(_tmpRoute_id);
        final int _tmpLine_color;
        _tmpLine_color = _cursor.getInt(_cursorIndexOfLineColor);
        _result.setLine_color(_tmpLine_color);
        final String _tmpEncoded_polylines;
        _tmpEncoded_polylines = _cursor.getString(_cursorIndexOfEncodedPolylines);
        _result.setEncoded_polylines(_tmpEncoded_polylines);
        final double _tmpStart_lat;
        _tmpStart_lat = _cursor.getDouble(_cursorIndexOfStartLat);
        _result.setStart_lat(_tmpStart_lat);
        final double _tmpStart_long;
        _tmpStart_long = _cursor.getDouble(_cursorIndexOfStartLong);
        _result.setStart_long(_tmpStart_long);
        final String _tmpStart_address;
        _tmpStart_address = _cursor.getString(_cursorIndexOfStartAddress);
        _result.setStart_address(_tmpStart_address);
        final double _tmpEnd_lat;
        _tmpEnd_lat = _cursor.getDouble(_cursorIndexOfEndLat);
        _result.setEnd_lat(_tmpEnd_lat);
        final double _tmpEnd_long;
        _tmpEnd_long = _cursor.getDouble(_cursorIndexOfEndLong);
        _result.setEnd_long(_tmpEnd_long);
        final String _tmpEnd_address;
        _tmpEnd_address = _cursor.getString(_cursorIndexOfEndAddress);
        _result.setEnd_address(_tmpEnd_address);
        final String _tmpTravel_mode;
        _tmpTravel_mode = _cursor.getString(_cursorIndexOfTravelMode);
        _result.setTravel_mode(_tmpTravel_mode);
        final float _tmpLength;
        _tmpLength = _cursor.getFloat(_cursorIndexOfLength);
        _result.setLength(_tmpLength);
        final float _tmpTime;
        _tmpTime = _cursor.getFloat(_cursorIndexOfTime);
        _result.setTime(_tmpTime);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
