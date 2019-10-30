package com.taskq.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dBaseArchitecture extends SQLiteOpenHelper {

    //Table properties
    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "taskQ_database.DB";

    //Table contents
    public static final String _ID = "_id";
    public static final String TABLE_NAME = "taskQ_table";
    public static final String COL_TASK = "taskQ_task";
    public static final String COL_TAGS = "taskQ_tags";
    public static final String COL_NAMES = "taskQ_names";
    public static final String COL_WHEN_TIME = "taskQ_time";
    public static final String COL_STATUS = "taskQ_status";
    public static final String COL_DESCRIPTION = "taskQ_description";
    public static final String COL_SET_REMINDER = "taskQ_setreminder";

    private static final String CREATE_TABLE=   "CREATE TABLE IF NOT EXISTS " +
                                                TABLE_NAME + " (" +
                                                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                COL_TASK + " TEXT, " +
                                                COL_TAGS + " TEXT, " +
                                                COL_NAMES + " TEXT, " +
                                                COL_WHEN_TIME + " INTEGER, " +
                                                COL_STATUS + " TEXT," +
                                                COL_DESCRIPTION + " TEXT," +
                                                COL_SET_REMINDER + " TEXT " + ")";

    public dBaseArchitecture(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 2) {
            //Do nothing for now.
        }
    }
}
