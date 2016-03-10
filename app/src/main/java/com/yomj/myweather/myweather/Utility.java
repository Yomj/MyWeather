package com.yomj.myweather.myweather;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 16-3-10.
 */
public class Utility {
    public synchronized static boolean handleCityResponse(CityDB cityDB,String response){
        try{
            JSONArray jsonArray = new JSONArray(response);
            for(int i  = 0; i <jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String cityName = jsonObject.getString("city");
                String cityId = jsonObject.getString("id");
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
}
