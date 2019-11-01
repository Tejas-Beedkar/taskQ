package com.taskq.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class receiverBoot extends BroadcastReceiver {

    private eventLibrary eventLib;

    @Override
    public void onReceive(Context context, Intent intent){

        eventLib = new eventLibrary(context);
        eventLib.scheduleAllEvents(intent.getAction());
        eventLib = null;
        System.gc();
    }
}
