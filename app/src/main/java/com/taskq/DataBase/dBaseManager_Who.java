package com.taskq.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class dBaseManager_Who {

    private dBaseArchitecture_Who dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public dBaseManager_Who(Context c) {
        context = c;
    }

    public dBaseManager_Who open() throws SQLException {
        dbHelper = new dBaseArchitecture_Who(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(String names, long namescount){
        ContentValues insertContentValue = new ContentValues();
        insertContentValue.put(dBaseArchitecture_Who.COL_WHO, names);
        insertContentValue.put(dBaseArchitecture_Who.COL_WHO_COUNT, namescount);
        return database.insert(dBaseArchitecture_Who.TABLE_NAME_WHO, null, insertContentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] {   dBaseArchitecture_Who._ID_WHO,
                dBaseArchitecture_Who.COL_WHO,
                dBaseArchitecture_Who.COL_WHO_COUNT };
        Cursor cursor = database.query(dBaseArchitecture_Who.TABLE_NAME_WHO, columns, null, null, null, null, dBaseArchitecture_Who.COL_WHO_COUNT+" DESC");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void deleteAll(){
        database.delete(dBaseArchitecture_Who.TABLE_NAME_WHO, null, null);
    }

}
