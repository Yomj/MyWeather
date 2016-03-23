package com.yomj.myweather.myweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-3-10.
 */
public class MyDB {
    public static final String DB_NAME = "my_weather";
    public static final int VERSION = 1;
    private SQLiteDatabase database;
    private static MyDB myDB;


    public MyDB(Context context) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context,DB_NAME,null,VERSION);
        database = myOpenHelper.getWritableDatabase();
    }
    public synchronized static MyDB getInstance(Context context){
        if(myDB == null){
            myDB = new MyDB(context);
        }
        return myDB;
    }
    public void saveCity(City city){
        if(city != null){
            ContentValues contentValues = new ContentValues();
            contentValues.put("city_name",city.getCityName());
            contentValues.put("city_id",city.getCityId());
            database.insert("City",null,contentValues);
        }
    }
    public List<City> loadCity(){
        List<City> cityList = new ArrayList<City>();
        Cursor cursor = database.query("City",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityId(cursor.getString(cursor.getColumnIndex("city_id")));
                cityList.add(city);
            }while (cursor.moveToNext());
        }
        return cityList;
    }

    public void saveWeatherCode(WeatherCode weatherCode){
        if(weatherCode != null){
            ContentValues contentValues = new ContentValues();
            contentValues.put("weather_code",weatherCode.getWeatherCode());
            contentValues.put("weather_text",weatherCode.getWeatherCode());
            contentValues.put("weather_icon",weatherCode.getWeatherIcon());
            database.insert("WeatherCode",null,contentValues);
        }
    }
    public List<WeatherCode> loadWeatherCode(){
        List<WeatherCode> weatherCodeList = new ArrayList<>();
        Cursor cursor = database.query("WeatherCode",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                WeatherCode weatherCode = new WeatherCode();
                weatherCode.setWeatherCode(cursor.getString(cursor.getColumnIndex("weather_code")));
                weatherCode.setWeatherText(cursor.getString(cursor.getColumnIndex("weather_text")));
                weatherCode.setWeatherIcon(cursor.getString(cursor.getColumnIndex("weather_icon")));
                weatherCodeList.add(weatherCode);
            }while(cursor.moveToNext());
        }
        return  weatherCodeList;
    }
}
