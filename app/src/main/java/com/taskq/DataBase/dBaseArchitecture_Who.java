package com.taskq.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dBaseArchitecture_Who extends SQLiteOpenHelper {

    //Table properties
    private static final int DB_VERSION_WHO = 1;
    public static final String DB_NAME_WHO = "taskQ_database_Who.DB";

    //Table contents
    public static final String _ID_WHO = "_id";
    public static final String TABLE_NAME_WHO = "taskQ_tablewho";
    public static final String COL_WHO = "taskQ_names_who";
    public static final String COL_WHO_COUNT = "taskQ_names_who_count";

    //Table Creation
    private static final String CREATE_TABLE_WHO = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME_WHO + " (" +
            _ID_WHO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_WHO + " TEXT, " +
            COL_WHO_COUNT + " INTEGER" + ")";

    public dBaseArchitecture_Who(Context context) {
        super(context, DB_NAME_WHO, null, DB_VERSION_WHO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WHO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_WHO);
        onCreate(db);
    }
}
