package com.rec.kuciaba.andrzej.officetime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class TimeBrowser extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_browser);

        Log.i("AK__", "AK__TimeBrowser::onCreate");
        brChecker checker = new brChecker();
        checker.setOperation(getBaseContext(), 1);
        Log.i("AK__", "AK__TimeBrowser::onCreate1");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(11);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings  = new Intent(this, Settings.class);
            startActivity(settings);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SimpleDateFormat mDateFormater = new SimpleDateFormat("MMM yyyy");

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 12;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            Calendar now = Calendar.getInstance();
            now.set(Calendar.MONTH, now.get(Calendar.MONTH)-(11-position));
            return mDateFormater.format(now.getTime());
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        private View setWeekName(String name){
            LinearLayout cell = new LinearLayout(getActivity());
            cell.setBackgroundColor(Color.WHITE);
            TableRow.LayoutParams llp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, 0, 2, 0);//2px right-margin
            cell.setLayoutParams(llp);
            TextView txt = new TextView(getActivity());
            txt.setText(name.substring(0,1).toUpperCase()+name.substring(1));
            txt.setBackgroundColor(0xFF808080);
            txt.setTextColor(0xFFFFFFFF);
            txt.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            cell.addView(txt);
            return cell;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_time_browser, container, false);
            TableLayout table = (TableLayout)rootView.findViewById(R.id.calendar_table);
            TableRow row = new TableRow(getActivity());
            TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.setMargins(0,0,2,0);
            row.setLayoutParams(params);
            row.setBackgroundColor(Color.BLACK);
            row.setPadding(2,0,2,0);

//            TextView txt = new TextView(getActivity());
//            txt.setText("aaaaa");
//            table.addView(txt);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis((0+345600)*1000);
            calendar.getTime();
            for(int i = 0; i<7;++i){
                row.addView(setWeekName(calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT,Locale.getDefault())));
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)+1);
            }
//            table.addView(row);

            calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
//            now.set(Calendar.MONTH, now.get(Calendar.MONTH)-(11-position));
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-(11-this.getArguments().getInt(ARG_SECTION_NUMBER)));
            int month = calendar.get(Calendar.MONTH);

            boolean lastDayReached = false;
            int[] weekDays = {Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};
            while(!lastDayReached){
                TableRow rowView = (TableRow)inflater.inflate(R.layout.days_row, null);
                for(int i =0; i<7; ++i){
                    if(month == calendar.get(Calendar.MONTH) && weekDays[i]==calendar.get(Calendar.DAY_OF_WEEK)){
                        TextView dayTextView =null;
                        TextView timeView =null;
                        LinearLayout dayView = null;
                        switch (weekDays[i]){
                            case Calendar.MONDAY:
                                dayTextView = (TextView)rowView.findViewById(R.id.day1);
                                break;
                            case Calendar.TUESDAY:
                                dayTextView = (TextView)rowView.findViewById(R.id.day2);
                                break;
                            case Calendar.WEDNESDAY:
                                dayTextView = (TextView)rowView.findViewById(R.id.day3);
                                break;
                            case Calendar.THURSDAY:
                                dayTextView = (TextView)rowView.findViewById(R.id.day4);
                                break;
                            case Calendar.FRIDAY:
                                dayTextView = (TextView)rowView.findViewById(R.id.day5);
                                break;
                            case Calendar.SATURDAY:
                                dayTextView = (TextView)rowView.findViewById(R.id.day6);
                                break;
                            case Calendar.SUNDAY:
                                dayTextView = (TextView)rowView.findViewById(R.id.day7);
                                break;
                        }
                        if(dayTextView!=null) {
                            dayTextView.setText(calendar.get(Calendar.DAY_OF_MONTH) + "");
                        }
                        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)+1);
                    }
                }
                if(calendar.get(Calendar.MONTH)!=month) lastDayReached=true;
                table.addView(rowView);
            }


            return rootView;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
        }
    }

}
