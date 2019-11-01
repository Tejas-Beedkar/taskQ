package com.taskq.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dBaseArchitecture_When extends SQLiteOpenHelper {

    //Table properties
    private static final int DB_VERSION_WHEN = 1;
    public static final String DB_NAME_WHEN = "taskQ_database_When.DB";

    //Table contents
    public static final String _ID_WHEN = "_id";
    public static final String TABLE_NAME_WHEN = "taskQ_tablewhen";
    public static final String COL_WHEN = "taskQ_dates_when";
    public static final String COL_WHEN_COUNT = "taskQ_dates_when_count";

    //Table Creation
    private static final String CREATE_TABLE_WHEN = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME_WHEN + " (" +
            _ID_WHEN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_WHEN + " TEXT, " +
            COL_WHEN_COUNT + " INTEGER" + ")";

    public dBaseArchitecture_When(Context context) {
        super(context, DB_NAME_WHEN, null, DB_VERSION_WHEN);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WHEN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_WHEN);
        onCreate(db);
    }
}
