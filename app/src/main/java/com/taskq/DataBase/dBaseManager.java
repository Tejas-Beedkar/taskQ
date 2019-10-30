package com.taskq.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

//==========================================================================================
//          Feature 12 - Main database of all entries (interfaces)
//==========================================================================================
public class dBaseManager {

    private dBaseArchitecture dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public dBaseManager(Context c) {
        context = c;
    }

    public dBaseManager open() throws SQLException {
        dbHelper = new dBaseArchitecture(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(String task, String tags, String names, String description, long time, String status, String setReminder) {
        ContentValues insertContentValue = new ContentValues();

        insertContentValue.put(dBaseArchitecture.COL_TASK, task);
        insertContentValue.put(dBaseArchitecture.COL_TAGS, tags);
        insertContentValue.put(dBaseArchitecture.COL_NAMES, names);
        insertContentValue.put(dBaseArchitecture.COL_DESCRIPTION, description);
        insertContentValue.put(dBaseArchitecture.COL_WHEN_TIME, time);
        insertContentValue.put(dBaseArchitecture.COL_STATUS, status);
        insertContentValue.put(dBaseArchitecture.COL_SET_REMINDER, setReminder);

        return database.insert(dBaseArchitecture.TABLE_NAME, null, insertContentValue);
    }

    public int update(long _id, String task, String tags, String names, String description, long time, String status, String setReminder) {
        ContentValues updateContentValue = new ContentValues();

        updateContentValue.put(dBaseArchitecture.COL_TASK, task);
        updateContentValue.put(dBaseArchitecture.COL_TAGS, tags);
        updateContentValue.put(dBaseArchitecture.COL_NAMES, names);
        updateContentValue.put(dBaseArchitecture.COL_DESCRIPTION, description);
        updateContentValue.put(dBaseArchitecture.COL_WHEN_TIME, time);
        updateContentValue.put(dBaseArchitecture.COL_STATUS, status);
        updateContentValue.put(dBaseArchitecture.COL_SET_REMINDER, setReminder);

        return database.update(dBaseArchitecture.TABLE_NAME, updateContentValue, dBaseArchitecture._ID + " = " + _id, null);
    }

    public int update_disableReminder(long _id) {

        ContentValues updateContentValue = new ContentValues();

        updateContentValue.put(dBaseArchitecture.COL_SET_REMINDER, String.valueOf(false));

        return database.update(dBaseArchitecture.TABLE_NAME, updateContentValue, dBaseArchitecture._ID + " = " + _id, null);
    }

    public void delete(long _id) {
        database.delete(dBaseArchitecture.TABLE_NAME, dBaseArchitecture._ID + "=" + _id, null);
    }

    public Cursor fetch() {
        String[] columns = new String[] {   dBaseArchitecture._ID,
                dBaseArchitecture.COL_TASK,
                dBaseArchitecture.COL_TAGS,
                dBaseArchitecture.COL_NAMES,
                dBaseArchitecture.COL_DESCRIPTION,
                dBaseArchitecture.COL_WHEN_TIME,
                dBaseArchitecture.COL_STATUS,
                dBaseArchitecture.COL_SET_REMINDER};
        Cursor cursor = database.query(dBaseArchitecture.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetch_NoCompleted() {
        String selection = "SELECT * FROM " + dBaseArchitecture.TABLE_NAME + " WHERE instr(" + dBaseArchitecture.COL_STATUS + ", " + "'" + String.valueOf(false) + "' ) > 0";
        Cursor cursor = database.rawQuery(selection, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetch_EntryById(long _id) {
        String selection = "SELECT * FROM " + dBaseArchitecture.TABLE_NAME + " WHERE " + dBaseArchitecture._ID + " = " + _id;
        Cursor cursor = database.rawQuery(selection, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchEntryByTag(String strTag){
        String selection = "SELECT * FROM " + dBaseArchitecture.TABLE_NAME + " WHERE instr(" + dBaseArchitecture.COL_TAGS + ", " + "'" + strTag + "' ) > 0";
        Cursor cursor = database.rawQuery(selection, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchEntryByTag_NoCompleted(String strTag){
        String selection = "SELECT * FROM " + dBaseArchitecture.TABLE_NAME + " WHERE instr(" + dBaseArchitecture.COL_TAGS + ", " + "'" + strTag + "' ) > 0"  + " AND instr(" + dBaseArchitecture.COL_STATUS + ", " + "'" + String.valueOf(false) + "' ) > 0";
        Cursor cursor = database.rawQuery(selection, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchEntryByName(String strName){
        String selection = "SELECT * FROM " + dBaseArchitecture.TABLE_NAME + " WHERE instr(" + dBaseArchitecture.COL_NAMES + ", " + "'" + strName + "' ) > 0";
        Cursor cursor = database.rawQuery(selection, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchEntryByName_NoCompleted(String strName){
        String selection = "SELECT * FROM " + dBaseArchitecture.TABLE_NAME + " WHERE instr(" + dBaseArchitecture.COL_NAMES + ", " + "'" + strName + "' ) > 0" + " AND instr(" + dBaseArchitecture.COL_STATUS + ", " + "'" + String.valueOf(false) + "' ) > 0";;
        Cursor cursor = database.rawQuery(selection, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchEntryByWhen(long lStartDate, long lEndDate){
        Cursor cursor;
        String selection = "SELECT * FROM " + dBaseArchitecture.TABLE_NAME +" WHERE " + dBaseArchitecture.COL_WHEN_TIME + " BETWEEN " + lStartDate + " AND " + lEndDate;
        cursor = database.rawQuery(selection, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchEntryByWhen_NoCompleted(long lStartDate, long lEndDate){
        Cursor cursor;
        String selection = "SELECT * FROM " + dBaseArchitecture.TABLE_NAME +" WHERE " + dBaseArchitecture.COL_WHEN_TIME + " BETWEEN " + lStartDate + " AND " + lEndDate + " AND instr(" + dBaseArchitecture.COL_STATUS + ", " + "'" + String.valueOf(false) + "' ) > 0";
        cursor = database.rawQuery(selection, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
