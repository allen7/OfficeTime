package com.rec.kuciaba.andrzej.officetime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Settings extends ActionBarActivity {

    private Boolean mConfigChanged = false;

    private Boolean mSaturday;
    private Boolean mSunday;
    private Set<String> mNetworks = new HashSet<String>(Arrays.asList("REC Network","REC Guest"));
    private Map<Integer,String> mBlankIntervals = new HashMap();
    private Map<Integer,Integer> mIntervalsValues = new HashMap();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button save_button = (Button) findViewById(R.id.button_save);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConfig();
                finish();
            }
        });


        Button cancel_button = (Button) findViewById(R.id.button_cancel);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((CheckBox)findViewById(R.id.checkbox_saturday)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConfigChanged = true;
            }
        });
        ((CheckBox)findViewById(R.id.checkbox_sunday)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConfigChanged = true;
            }
        });

        if(savedInstanceState==null){
            loadConfig();
        }
        else{
            mSaturday = ((CheckBox)findViewById(R.id.checkbox_saturday)).isChecked();
            mSunday = ((CheckBox)findViewById(R.id.checkbox_sunday)).isChecked();

            mIntervalsValues.put(R.id.interval_in_00_06, savedInstanceState.getInt(getString(R.string.interval0_6_in_value),5));
            mIntervalsValues.put(R.id.interval_in_07_10, savedInstanceState.getInt(getString(R.string.interval7_10_in_value),5));
            mIntervalsValues.put(R.id.interval_in_11_14, savedInstanceState.getInt(getString(R.string.interval11_14_in_value),5));
            mIntervalsValues.put(R.id.interval_in_15_18, savedInstanceState.getInt(getString(R.string.interval15_18_in_value),5));
            mIntervalsValues.put(R.id.interval_in_19_23, savedInstanceState.getInt(getString(R.string.interval19_23_in_value),5));

            mIntervalsValues.put(R.id.interval_out_00_06, savedInstanceState.getInt(getString(R.string.interval0_6_out_value),5));
            mIntervalsValues.put(R.id.interval_out_07_10, savedInstanceState.getInt(getString(R.string.interval7_10_out_value),5));
            mIntervalsValues.put(R.id.interval_out_11_14, savedInstanceState.getInt(getString(R.string.interval11_14_out_value),5));
            mIntervalsValues.put(R.id.interval_out_15_18, savedInstanceState.getInt(getString(R.string.interval15_18_out_value),5));
            mIntervalsValues.put(R.id.interval_out_19_23, savedInstanceState.getInt(getString(R.string.interval19_23_out_value),5));

            mConfigChanged = savedInstanceState.getBoolean(getString(R.string.drop_changes), false);
        }
        setValues();

//        NumberPicker np = (NumberPicker) findViewById(R.id.number_picker);
//        np.setMaxValue(100);
//        np.setMinValue(0);
//        np.setValue(33);

    }

//    private void setIntervalValue(int id, ){
//
//    }

    @SuppressWarnings("deprecation")
    public void intervalClicked(View v){
        final View text = v;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.number_picker_layout);
        dialog.setTitle("Interval for "+ mBlankIntervals.get(v.getId()));
        NumberPicker np = (NumberPicker) dialog.findViewById(R.id.number_picker);
        np.setMaxValue(30);
        np.setMinValue(0);
        np.setValue(mIntervalsValues.get(text.getId()));
        np.setWrapSelectorWheel(false);
        Button dialogOkButton = (Button)dialog.findViewById(R.id.ok);
        dialogOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntervalsValues.put(text.getId(), ((NumberPicker) dialog.findViewById(R.id.number_picker)).getValue());
                ((TextView)findViewById(text.getId())).setText(mBlankIntervals.get(text.getId())+" "+ mIntervalsValues.get(text.getId()));
