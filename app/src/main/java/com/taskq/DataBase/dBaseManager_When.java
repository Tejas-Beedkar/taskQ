package com.taskq.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class dBaseManager_When {

    private dBaseArchitecture_When dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public dBaseManager_When(Context c) {
        context = c;
    }

    public dBaseManager_When open() throws SQLException {
        dbHelper = new dBaseArchitecture_When(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(String tags, long tags_count) {
        ContentValues insertContentValue = new ContentValues();
        insertContentValue.put(dBaseArchitecture_When.COL_WHEN, tags);
        insertContentValue.put(dBaseArchitecture_When.COL_WHEN_COUNT, tags_count);
        return database.insert(dBaseArchitecture_When.TABLE_NAME_WHEN, null, insertContentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{dBaseArchitecture_When._ID_WHEN,
                dBaseArchitecture_When.COL_WHEN,
                dBaseArchitecture_When.COL_WHEN_COUNT};
        Cursor cursor = database.query(dBaseArchitecture_When.TABLE_NAME_WHEN, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void deleteAll(){
        database.delete(dBaseArchitecture_When.TABLE_NAME_WHEN, null, null);
    }
}
