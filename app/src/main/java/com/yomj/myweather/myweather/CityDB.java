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
public class CityDB {
    public static final String DB_NAME = "my_weather";
    public static final int VERSION = 1;
    private SQLiteDatabase database;
    private static CityDB cityDB;


    public CityDB(Context context) {
        CityOpenHelper cityOpenHelper = new CityOpenHelper(context,DB_NAME,null,VERSION);
        database = cityOpenHelper.getWritableDatabase();
    }
    public synchronized static CityDB getInstance(Context context){
        if(cityDB == null){
            cityDB = new CityDB(context);
        }
        return cityDB;
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

}
