package com.taskq.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class dBaseManager_What {

    private dBaseArchitecture_What dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public dBaseManager_What(Context c) {
        context = c;
    }

    public dBaseManager_What open() throws SQLException {
        dbHelper = new dBaseArchitecture_What(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(String tags, long tags_count) {
        ContentValues insertContentValue = new ContentValues();
        insertContentValue.put(dBaseArchitecture_What.COL_WHAT, tags);
        insertContentValue.put(dBaseArchitecture_What.COL_WHAT_COUNT, tags_count);
        return database.insert(dBaseArchitecture_What.TABLE_NAME_WHAT, null, insertContentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] {   dBaseArchitecture_What._ID_WHAT,
                dBaseArchitecture_What.COL_WHAT,
                dBaseArchitecture_What.COL_WHAT_COUNT };

        Cursor cursor = database.query(dBaseArchitecture_What.TABLE_NAME_WHAT, columns, null, null, null, null, dBaseArchitecture_What.COL_WHAT_COUNT+" DESC");

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public void deleteAll(){
        database.delete(dBaseArchitecture_What.TABLE_NAME_WHAT, null, null);
    }
}
