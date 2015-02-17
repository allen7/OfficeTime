package com.rec.kuciaba.andrzej.officetime;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wro00669 on 2015-01-21.
 */
public class brChecker extends BroadcastReceiver{

    public brChecker(){
        Log.i("AK__", "AK_constructor");
    }

    private Map<Integer,Integer> m_intervals;
    private Boolean m_sathurday = false;
    private Boolean m_sunday = false;
    private long m_startDay = 0;
    private long m_endDay = 0;
    private static final long m_shiftTime = 28800000;
    //    private static final long m_shiftTime = 5*60*1000;
//    private int m_lastCheckMinute = -1;
//    private Boolean workingLoop = true;
    DBHelper m_dhHelper = null;
    SimpleDateFormat m_timeFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
    SimpleDateFormat m_dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    Notify mNotify = new Notify();

    private Context mContext;

    Integer count = 0;

    public void init(Context context    ){
        m_dhHelper = new DBHelper(context);
        SharedPreferences pref = context.getSharedPreferences(context.getApplicationContext().getString(R.string.conf), context.getApplicationContext().MODE_PRIVATE);
        m_sathurday = pref.getBoolean(context.getApplicationContext().getString(R.string.saturday), false);
        m_sunday = pref.getBoolean(context.getApplicationContext().getString(R.string.sunday), false);

        Calendar now = Calendar.getInstance();
        m_startDay = m_dhHelper.getDateTime(m_dateFormat.format(now.getTime()), 0);
        m_endDay = m_dhHelper.getDateTime(m_dateFormat.format(now.getTime()), 1);
        Calendar lastLogin = Calendar.getInstance();
        lastLogin.setTimeInMillis(m_startDay);
        boolean loggedIn = now.get(Calendar.DAY_OF_YEAR)== lastLogin.get(Calendar.DAY_OF_YEAR);
        m_intervals = new HashMap();
        if(loggedIn) {
            for (int i = 0; i < 23; ++i) {
                if (i <= 6) {
                    m_intervals.put(i, pref.getInt(context.getApplicationContext().getString(R.string.interval0_6_in_value), 5));
                } else if (i >= 7 && i <= 10) {
                    m_intervals.put(i, pref.getInt(context.getApplicationContext().getString(R.string.interval7_10_in_value), 5));
                } else if (i >= 11 && i <= 14) {
                    m_intervals.put(i, pref.getInt(context.getApplicationContext().getString(R.string.interval11_14_in_value), 5));
                } else if (i >= 15 && i <= 18) {
                    m_intervals.put(i, pref.getInt(context.getApplicationContext().getString(R.string.interval15_18_in_value), 5));
                } else if (i >= 19) {
                    m_intervals.put(i, pref.getInt(context.getApplicationContext().getString(R.string.interval19_23_in_value), 5));
                }
            }
        }
        else {
            for (int i = 0; i < 23; ++i) {
                if (i <= 6) {
                    m_intervals.put(i, pref.getInt(context.getApplicationContext().getString(R.string.interval0_6_out_value), 5));
                } else if (i >= 7 && i <= 10) {
                    m_intervals.put(i, pref.getInt(context.getApplicationContext().getString(R.string.interval7_10_out_value), 5));
                } else if (i >= 11 && i <= 14) {
                    m_intervals.put(i, pref.getInt(context.getApplicationContext().getString(R.string.interval11_14_out_value), 5));
                } else if (i >= 15 && i <= 18) {
                    m_intervals.put(i, pref.getInt(context.getApplicationContext().getString(R.string.interval15_18_in_value), 5));
                } else if (i >= 19) {
                    m_intervals.put(i, pref.getInt(context.getApplicationContext().getString(R.string.interval19_23_out_value), 5));
                }
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent){
        try {
            Log.i("AK__", "AK_onReceive");
            init(context);
            Calendar now = Calendar.getInstance();
            int seconds = now.get(Calendar.SECOND);
            int minutes = now.get(Calendar.MINUTE);
            int hour = now.get(Calendar.HOUR_OF_DAY);

            try {

                mContext = context;


                if (!(Calendar.SUNDAY == now.get(Calendar.DAY_OF_WEEK) && !m_sunday || Calendar.SATURDAY == now.get(Calendar.DAY_OF_WEEK) && !m_sathurday)) {
                    if ((m_intervals.get(hour) != 0 && minutes % m_intervals.get(hour) == 0) || true) {
                        WifiChecker wifi = new WifiChecker();
                        Boolean networkFound = wifi.findRECNetwork(mContext);
                        if (networkFound) {
                            Calendar past = Calendar.getInstance();
                            past.setTimeInMillis(m_startDay);
                            if (!(now.get(Calendar.YEAR) == past.get(Calendar.YEAR) && now.get(Calendar.DAY_OF_YEAR) == past.get(Calendar.DAY_OF_YEAR))) {
                                m_startDay = now.getTimeInMillis();
                                m_dhHelper.addTime(m_startDay, m_dateFormat.format(now.getTime()), 0);
                                m_dhHelper.addTime(m_startDay, m_dateFormat.format(now.getTime()), 1);

                            } else {
                                m_dhHelper.updateTime(now.getTimeInMillis(), m_dateFormat.format(now.getTime()), 1);
                            }
                            m_endDay = now.getTimeInMillis();
                        }
                        if (!networkFound) {
                            mNotify.clearNotification(mContext);
                            if (m_startDay != 0 && m_shiftTime - (m_endDay - m_startDay) > m_shiftTime * 0.1 && now.getTimeInMillis() - m_startDay > m_shiftTime * 1.1) {
                                mNotify.manageNotification(mContext, m_startDay, m_endDay, m_shiftTime);
                            }
                        } else {
                            if (m_startDay != 0 && m_endDay - m_startDay <= m_shiftTime) {
                                mNotify.manageNotification(mContext, m_startDay, m_endDay, m_shiftTime);
                            } else if (m_endDay - m_startDay > m_shiftTime) {
                                mNotify.manageOvertimeNotification(mContext, m_startDay, m_endDay, m_shiftTime);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("officetitme::onReceive", "AK_ exception " + e.toString());
            }
            if (m_intervals.get(hour) != 0 && minutes + m_intervals.get(hour) < 60 && (!(Calendar.SUNDAY == now.get(Calendar.DAY_OF_WEEK) && !m_sunday || Calendar.SATURDAY == now.get(Calendar.DAY_OF_WEEK) && !m_sathurday))) {
                setOperation(mContext, m_intervals.get(hour));
//                Log.e("AK__", "time " + m_intervals.get(hour));
//                String text = "Update: " + m_timeFormat.format(now.getTime());
//                popUpNotification(mContext, "Running " + m_intervals.get(hour), text, 2, R.drawable.overtime);
            } else if (minutes + 15 > 59) {
                setOperation(mContext, m_intervals.get(60 - minutes));
//                Log.e("AK__", "time " + m_intervals.get(60 - minutes));
//                String text = "Update: " + m_timeFormat.format(now.getTime());
//                popUpNotification(mContext, "Running " + (60 - minutes), text, 2, R.drawable.overtime);
            } else {
                setOperation(mContext, (15));
//                Log.e("AK__", "time 0  " + (15));
//                String text = "Update: " + m_timeFormat.format(now.getTime());
//                popUpNotification(mContext, "Running " + 15, text, 2, R.drawable.overtime);
            }


//        Toast.makeText(context, "Alarm call AK_", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            setOperation(mContext, 1);
        }
    }

    public void setOperation(Context context, long time){
        Log.i("AK__", "AK_setOperation");
        Log.i("AK__", "AK_setOperation "+ time);
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, brChecker.class);
        context.getApplicationContext().registerReceiver(this, new IntentFilter("com.blah.blah.somemessage"));
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60, pi); // Millisec * Second * Minute
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+time*60000, pi);
    }


//    public void popUpNotification(Context context, String title, String contextText, int id, int icon){
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
//        mBuilder.setSmallIcon(icon);
//
//        mBuilder.setContentTitle(title);
////        mBuilder.setLargeIcon(generateIcon());
//
//        mBuilder.setContentText(contextText);
//
//        Intent resultInt = new Intent(context, NotifyActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(NotifyActivity.class);
//
//        stackBuilder.addNextIntent(resultInt);
//        PendingIntent resultPendingIntend = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(resultPendingIntend);
//
//        NotificationManager mNotficationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
////        mNotficationManager.notify(id, mBuilder.build());
//        Notification notice = mBuilder.build();
////        notice.flags |= Notification.FLAG_NO_CLEAR;
////        notice.flags |= Notification.FLAG_FOREGROUND_SERVICE;
////        notice.priority = Notification.PRIORITY_MAX;
//        mNotficationManager.notify(id, notice);
////        startForeground(id, notice);
//    }

}

