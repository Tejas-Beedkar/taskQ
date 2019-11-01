package com.taskq.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import androidx.core.app.AlarmManagerCompat;
import android.widget.Toast;

import com.taskq.DataBase.dBaseArchitecture;
import com.taskq.DataBase.dBaseManager;

public class eventLibrary {

    private Context context;
    private dBaseManager dbManager;

    public eventLibrary(Context c) {
        context = c;
    }

    public boolean scheduleAllEvents(String strTrigger){

        Cursor cursor;
        boolean bRetValue = true;

        //==============================================================================================
        // ToDo: Feature - 019 Schedule all upcomming events using this root call.
        //==============================================================================================
        dbManager = new dBaseManager(context);
        dbManager.open();

        //Step 1 - Get the entire database of non completed items.
        //         Then call the scheduleEvent function to schedule all of them
        //ToDo: not sure if we need to schedule future events that have been marked completed.
        cursor = dbManager.fetch_NoCompleted();
        try {
            //This for loop will go through every member of the table
            for(cursor.moveToFirst() ; !cursor.isAfterLast() ; cursor.moveToNext()){
                //Check if we Reminders are enabled for this task.
                if( (cursor.getString(cursor.getColumnIndex(dBaseArchitecture.COL_SET_REMINDER))).equals(String.valueOf(true))) {
                    //Pass the date and time to scheduleEvent so that it can create a alarm and receiver.
                    if (!scheduleEvent(cursor.getLong(cursor.getColumnIndex(dBaseArchitecture._ID)), cursor.getLong(cursor.getColumnIndex(dBaseArchitecture.COL_WHEN_TIME)))){
                        bRetValue = false;}
                }
            }
        } catch (Exception e) {
            //Catch any exceptions
            Toast.makeText(context, "taskQ Error 019a" , Toast.LENGTH_LONG).show();
        }

        dbManager.close();
        //Toast.makeText(context, "taskQ onReceive Trigger" + strTrigger, Toast.LENGTH_LONG).show();
        return bRetValue;
    }

    public boolean scheduleEvent(long EventId, long lEventTriggerTime) {

        boolean bRetValue = true;

        //==============================================================================================
        // ToDo: Feature - 020 Schedule the event as long as it is a future event. Reject past events
        //==============================================================================================
        PendingIntent pendingIntent = makeNotificationIntent(context, EventId);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        try{
            AlarmManagerCompat.setExactAndAllowWhileIdle(alarmManager, AlarmManager.RTC_WAKEUP, lEventTriggerTime, pendingIntent);
        }catch (Exception e){
            Toast.makeText(context, "taskQ Error 019b" , Toast.LENGTH_LONG).show();
            e.printStackTrace();
            bRetValue=false;
        }
        return bRetValue;
    }

    public boolean removeEvent(long EventId, long lEventTriggerTime) {

        boolean bRetValue = true;

        //==============================================================================================
        // ToDo: Feature - 021 Remove alarm of a event. Other option is to supress to notification and let this event fire.
        //==============================================================================================
        PendingIntent pendingIntent = makeNotificationIntent(context, EventId);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        try{
            alarmManager.cancel (pendingIntent);
        }catch (Exception e){
            Toast.makeText(context, "taskQ Error 019c" , Toast.LENGTH_LONG).show();
            e.printStackTrace();
            bRetValue=false;
        }
        return bRetValue;
    }

    private PendingIntent makeNotificationIntent(Context context, long EventId){

        Intent intent = new Intent(context, receiverNotification.class);
        intent.putExtra("EventId", String.valueOf(EventId));
        return PendingIntent.getBroadcast(context, Integer.parseInt(String.valueOf(EventId)), intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

}

























