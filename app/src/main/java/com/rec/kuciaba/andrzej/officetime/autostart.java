package com.rec.kuciaba.andrzej.officetime;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class autostart extends BroadcastReceiver
{
    public void onReceive(Context arg0, Intent arg1)
    {
        if (arg1.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
//            Intent mServiceIntent = new Intent(arg0, RECTimeService.class);
//            arg0.startService(mServiceIntent);
            brChecker checker = new brChecker();
            checker.setOperation(arg0, 1);
        }
    }
}
