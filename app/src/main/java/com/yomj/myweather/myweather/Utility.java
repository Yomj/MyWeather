package com.yomj.myweather.myweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 16-3-10.
 */
public class Utility {
    public synchronized static boolean handleCityResponse(CityDB cityDB,String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("city_info");
            for(int i  = 0; i <jsonArray.length(); i++){
                JSONObject jsonObject01 = jsonArray.getJSONObject(i);
                String cityName = jsonObject01.getString("city");
                String cityId = jsonObject01.getString("id");
                Log.d("Response","cityName is " + cityName + ", cityId is " + cityId);
                City city = new City();
                city.setCityName(cityName);
                city.setCityId(cityId);
                cityDB.saveCity(city);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void handleWeatherResponse(Context context, String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray data = jsonObject.getJSONArray("HeWeather data service 3.0");
            for(int i = 0; i < data.length(); i++){
                JSONObject datas = data.getJSONObject(i);
                JSONObject basic = datas.getJSONObject("basic");
                String basicCity = basic.getString("city");
                String basicCnty = basic.getString("cnty");
                String basicId = basic.getString("id");
                String basicLat = basic.getString("lat");
                String basicLon = basic.getString("lon");
                JSONObject basicUpdate = basic.getJSONObject("update");
                String basicUpdateLoc = basicUpdate.getString("loc");
                String basicUpdateUtc = basicUpdate.getString("utc");
                Log.d("BasicResponse",basicCity + "当地时间是" + basicUpdateLoc);
                saveBasic(context,basicCity,basicCnty,basicId,basicLat,basicLon,basicUpdateLoc,basicUpdateUtc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveBasic(Context context,String basicCity,String basicCnty,String basicId,String basicLat,String basicLon,String basicUpdateLoc,String basicUpdateUtc){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("basicCity",basicCity);
        editor.putString("basicCnty",basicCnty);
        editor.putString("basicId",basicId);
        editor.putString("basicLat",basicLat);
        editor.putString("basicLon",basicLon);
        editor.putString("basicUpdateLoc",basicUpdateLoc);
        editor.putString("basicUpdateUtc",basicUpdateUtc);
        editor.commit();
    }
}
