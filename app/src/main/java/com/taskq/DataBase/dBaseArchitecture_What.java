package com.taskq.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dBaseArchitecture_What extends SQLiteOpenHelper {

    //Table properties
    private static final int DB_VERSION_WHAT = 1;
    public static final String DB_NAME_WHAT = "taskQ_database_What.DB";

    //Table contents
    public static final String _ID_WHAT = "_id";
    public static final String TABLE_NAME_WHAT = "taskQ_tablewhat";
    public static final String COL_WHAT = "taskQ_tags_what";
    public static final String COL_WHAT_COUNT = "taskQ_tags_what_count";

    //Table Creation
    private static final String CREATE_TABLE_WHAT = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME_WHAT + " (" +
            _ID_WHAT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_WHAT + " TEXT, " +
            COL_WHAT_COUNT + " INTEGER" + ")";

    public dBaseArchitecture_What(Context context) {
        super(context, DB_NAME_WHAT, null, DB_VERSION_WHAT);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WHAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_WHAT);
        onCreate(db);
    }
}
