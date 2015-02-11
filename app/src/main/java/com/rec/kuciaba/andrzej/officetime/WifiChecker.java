package com.rec.kuciaba.andrzej.officetime;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wro00669 on 2015-01-23.
 */
public class WifiChecker {

    private List<ScanResult> findWiFis(Context context){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> networks = new ArrayList<ScanResult>();
        if(wifi.isWifiEnabled()) {

            wifi.startScan();
            networks = wifi.getScanResults();
            int in = 6;
        } else {
            wifi.setWifiEnabled(true);
            Log.i("AK__", "1 loop");
            for(int i =0; i<10;++i){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    continue;
                }
                if(wifi.getWifiState()==WifiManager.WIFI_STATE_ENABLED) break;
            }
            wifi.startScan();
            Log.i("AK__", "2 loop");
            for(int i =0; i<10;++i){
                networks = wifi.getScanResults();
                if(networks!= null && networks.size()>0){
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    continue;
                }
            }
            wifi.setWifiEnabled(false);
            Log.i("AK__", "3 loop");
        }
        Log.i("AK__", "4 loop");
        return networks;
    }

    public Boolean findRECNetwork(Context context){
        List<ScanResult> networks = findWiFis(context);
        Boolean inRecRange = false;
        if(networks!=null) {
            for (ScanResult net : networks) {
                if (net.SSID.contains("REC Network") || net.SSID.contains("REC Guest")) {
                    inRecRange = true;
                    break;
                }
            }
        }
        return inRecRange;
    }
}
