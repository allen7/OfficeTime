package com.rec.kuciaba.andrzej.officetime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by wro00669 on 2015-01-23.
 */
public class Notify {

    SimpleDateFormat m_timeFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
    SimpleDateFormat m_dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public void manageNotification(Context context, long m_startDay, long m_endDay, long m_shiftTime){
        Calendar startTime = Calendar.getInstance();
        startTime.setTimeInMillis(m_startDay);

        long timeLeft = (m_startDay + m_shiftTime - m_endDay)/60000;
        int hoursLeft = (int)timeLeft/60;
        int minsLeft = (int)(timeLeft-hoursLeft*60);

        String title = "Time left: " + hoursLeft + ":" + (minsLeft<10?"0":"") +minsLeft;
        String text = "Work start: " + m_timeFormat.format(startTime.getTime());

        popUpNotification(context, title, text, 1, R.drawable.ic_launcher);

    }

    public void manageOvertimeNotification(Context context, long m_startDay, long m_endDay, long m_shiftTime){
        Calendar startTime = Calendar.getInstance();
        long now = startTime.getTimeInMillis();
        startTime.setTimeInMillis(m_startDay);

        long overtime = (now - m_startDay - m_shiftTime)/60000;
        int hoursOvertime = (int)overtime/60;
        int minsOvertime = (int)(overtime-hoursOvertime*60);

        String title = "Overtime: " + hoursOvertime + ":" + (minsOvertime<10?"0":"") +minsOvertime;
        String text = "Work start: " + m_timeFormat.format(startTime.getTime());

        popUpNotification(context, title, text, 1, R.drawable.overtime);
    }

    public void popUpNotification(Context context, String title, String contextText, int id, int icon){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(icon);

        mBuilder.setContentTitle(title);

        mBuilder.setContentText(contextText);

        Intent resultInt = new Intent(context, TimeBrowser.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(TimeBrowser.class);

        stackBuilder.addNextIntent(resultInt);
        PendingIntent resultPendingIntend = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntend);

        NotificationManager mNotficationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notice = mBuilder.build();
        mNotficationManager.notify(id, notice);
    }

    public void clearNotification(Context context){
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(1);
        mNotificationManager.cancel(2);
    }
}
