//package com.rec.kuciaba.andrzej.officetime;
//
//import android.app.IntentService;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.TaskStackBuilder;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.wifi.ScanResult;
//import android.net.wifi.WifiManager;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//import static java.lang.Thread.*;
//
///**
// * Created by wro00669 on 2015-01-08.
// */
//public class RECTimeService extends IntentService {
//
//    private Map<Integer,Integer> m_intervals;
//    private Boolean m_sathurday = false;
//    private Boolean m_sunday = false;
//    private long m_startDay = 0;
//    private long m_endDay = 0;
//    private static final long m_shiftTime = 28800000;
////    private static final long m_shiftTime = 5*60*1000;
//    private int m_lastCheckMinute = -1;
//    private Boolean workingLoop = true;
//    DBHelper m_dhHelper = null;
//    SimpleDateFormat m_timeFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
//    SimpleDateFormat m_dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//
////    private static final long m_shiftTime = 120000;
//    /**
//     * Creates an IntentService.  Invoked by your subclass's constructor.
//     *
//     * @param name Used to name the worker thread, important only for debugging.
//     */
//    public RECTimeService(String name) {
//        super(name);
//        brChecker checker = new brChecker();
//        checker.setOperation(getBaseContext(), 1);
//        Log.i("AK__", "AK_RECTimeService");
//    }
//
//    public RECTimeService() {
//        super("RECTimeService");
//        brChecker checker = new brChecker();
//        checker.setOperation(getBaseContext(), 1);
//        Log.i("AK__", "AK_RECTimeService");
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        // We want this service to continue running until it is explicitly
//        // stopped, so return sticky.
//        super.onStartCommand(intent, flags, startId);
//
//        popUpNotification("OnstartCommand", "here", 3, R.drawable.overtime);
//        return START_STICKY;
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        clearNotification();
//        stopForeground(true);
//    }
//
//
////    public RECTimeService(String name) {
//////        super();
//////        mName = name;
////    }
//
//    public void init(){
//        Log.i("AK__", "AK_RECTimeService:init");
//        m_dhHelper = new DBHelper(this);
//        m_intervals = new HashMap();
//        for(int i = 0 ; i < 24; ++i){
//            if(i<6 || i >17){
//                m_intervals.put(i, 15);
//            }
//            if(i>=6 && i<=9){
//                m_intervals.put(i, 2);
//            }
//            else{
//                m_intervals.put(i, 2);
//            }
//
//        }
//        Calendar now = Calendar.getInstance();
//        m_startDay = m_dhHelper.getDateTime(m_dateFormat.format(now.getTime()), 0);
//        m_endDay = m_dhHelper.getDateTime(m_dateFormat.format(now.getTime()), 1);
//    }
//
//    @Override
//    protected void onHandleIntent(Intent workIntent) {
//        Log.i("AK__officetime", "AK_officetime 0");
//
//        while(true) {
//            Log.i("AK__officetime", "AK_officetime 1");
//            try {
//                Log.i("AK__officetime", "AK_officetime 2");
//                init();
//                Boolean checkRequired = true;
//                Log.i("AK__officetime", "AK_officetime 3");
//                while (workingLoop) {
//                    Log.i("AK__officetime", "AK_officetime 3.1");
//                    Calendar now = Calendar.getInstance();
//                    int seconds = now.get(Calendar.SECOND);
//                    int minutes = now.get(Calendar.MINUTE);
//                    int hour = now.get(Calendar.HOUR_OF_DAY);
//                    Log.i("AK__officetime", "AK_officetime 3.2");
//                    if (minutes != m_lastCheckMinute) {
//                        checkRequired = true;
//                        m_lastCheckMinute = minutes;
//                    }
//                    Log.i("AK__officetime", "AK_officetime 3.3");
//                    if (checkRequired && !(Calendar.SUNDAY == now.get(Calendar.DAY_OF_WEEK) && !m_sunday || Calendar.SATURDAY == now.get(Calendar.DAY_OF_WEEK) && !m_sathurday)) {
//                        Log.i("AK__officetime", "AK_officetime 3.3.1");
//
//                        if (m_intervals.get(hour) != 0 && minutes % m_intervals.get(hour) == 0) {
//                            Log.i("AK__officetime", "AK_officetime 3.3.1.1");
//                            checkRequired = false;
//                            List<ScanResult> networks = findWiFis();
//                            Boolean networkFound = findRECNetwork(networks);
//                            Log.i("AK__officetime", "AK_officetime 3.3.1.2");
//                            if (networkFound) {
//                                Log.i("AK__officetime", "AK_officetime 3.3.1.2.1");
//                                Calendar past = Calendar.getInstance();
//                                past.setTimeInMillis(m_startDay);
//                                if (!(now.get(Calendar.YEAR) == past.get(Calendar.YEAR) && now.get(Calendar.DAY_OF_YEAR) == past.get(Calendar.DAY_OF_YEAR))) {
////                            past.setTimeInMillis(now.getTimeInMillis());
////                            m_startDay = past.getTimeInMillis();
//                                    m_startDay = now.getTimeInMillis();
//                                    m_dhHelper.addTime(m_startDay, m_dateFormat.format(now.getTime()), 0);
//                                    m_dhHelper.addTime(m_startDay, m_dateFormat.format(now.getTime()), 1);
//
//                                } else {
//                                    m_dhHelper.updateTime(now.getTimeInMillis(), m_dateFormat.format(now.getTime()), 1);
//                                }
//                                m_endDay = now.getTimeInMillis();
//                                Log.i("AK__officetime", "AK_officetime 3.3.1.2.2");
//                            }
//                            Log.i("AK__officetime", "AK_officetime 3.3.1.3");
////                    m_dhHelper.addTime(m_startDay, m_dateFormat.format(now.getTime()),0);
//                            if (!networkFound) {
//                                clearNotification();
//                                if (m_startDay != 0 && m_shiftTime - (m_endDay - m_startDay) > m_shiftTime * 0.1 && now.getTimeInMillis() - m_startDay > m_shiftTime * 1.1) {
//                                    manageNotification();
//                                }
//                            } else {
//                                if (m_startDay != 0 && m_endDay - m_startDay <= m_shiftTime) {
//                                    manageNotification();
//                                } else if (m_endDay - m_startDay > m_shiftTime) {
//                                    manageOvertimeNotification();
//                                }
//                            }
//                            Log.i("AK__officetime", "AK_officetime 3.3.1.4");
//
//
////                    else if(m_startDay != 0 && m_endDay - m_startDay <= m_shiftTime - m_shiftTime*0.05){
////                        clearNotification();
////                    }
//                        }
//                        Log.i("AK__officetime", "AK_officetime 3.3.2");
//                    }
//                    Log.i("AK__officetime", "AK_officetime 3.4");
//                    String text = "Update: " + m_timeFormat.format(now.getTime());
////                    popUpNotification("Running " + seconds, text, 2, R.drawable.overtime);
//                    Log.i("AK__officetime", "AK_officetime 3.5");
//                    try {
//                        Log.i("AK__officetime", "AK_officetime 3.5.1");
//                        Thread.sleep(15000);
//                        Log.i("AK__officetime", "AK_officetime 3.5.1");
//                    } catch (InterruptedException e) {
//                        Log.i("AK__officetime", "AK_officetime 3.5_e");
//                        e.printStackTrace();
//                        continue;
//                    }
//
//
////            workingLoop = false;
//                }
//                Log.i("AK__officetime", "AK_officetime 4");
//            }
//            catch(Exception e){
//                Log.i("AK__officetime", "AK_officetime 4-e");
//                e.printStackTrace();
//                popUpNotification("Exception", e.toString(), 69, R.drawable.ic_launcher);
//                Log.e("AK__officetime", "Exception in RECTimeService::onHandleIntent loop "+e.getMessage());
//            }
//            Log.i("AK__officetime", "AK_officetime 5");
//            if(workingLoop == false){
//                break;
//            }
//        }
//        Log.i("AK__officetime", "AK_officetime 5");
//    }
//
//
//    private List<ScanResult> findWiFis(){
//        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//        List<ScanResult> networks = new ArrayList<ScanResult>();
//        if(wifi.isWifiEnabled()) {
//
//            wifi.startScan();
//            networks = wifi.getScanResults();
//            int in = 6;
//        } else {
//            wifi.setWifiEnabled(true);
//            Log.i("AK__", "1 loop");
//            for(int i =0; i<10;++i){
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    continue;
//                }
//                if(wifi.getWifiState()==WifiManager.WIFI_STATE_ENABLED) break;
//            }
//            wifi.startScan();
//            Log.i("AK__", "2 loop");
//            for(int i =0; i<10;++i){
//                networks = wifi.getScanResults();
//                if(networks!= null && networks.size()>0){
//                    break;
//                }
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    continue;
//                }
//            }
//            wifi.setWifiEnabled(false);
//            Log.i("AK__", "3 loop");
//        }
//        Log.i("AK__", "4 loop");
//        return networks;
//    }
//
//    private Boolean findRECNetwork(List<ScanResult> networks){
//        Boolean inRecRange = false;
//        if(networks!=null) {
//            for (ScanResult net : networks) {
//                if (net.SSID.contains("REC Network") || net.SSID.contains("REC Guest")) {
//                    inRecRange = true;
//                    break;
//                }
//            }
//        }
//        return inRecRange;
//    }
//
//    private void manageNotification(){
//        Calendar startTime = Calendar.getInstance();
////        long now = startTime.getTimeInMillis();
//        startTime.setTimeInMillis(m_startDay);
//
//        long timeLeft = (m_startDay + m_shiftTime - m_endDay)/60000;
//        int hoursLeft = (int)timeLeft/60;
//        int minsLeft = (int)(timeLeft-hoursLeft*60);
//
//        String title = "Time left: " + hoursLeft + ":" + (minsLeft<10?"0":"") +minsLeft;
////        SimpleDateFormat format = new SimpleDateFormat("d, yyyy 'at' h:mm a");
//        String text = "Work start: " + m_timeFormat.format(startTime.getTime());
//
//        popUpNotification(title, text, 1, R.drawable.ic_launcher);
//
//    }
//
//    private void manageOvertimeNotification(){
//        Calendar startTime = Calendar.getInstance();
//        long now = startTime.getTimeInMillis();
//        startTime.setTimeInMillis(m_startDay);
//
//        long overtime = (now - m_startDay - m_shiftTime)/60000;
//        int hoursOvertime = (int)overtime/60;
//        int minsOvertime = (int)(overtime-hoursOvertime*60);
//
//        String title = "Overtime: " + hoursOvertime + ":" + (minsOvertime<10?"0":"") +minsOvertime;
//        String text = "Work start: " + m_timeFormat.format(startTime.getTime());
//
//        popUpNotification(title, text, 1, R.drawable.overtime);
//    }
//
//    private void popUpNotification(String title, String contextText, int id, int icon){
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//        mBuilder.setSmallIcon(icon);
//
//        mBuilder.setContentTitle(title);
////        mBuilder.setLargeIcon(generateIcon());
//
//        mBuilder.setContentText(contextText);
//
//        Intent resultInt = new Intent(this, NotifyActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(NotifyActivity.class);
//
//        stackBuilder.addNextIntent(resultInt);
//        PendingIntent resultPendingIntend = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(resultPendingIntend);
//
//        NotificationManager mNotficationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////        mNotficationManager.notify(id, mBuilder.build());
//        Notification notice = mBuilder.build();
//        notice.flags |= Notification.FLAG_NO_CLEAR;
//        notice.flags |= Notification.FLAG_FOREGROUND_SERVICE;
//        notice.priority = Notification.PRIORITY_MAX;
//        mNotficationManager.notify(id, notice);
////        startForeground(id, notice);
//    }
//
//    private void clearNotification(){
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.cancel(1);
//        mNotificationManager.cancel(2);
//    }
//
//
//
////    priv;ate Bitmap generateIcon(String value, Boolean red){
////
////    }
//}
