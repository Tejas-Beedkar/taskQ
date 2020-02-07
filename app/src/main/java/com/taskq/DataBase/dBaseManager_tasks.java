package com.taskq.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class dBaseManager_tasks {

    private dBaseArchitecture_tasks dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public dBaseManager_tasks(Context c) {
        context = c;
    }

    public dBaseManager_tasks open() throws SQLException {
        dbHelper = new dBaseArchitecture_tasks(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(String task_parent, String task_status, String task_description) {
        ContentValues insertContentValue = new ContentValues();

        insertContentValue.put(dBaseArchitecture_tasks.COL_TASK_PARENT, task_parent);
        insertContentValue.put(dBaseArchitecture_tasks.COL_TASK_STATUS, task_status);
        insertContentValue.put(dBaseArchitecture_tasks.COL_TASK_DESCRIPTION, task_description);

        return database.insert(dBaseArchitecture_tasks.TABLE_NAME, null, insertContentValue);
    }

    public void delete(long _id) {
        database.delete(dBaseArchitecture_tasks.TABLE_NAME, dBaseArchitecture_tasks._ID + "=" + _id, null);
    }

    public Cursor fetch() {
        String[] columns = new String[] {   dBaseArchitecture_tasks._ID,
                                            dBaseArchitecture_tasks.COL_TASK_PARENT,
                                            dBaseArchitecture_tasks.COL_TASK_STATUS,
                                            dBaseArchitecture_tasks.COL_TASK_DESCRIPTION};
        Cursor cursor = database.query(dBaseArchitecture_tasks.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetch_ID(long _id) {
        String selection = "SELECT * FROM " + dBaseArchitecture_tasks.TABLE_NAME + " WHERE " + dBaseArchitecture_tasks._ID + " = " + _id;
        Cursor cursor = database.rawQuery(selection, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update_status(long _id, String task_status) {

        ContentValues updateContentValue = new ContentValues();
        updateContentValue.put(dBaseArchitecture_tasks.COL_TASK_STATUS, task_status);
        return database.update(dBaseArchitecture_tasks.TABLE_NAME, updateContentValue, dBaseArchitecture_tasks._ID + " = " + _id, null);
    }

    public int update_Description(long _id, String task_description) {
        ContentValues updateContentValue = new ContentValues();
        updateContentValue.put(dBaseArchitecture_tasks.COL_TASK_DESCRIPTION, task_description);
        return database.update(dBaseArchitecture_tasks.TABLE_NAME, updateContentValue, dBaseArchitecture_tasks._ID + " = " + _id, null);
    }

    public Cursor fetch_EntryById(long _id) {

            String selection = "SELECT * FROM " + dBaseArchitecture_tasks.TABLE_NAME + " WHERE instr(" + dBaseArchitecture_tasks.COL_TASK_PARENT + ", " + "'" + String.valueOf(_id) + "' ) > 0 " + "ORDER BY _ID DESC";

            Cursor cursor = database.rawQuery(selection, null);

            if (cursor != null) {
                cursor.moveToFirst();
            }
            return cursor;
    }


}
