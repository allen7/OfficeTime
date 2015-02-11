package com.rec.kuciaba.andrzej.officetime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wro00669 on 2015-01-15.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "events.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table networkEvents (id integer primary key autoincrement, time integer, date text, type integer);");
        db.execSQL("create table updates (id integer primary key autoincrement, date text, diff integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }

    public void addTime(long time, String date, Integer type){
        SQLiteDatabase db =  getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("time",time);
        values.put("date",date);
        values.put("type",type);
        db.insertOrThrow("networkEvents",null, values);
    }

    public void updateTime(long time, String date, Integer type){
        SQLiteDatabase db =  getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("time",time);
//        values.put("date",date);
//        values.put("type",type);
//        db.insertOrThrow("networkEvents",null, values);
        db.update("networkEvents", values, "date=? and type=?", new String[]{date, type.toString()});
    }

    public long getDateTime(String date, Integer type){
        SQLiteDatabase db =  getReadableDatabase();
        long time = 0;
        Cursor cursor = db.query("networkEvents", new String[] {"time"}, "date=? and type=?", new String[]{date, type.toString()}, null, null,null);
        if(cursor != null){
            while(cursor.moveToNext()) {
                time = cursor.getLong(0);
            }
        }
        return time;
    }
}
