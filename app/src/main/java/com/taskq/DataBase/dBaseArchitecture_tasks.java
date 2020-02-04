package com.taskq.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dBaseArchitecture_tasks extends SQLiteOpenHelper {

    //Table properties
    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "taskQ_database_tasks.DB";

    //Table contents
    public static final String _ID = "_id";
    public static final String TABLE_NAME = "taskQ_tabletasks";
    public static final String COL_TASK_PARENT = "Parent_ID";
    public static final String COL_TASK_STATUS = "false";
    public static final String COL_TASK_DESCRIPTION = "taskQ_description";

    private static final String CREATE_TABLE=   "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_TASK_PARENT + " TEXT, " +
            COL_TASK_STATUS + " TEXT, " +
            COL_TASK_DESCRIPTION + " TEXT " + ")";

    public dBaseArchitecture_tasks(Context context) {
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
