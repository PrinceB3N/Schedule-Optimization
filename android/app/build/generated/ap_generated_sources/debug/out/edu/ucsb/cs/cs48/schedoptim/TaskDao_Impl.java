package edu.ucsb.cs.cs48.schedoptim;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Task> __insertionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter<Task> __deletionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter<Task> __updateAdapterOfTask;

  public TaskDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTask = new EntityInsertionAdapter<Task>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `task` (`id`,`title`,`type`,`location`,`begin_time`,`end_time`,`date`,`note`,`color`,`importance`,`notification`,`calRoute`,`travelMode`,`duration`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Task value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getType() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getType());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getLocation());
        }
        if (value.getBegin_time() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getBegin_time());
        }
        if (value.getEnd_time() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getEnd_time());
        }
        if (value.getDate() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDate());
        }
        if (value.getNote() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getNote());
        }
        if (value.getColor() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getColor());
        }
        if (value.getImportance() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getImportance());
        }
        if (value.getNotiTime() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getNotiTime());
        }
        final int _tmp;
        _tmp = value.getCalRoute() ? 1 : 0;
        stmt.bindLong(12, _tmp);
        if (value.getTravelMode() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getTravelMode());
        }
        if (value.getDuration() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getDuration());
        }
      }
    };
    this.__deletionAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `task` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Task value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `task` SET `id` = ?,`title` = ?,`type` = ?,`location` = ?,`begin_time` = ?,`end_time` = ?,`date` = ?,`note` = ?,`color` = ?,`importance` = ?,`notification` = ?,`calRoute` = ?,`travelMode` = ?,`duration` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Task value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getType() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getType());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getLocation());
        }
        if (value.getBegin_time() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getBegin_time());
        }
        if (value.getEnd_time() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getEnd_time());
        }
        if (value.getDate() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDate());
        }
        if (value.getNote() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getNote());
        }
        if (value.getColor() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getColor());
        }
        if (value.getImportance() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getImportance());
        }
        if (value.getNotiTime() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getNotiTime());
        }
        final int _tmp;
        _tmp = value.getCalRoute() ? 1 : 0;
        stmt.bindLong(12, _tmp);
        if (value.getTravelMode() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getTravelMode());
        }
        if (value.getDuration() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getDuration());
        }
        stmt.bindLong(15, value.getId());
      }
    };
  }

  @Override
  public Long insert(final Task task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfTask.insertAndReturnId(task);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Long> insertAll(final Task... tasks) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      List<Long> _result = __insertionAdapterOfTask.insertAndReturnIdsList(tasks);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Long> insertAll(final List<Task> tasks) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      List<Long> _result = __insertionAdapterOfTask.insertAndReturnIdsList(tasks);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(final Task task) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfTask.handle(task);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAll(final List<Task> tasks) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfTask.handleMultiple(tasks);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAll(final Task... tasks) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfTask.handleMultiple(tasks);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int update(final Task task) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfTask.handle(task);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateAll(final Task... task) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfTask.handleMultiple(task);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateAll(final List<Task> task) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfTask.handleMultiple(task);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Task> loadAllTasks() {
    final String _sql = "SELECT * FROM task";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
      final int _cursorIndexOfBeginTime = CursorUtil.getColumnIndexOrThrow(_cursor, "begin_time");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
      final int _cursorIndexOfImportance = CursorUtil.getColumnIndexOrThrow(_cursor, "importance");
      final int _cursorIndexOfNotiTime = CursorUtil.getColumnIndexOrThrow(_cursor, "notification");
      final int _cursorIndexOfCalRoute = CursorUtil.getColumnIndexOrThrow(_cursor, "calRoute");
      final int _cursorIndexOfTravelMode = CursorUtil.getColumnIndexOrThrow(_cursor, "travelMode");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Task _item;
        _item = new Task();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        _item.setType(_tmpType);
        final String _tmpLocation;
        _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
        _item.setLocation(_tmpLocation);
        final String _tmpBegin_time;
        _tmpBegin_time = _cursor.getString(_cursorIndexOfBeginTime);
        _item.setBegin_time(_tmpBegin_time);
        final String _tmpEnd_time;
        _tmpEnd_time = _cursor.getString(_cursorIndexOfEndTime);
        _item.setEnd_time(_tmpEnd_time);
        final String _tmpDate;
        _tmpDate = _cursor.getString(_cursorIndexOfDate);
        _item.setDate(_tmpDate);
        final String _tmpNote;
        _tmpNote = _cursor.getString(_cursorIndexOfNote);
        _item.setNote(_tmpNote);
        final String _tmpColor;
        _tmpColor = _cursor.getString(_cursorIndexOfColor);
        _item.setColor(_tmpColor);
        final String _tmpImportance;
        _tmpImportance = _cursor.getString(_cursorIndexOfImportance);
        _item.setImportance(_tmpImportance);
        final String _tmpNotiTime;
        _tmpNotiTime = _cursor.getString(_cursorIndexOfNotiTime);
        _item.setNotiTime(_tmpNotiTime);
        final boolean _tmpCalRoute;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCalRoute);
        _tmpCalRoute = _tmp != 0;
        _item.setCalRoute(_tmpCalRoute);
        final String _tmpTravelMode;
        _tmpTravelMode = _cursor.getString(_cursorIndexOfTravelMode);
        _item.setTravelMode(_tmpTravelMode);
        final String _tmpDuration;
        _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
        _item.setDuration(_tmpDuration);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Task> loadAllByIds(final int[] taskIds) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT ");
    _stringBuilder.append("*");
    _stringBuilder.append(" FROM task WHERE id IN (");
    final int _inputSize = taskIds.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int _item : taskIds) {
      _statement.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
      final int _cursorIndexOfBeginTime = CursorUtil.getColumnIndexOrThrow(_cursor, "begin_time");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
      final int _cursorIndexOfImportance = CursorUtil.getColumnIndexOrThrow(_cursor, "importance");
      final int _cursorIndexOfNotiTime = CursorUtil.getColumnIndexOrThrow(_cursor, "notification");
      final int _cursorIndexOfCalRoute = CursorUtil.getColumnIndexOrThrow(_cursor, "calRoute");
      final int _cursorIndexOfTravelMode = CursorUtil.getColumnIndexOrThrow(_cursor, "travelMode");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Task _item_1;
        _item_1 = new Task();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item_1.setId(_tmpId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item_1.setTitle(_tmpTitle);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        _item_1.setType(_tmpType);
        final String _tmpLocation;
        _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
        _item_1.setLocation(_tmpLocation);
        final String _tmpBegin_time;
        _tmpBegin_time = _cursor.getString(_cursorIndexOfBeginTime);
        _item_1.setBegin_time(_tmpBegin_time);
        final String _tmpEnd_time;
        _tmpEnd_time = _cursor.getString(_cursorIndexOfEndTime);
        _item_1.setEnd_time(_tmpEnd_time);
        final String _tmpDate;
        _tmpDate = _cursor.getString(_cursorIndexOfDate);
        _item_1.setDate(_tmpDate);
        final String _tmpNote;
        _tmpNote = _cursor.getString(_cursorIndexOfNote);
        _item_1.setNote(_tmpNote);
        final String _tmpColor;
        _tmpColor = _cursor.getString(_cursorIndexOfColor);
        _item_1.setColor(_tmpColor);
        final String _tmpImportance;
        _tmpImportance = _cursor.getString(_cursorIndexOfImportance);
        _item_1.setImportance(_tmpImportance);
        final String _tmpNotiTime;
        _tmpNotiTime = _cursor.getString(_cursorIndexOfNotiTime);
        _item_1.setNotiTime(_tmpNotiTime);
        final boolean _tmpCalRoute;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCalRoute);
        _tmpCalRoute = _tmp != 0;
        _item_1.setCalRoute(_tmpCalRoute);
        final String _tmpTravelMode;
        _tmpTravelMode = _cursor.getString(_cursorIndexOfTravelMode);
        _item_1.setTravelMode(_tmpTravelMode);
        final String _tmpDuration;
        _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
        _item_1.setDuration(_tmpDuration);
        _result.add(_item_1);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Task> loadAllByDate(final int taskDate) {
    final String _sql = "SELECT * FROM task WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, taskDate);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
      final int _cursorIndexOfBeginTime = CursorUtil.getColumnIndexOrThrow(_cursor, "begin_time");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
      final int _cursorIndexOfImportance = CursorUtil.getColumnIndexOrThrow(_cursor, "importance");
      final int _cursorIndexOfNotiTime = CursorUtil.getColumnIndexOrThrow(_cursor, "notification");
      final int _cursorIndexOfCalRoute = CursorUtil.getColumnIndexOrThrow(_cursor, "calRoute");
      final int _cursorIndexOfTravelMode = CursorUtil.getColumnIndexOrThrow(_cursor, "travelMode");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Task _item;
        _item = new Task();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        _item.setType(_tmpType);
        final String _tmpLocation;
        _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
        _item.setLocation(_tmpLocation);
        final String _tmpBegin_time;
        _tmpBegin_time = _cursor.getString(_cursorIndexOfBeginTime);
        _item.setBegin_time(_tmpBegin_time);
        final String _tmpEnd_time;
        _tmpEnd_time = _cursor.getString(_cursorIndexOfEndTime);
        _item.setEnd_time(_tmpEnd_time);
        final String _tmpDate;
        _tmpDate = _cursor.getString(_cursorIndexOfDate);
        _item.setDate(_tmpDate);
        final String _tmpNote;
        _tmpNote = _cursor.getString(_cursorIndexOfNote);
        _item.setNote(_tmpNote);
        final String _tmpColor;
        _tmpColor = _cursor.getString(_cursorIndexOfColor);
        _item.setColor(_tmpColor);
        final String _tmpImportance;
        _tmpImportance = _cursor.getString(_cursorIndexOfImportance);
        _item.setImportance(_tmpImportance);
        final String _tmpNotiTime;
        _tmpNotiTime = _cursor.getString(_cursorIndexOfNotiTime);
        _item.setNotiTime(_tmpNotiTime);
        final boolean _tmpCalRoute;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCalRoute);
        _tmpCalRoute = _tmp != 0;
        _item.setCalRoute(_tmpCalRoute);
        final String _tmpTravelMode;
        _tmpTravelMode = _cursor.getString(_cursorIndexOfTravelMode);
        _item.setTravelMode(_tmpTravelMode);
        final String _tmpDuration;
        _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
        _item.setDuration(_tmpDuration);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Task findById(final int id) {
    final String _sql = "SELECT * FROM task WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
      final int _cursorIndexOfBeginTime = CursorUtil.getColumnIndexOrThrow(_cursor, "begin_time");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
      final int _cursorIndexOfImportance = CursorUtil.getColumnIndexOrThrow(_cursor, "importance");
      final int _cursorIndexOfNotiTime = CursorUtil.getColumnIndexOrThrow(_cursor, "notification");
      final int _cursorIndexOfCalRoute = CursorUtil.getColumnIndexOrThrow(_cursor, "calRoute");
      final int _cursorIndexOfTravelMode = CursorUtil.getColumnIndexOrThrow(_cursor, "travelMode");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final Task _result;
      if(_cursor.moveToFirst()) {
        _result = new Task();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _result.setTitle(_tmpTitle);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        _result.setType(_tmpType);
        final String _tmpLocation;
        _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
        _result.setLocation(_tmpLocation);
        final String _tmpBegin_time;
        _tmpBegin_time = _cursor.getString(_cursorIndexOfBeginTime);
        _result.setBegin_time(_tmpBegin_time);
        final String _tmpEnd_time;
        _tmpEnd_time = _cursor.getString(_cursorIndexOfEndTime);
        _result.setEnd_time(_tmpEnd_time);
        final String _tmpDate;
        _tmpDate = _cursor.getString(_cursorIndexOfDate);
        _result.setDate(_tmpDate);
        final String _tmpNote;
        _tmpNote = _cursor.getString(_cursorIndexOfNote);
        _result.setNote(_tmpNote);
        final String _tmpColor;
        _tmpColor = _cursor.getString(_cursorIndexOfColor);
        _result.setColor(_tmpColor);
        final String _tmpImportance;
        _tmpImportance = _cursor.getString(_cursorIndexOfImportance);
        _result.setImportance(_tmpImportance);
        final String _tmpNotiTime;
        _tmpNotiTime = _cursor.getString(_cursorIndexOfNotiTime);
        _result.setNotiTime(_tmpNotiTime);
        final boolean _tmpCalRoute;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCalRoute);
        _tmpCalRoute = _tmp != 0;
        _result.setCalRoute(_tmpCalRoute);
        final String _tmpTravelMode;
        _tmpTravelMode = _cursor.getString(_cursorIndexOfTravelMode);
        _result.setTravelMode(_tmpTravelMode);
        final String _tmpDuration;
        _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
        _result.setDuration(_tmpDuration);
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
