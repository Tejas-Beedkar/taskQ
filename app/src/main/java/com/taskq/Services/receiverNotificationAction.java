package com.taskq.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationManagerCompat;
import android.util.Log;
import com.taskq.Activities.taskQEntryActivity;
import com.taskq.CustomClasses.taskQGlobal;
import com.taskq.R;

public class receiverNotificationAction extends BroadcastReceiver {

    private static String strEventId;
    private static String strEventOrder;

    @Override
    public void onReceive(Context context, Intent intent){

        strEventId = intent.getStringExtra("EventId");
        strEventOrder = intent.getStringExtra("EventIdOrder");

        if(strEventOrder.equals(context.getResources().getString(R.string.Open_caps))){
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.cancel(Integer.parseInt(strEventId));

            Intent modifyIntent = new Intent(context, taskQEntryActivity.class);

            //Activity will not lauanch if the following flag is not set
            modifyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //ToDo : Replace ID modify with a string resource
            modifyIntent.putExtra("idModify", strEventId);
            context.startActivity(modifyIntent);
            Log.i("taskQ", "taskQ receiverNotificationAction : Open " + Integer.parseInt(strEventId));

        } else
        if(strEventOrder.equals(context.getResources().getString(R.string.Dismiss_caps))){
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.cancel(Integer.parseInt(strEventId));

//            dBaseManager dbManager;
//            dbManager = new dBaseManager(context);
//            dbManager.open();
//            dbManager.update_disableReminder(Long.parseLong(strEventId));
//            dbManager.close();

            eventLibrary eventLib = new eventLibrary(context);
            eventLib.removeEvent(Integer.parseInt(strEventId), 0l);
            eventLib = null;
            System.gc();

            Log.i("taskQ", "taskQ receiverNotificationAction : Dismiss " + Integer.parseInt(strEventId));
        }


    }
}