//                ((TextView) findViewById(R.id.interval_in_00_06)).setText(R.string.);
                mConfigChanged = true;
                dialog.dismiss();
            }
        });
        Button dialogCancelButton = (Button)dialog.findViewById(R.id.cancel_button);
        dialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void saveConfig(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.conf), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(getString(R.string.saturday), ((CheckBox)findViewById(R.id.checkbox_saturday)).isChecked());
        editor.putBoolean(getString(R.string.sunday), ((CheckBox)findViewById(R.id.checkbox_sunday)).isChecked());
        editor.putInt(getString(R.string.interval0_6_in_value), mIntervalsValues.get(R.id.interval_in_00_06));
        editor.putInt(getString(R.string.interval7_10_in_value), mIntervalsValues.get(R.id.interval_in_07_10));
        editor.putInt(getString(R.string.interval11_14_in_value), mIntervalsValues.get(R.id.interval_in_11_14));
        editor.putInt(getString(R.string.interval15_18_in_value), mIntervalsValues.get(R.id.interval_in_15_18));
        editor.putInt(getString(R.string.interval19_23_in_value), mIntervalsValues.get(R.id.interval_in_19_23));
        editor.putInt(getString(R.string.interval0_6_out_value), mIntervalsValues.get(R.id.interval_out_00_06));
        editor.putInt(getString(R.string.interval7_10_out_value), mIntervalsValues.get(R.id.interval_out_07_10));
        editor.putInt(getString(R.string.interval11_14_out_value), mIntervalsValues.get(R.id.interval_out_11_14));
        editor.putInt(getString(R.string.interval15_18_out_value), mIntervalsValues.get(R.id.interval_out_15_18));
        editor.putInt(getString(R.string.interval19_23_out_value), mIntervalsValues.get(R.id.interval_out_19_23));
        editor.commit();
        mConfigChanged = false;
    }

    private void setValues(){
        setBoolValueOfItem(R.id.checkbox_saturday, mSaturday);
        setBoolValueOfItem(R.id.checkbox_sunday, mSunday);

        TextView networks = (TextView) findViewById(R.id.list_of_networks);
        networks.setText("");
        Iterator nets = mNetworks.iterator();
        while(nets.hasNext()){
            networks.setText(""+networks.getText() + nets.next() + "\n");
        }

        mBlankIntervals.put(R.id.interval_in_00_06, getString(R.string.interval0_6));
        mBlankIntervals.put(R.id.interval_in_07_10, getString(R.string.interval7_10));
        mBlankIntervals.put(R.id.interval_in_11_14, getString(R.string.interval11_14));
        mBlankIntervals.put(R.id.interval_in_15_18, getString(R.string.interval15_18));
        mBlankIntervals.put(R.id.interval_in_19_23, getString(R.string.interval19_23));
        mBlankIntervals.put(R.id.interval_out_00_06, getString(R.string.interval0_6));
        mBlankIntervals.put(R.id.interval_out_07_10, getString(R.string.interval7_10));
        mBlankIntervals.put(R.id.interval_out_11_14, getString(R.string.interval11_14));
        mBlankIntervals.put(R.id.interval_out_15_18, getString(R.string.interval15_18));
        mBlankIntervals.put(R.id.interval_out_19_23, getString(R.string.interval19_23));

        ((TextView)findViewById(R.id.interval_out_00_06)).setText(mBlankIntervals.get(R.id.interval_out_00_06)+" "+ mIntervalsValues.get(R.id.interval_out_00_06));
        ((TextView)findViewById(R.id.interval_out_07_10)).setText(mBlankIntervals.get(R.id.interval_out_07_10)+" "+ mIntervalsValues.get(R.id.interval_out_07_10));
        ((TextView)findViewById(R.id.interval_out_11_14)).setText(mBlankIntervals.get(R.id.interval_out_11_14)+" "+ mIntervalsValues.get(R.id.interval_out_11_14));
        ((TextView)findViewById(R.id.interval_out_15_18)).setText(mBlankIntervals.get(R.id.interval_out_15_18)+" "+ mIntervalsValues.get(R.id.interval_out_15_18));
        ((TextView)findViewById(R.id.interval_out_19_23)).setText(mBlankIntervals.get(R.id.interval_out_19_23)+" "+ mIntervalsValues.get(R.id.interval_out_19_23));
        ((TextView)findViewById(R.id.interval_in_00_06)).setText(mBlankIntervals.get(R.id.interval_in_00_06)+" "+ mIntervalsValues.get(R.id.interval_in_00_06));
        ((TextView)findViewById(R.id.interval_in_07_10)).setText(mBlankIntervals.get(R.id.interval_in_07_10)+" "+ mIntervalsValues.get(R.id.interval_in_07_10));
        ((TextView)findViewById(R.id.interval_in_11_14)).setText(mBlankIntervals.get(R.id.interval_in_11_14)+" "+ mIntervalsValues.get(R.id.interval_in_11_14));
        ((TextView)findViewById(R.id.interval_in_15_18)).setText(mBlankIntervals.get(R.id.interval_in_15_18)+" "+ mIntervalsValues.get(R.id.interval_in_15_18));
        ((TextView)findViewById(R.id.interval_in_19_23)).setText(mBlankIntervals.get(R.id.interval_in_19_23)+" "+ mIntervalsValues.get(R.id.interval_in_19_23));

    }



    private void setBoolValueOfItem(int item, Boolean value){
        ((CheckBox)findViewById(item)).setChecked(value);
    }

    private void loadConfig(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.conf), Context.MODE_PRIVATE);
        mSaturday = pref.getBoolean(getString(R.string.saturday), false);
        mSunday = pref.getBoolean(getString(R.string.sunday), false);
        mNetworks = pref.getStringSet(getString(R.string.defined_networks), mNetworks);

        mIntervalsValues.put(R.id.interval_in_00_06, pref.getInt(getString(R.string.interval0_6_in_value), 5));
        mIntervalsValues.put(R.id.interval_in_07_10, pref.getInt(getString(R.string.interval7_10_in_value), 5));
        mIntervalsValues.put(R.id.interval_in_11_14, pref.getInt(getString(R.string.interval11_14_in_value), 5));
        mIntervalsValues.put(R.id.interval_in_15_18, pref.getInt(getString(R.string.interval15_18_in_value), 5));
        mIntervalsValues.put(R.id.interval_in_19_23, pref.getInt(getString(R.string.interval19_23_in_value), 5));
        mIntervalsValues.put(R.id.interval_out_00_06, pref.getInt(getString(R.string.interval0_6_out_value), 5));
        mIntervalsValues.put(R.id.interval_out_07_10, pref.getInt(getString(R.string.interval7_10_out_value), 5));
        mIntervalsValues.put(R.id.interval_out_11_14, pref.getInt(getString(R.string.interval11_14_out_value), 5));
        mIntervalsValues.put(R.id.interval_out_15_18, pref.getInt(getString(R.string.interval15_18_out_value), 5));
        mIntervalsValues.put(R.id.interval_out_19_23, pref.getInt(getString(R.string.interval19_23_out_value), 5));
    }

    private void showAlert(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.warning));
        alert.setMessage(getString(R.string.warning_message));
        alert.setPositiveButton(getString(R.string.drop_changes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Settings.this.finish();
            }
        });
        alert.setNegativeButton(getString(R.string.back), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if(mConfigChanged)
        showAlert();
        else
        super.onBackPressed();
//        TODO add wartning popup + also for <- action

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.interval0_6_in_value), mIntervalsValues.get(R.id.interval_in_00_06));
        outState.putInt(getString(R.string.interval7_10_in_value), mIntervalsValues.get(R.id.interval_in_07_10));
        outState.putInt(getString(R.string.interval11_14_in_value), mIntervalsValues.get(R.id.interval_in_11_14));
        outState.putInt(getString(R.string.interval15_18_in_value), mIntervalsValues.get(R.id.interval_in_15_18));
        outState.putInt(getString(R.string.interval19_23_in_value), mIntervalsValues.get(R.id.interval_in_19_23));

        outState.putInt(getString(R.string.interval0_6_out_value), mIntervalsValues.get(R.id.interval_out_00_06));
        outState.putInt(getString(R.string.interval7_10_out_value), mIntervalsValues.get(R.id.interval_out_07_10));
        outState.putInt(getString(R.string.interval11_14_out_value), mIntervalsValues.get(R.id.interval_out_11_14));
        outState.putInt(getString(R.string.interval15_18_out_value), mIntervalsValues.get(R.id.interval_out_15_18));
        outState.putInt(getString(R.string.interval19_23_out_value), mIntervalsValues.get(R.id.interval_out_19_23));

        outState.putBoolean(getString(R.string.drop_changes), mConfigChanged);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_settings, menu);
//        return true;
//    }
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            if(mConfigChanged)
                showAlert();
            else
                super.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
