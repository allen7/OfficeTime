package com.rec.kuciaba.andrzej.officetime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brChecker checker = new brChecker();
        checker.setOperation(getBaseContext(), 1);

//        Intent mServiceIntent = new Intent(getApplicationContext(), RECTimeService.class);
//        //mServiceIntent.setData(Uri.parse());
//        startService(mServiceIntent);

//        ComponentName receiver = new ComponentName(this,autostart.class);
//        PackageManager pm = this.getPackageManager();
//        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

//        brChecker checker = new brChecker();
//        checker.setOperation(this);


//        Intent alarmIntent = new Intent(this, brChecker.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
//
//        AlarmManager manager = (AlarmManager) getSystemService(this.getBaseContext().ALARM_SERVICE);
//        int interval = 10000;
//
//        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
//        Toast.makeText(this, "Alarm Set AK_", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();




//        ComponentName receiver = new ComponentName(this,autostart.class);
//        PackageManager pm = this.getPackageManager();
//        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
//        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
