package com.taskq.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.taskq.R;

//Dummy Edit for Git merge
public class taskQSettings {

    private static final String APP_SHARED_PREFS = "taskQPref";
    private static final String Switch_ShowCompleted ="switchShowCompleted";

    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    public taskQSettings(Context context)
    {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public boolean getSwitchShowCompleted()
    {
        return appSharedPrefs.getBoolean(Switch_ShowCompleted, false);
    }

    public void setSwitchShowCompleted(boolean status)
    {
        prefsEditor.putBoolean(Switch_ShowCompleted, status);
        prefsEditor.commit();
    }

}
