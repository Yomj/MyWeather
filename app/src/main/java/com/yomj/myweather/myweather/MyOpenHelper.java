package com.yomj.myweather.myweather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 16-3-10.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

    public static final String CREATE_CITY = "create table City (id integer primary key autoincrement, city_name text,city_id text)";
    public static final String CREATE_WEATHER_CODE = "create table WeatherCode (id integer primary key autoincrement, weather_code text,weather_text text,weather_icon text)";

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_WEATHER_CODE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
