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
//        Intent intent = new Intent(arg0,service.class);
//        arg0.startService(intent);
//        Log.e("AK__", "started");
        if (arg1.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
//            Intent mServiceIntent = new Intent(arg0, RECTimeService.class);
//            arg0.startService(mServiceIntent);
            brChecker checker = new brChecker();
            checker.setOperation(arg0, 1);
        }
//        Intent mA = new Intent(arg0, MainActivity.class)
//                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //mServiceIntent.setData(Uri.parse());

//        arg0.startActivity(mA);


//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(arg0);
//        mBuilder.setSmallIcon(R.drawable.ic_launcher);
//        mBuilder.setContentTitle("Alert2");
//        mBuilder.setContentText("Text2");
//
//        Intent resultInt = new Intent(arg0, NotifyActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(arg0);
//        stackBuilder.addParentStack(NotifyActivity.class);
//
//        stackBuilder.addNextIntent(resultInt);
//        PendingIntent resultPendingIntend = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(resultPendingIntend);
//
//        NotificationManager mNotficationManager = (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
//        int id = 1;
//        mNotficationManager.notify(id, mBuilder.build());
    }
}
