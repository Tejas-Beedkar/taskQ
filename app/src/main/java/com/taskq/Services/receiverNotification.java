package com.taskq.Services;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.taskq.DataBase.dBaseArchitecture;
import com.taskq.DataBase.dBaseManager;
import com.taskq.R;

import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;

public class receiverNotification extends BroadcastReceiver {

    private static String strEventId;

    @Override
    public void onReceive(Context context, Intent intent){
        //==============================================================================================
        // ToDo: Feature - 022 There is where we send notifications from.
        //==============================================================================================
        strEventId = intent.getStringExtra("EventId");

        // The NotificationChannel class is not supported below API 26
        // Recalling this function has no impact after first Call.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            CharSequence strChannelName = context.getText(R.string.Channel_Name);
            String strChannelDescription =  context.getResources().getString(R.string.Channel_Description);
            int intChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel chNotificationChannel = new NotificationChannel(context.getResources().getString(R.string.Channel_ID), strChannelName, intChannelImportance);
            chNotificationChannel.setDescription(strChannelDescription);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(chNotificationChannel);
        }

        //==============================================================================================
        // ToDo: Feature - 023 Construct a meaningful notification.
        //============================================================================================
        dBaseManager dbManager;
        dbManager = new dBaseManager(context);
        dbManager.open();
        Cursor cursor = dbManager.fetch_EntryById(Long.parseLong(strEventId));
        String strTaskDescription = cursor.getString(cursor.getColumnIndex(dBaseArchitecture.COL_TASK));
        String strTaskNames = cursor.getString(cursor.getColumnIndex(dBaseArchitecture.COL_NAMES));
        String strTaskTags = cursor.getString(cursor.getColumnIndex(dBaseArchitecture.COL_TAGS));
        if(strTaskNames.isEmpty()){
            strTaskNames = "Someone";
        }
        if(strTaskTags.isEmpty()){
            strTaskTags = "something";
        }
        String strNotification_Message = strTaskNames + " need(s) you to " + strTaskDescription + " to get " + strTaskTags + " done.";

        //Disable the Reminder. This way it does not pop up again every time the Tab Activity is created.
        dbManager.update_disableReminder(Long.parseLong(strEventId));

        if(Boolean.getBoolean( cursor.getString(cursor.getColumnIndex(dBaseArchitecture.COL_STATUS))) == true){
            //This item was closed after the notification was set. Do not trigger.
            dbManager.close();
            return;
        }

        dbManager.close();

        Intent intentLaunch = new Intent(context, receiverNotificationAction.class);
        intentLaunch.putExtra("EventId",strEventId);
        intentLaunch.putExtra("EventIdOrder",context.getResources().getString(R.string.Open_caps));
        PendingIntent pendingLaunch = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intentLaunch, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentDismiss = new Intent(context, receiverNotificationAction.class);
        intentDismiss.putExtra("EventId",strEventId);
        intentDismiss.putExtra("EventIdOrder",context.getResources().getString(R.string.Dismiss_caps));
        PendingIntent pendingDismiss = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intentDismiss, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, context.getResources().getString(R.string.Channel_ID) )
                .setStyle(new NotificationCompat.BigTextStyle().bigText(strNotification_Message))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingLaunch)
                .setAutoCancel(true)
                .setVisibility(VISIBILITY_PUBLIC)
                .addAction(R.drawable.ic_notification, context.getResources().getString(R.string.Open_caps), pendingLaunch)
                .addAction(R.drawable.ic_notification, context.getResources().getString(R.string.Dismiss_caps), pendingDismiss);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        try {
            notificationManager.notify(Integer.parseInt(strEventId), mBuilder.build());
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
