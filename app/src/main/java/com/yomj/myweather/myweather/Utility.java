package com.yomj.myweather.myweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 16-3-10.
 */
public class Utility {
    public synchronized static boolean handleCityResponse(MyDB myDB,String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("city_info");
            for(int i  = 0; i <jsonArray.length(); i++){
                JSONObject jsonObject01 = jsonArray.getJSONObject(i);
                String cityName = jsonObject01.getString("city");
                String cityId = jsonObject01.getString("id");
                City city = new City();
                city.setCityName(cityName);
                city.setCityId(cityId);
                myDB.saveCity(city);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public synchronized static boolean handleWeatherCodeResponse(MyDB myDB,String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("cond_info");
            for(int i = 0; i <jsonArray.length(); i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String weatherCode = jsonObject1.getString("code");
                String weatherText = jsonObject1.getString("text");
                String weatherIcon = jsonObject1.getString("icon");
                Log.d("Response","weatherCode is " + weatherCode + ", weatherText is " + weatherText + ", weatherIcon is " + weatherIcon);
                WeatherCode weatherCode0 = new WeatherCode();
                weatherCode0.setWeatherCode(weatherCode);
                weatherCode0.setWeatherText(weatherText);
                weatherCode0.setWeatherIcon(weatherIcon);
                myDB.saveWeatherCode(weatherCode0);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void handleWeatherResponse(Context context, String response){
        try {
            Log.d("WeahterResponse",response);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray data = jsonObject.getJSONArray("HeWeather data service 3.0");
            for(int i = 0; i < data.length(); i++){
                JSONObject datas = data.getJSONObject(i);
                JSONObject aqi0 = datas.getJSONObject("aqi");
                JSONObject aqiCity = aqi0.getJSONObject("city");
                String aqi = aqiCity.getString("aqi");
                String co = aqiCity.getString("co");
                String no2 = aqiCity.getString("no2");
                String o3 = aqiCity.getString("o3");
                String pm10 = aqiCity.getString("pm10");
                String pm25 = aqiCity.getString("pm25");
                String qlty = aqiCity.getString("qlty");
                String so2 = aqiCity.getString("so2");
                Log.d("AqiResponse", "aqi is " + aqi + ", 空气质量类别" + qlty);
                saveAqi(context, aqi, co, no2, o3, pm10, pm25, qlty, so2);
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
                saveBasic(context, basicCity, basicCnty, basicId, basicLat, basicLon, basicUpdateLoc, basicUpdateUtc);
                /*JSONObject alarms = datas.getJSONObject("alarms");
                String level = alarms.getString("level");
                String stat = alarms.getString("stat");
                String alarmsTitle = alarms.getString("title");
                String alarmsTxt = alarms.getString("txt");
                String alarmsType = alarms.getString("type");
                Log.d("AlarmsResponse", "alarms level is " + level);
                saveAlarms(context, level, stat, alarmsTitle, alarmsTxt, alarmsType);*/
                JSONArray dF = datas.getJSONArray("daily_forecast");
                for(int j = 0;j < dF.length();j++){
                    JSONObject dailyForecast = dF.getJSONObject(j);
                    String dailyForecastDate = dailyForecast.getString("date");
                    JSONObject dailyForecastAstro = dailyForecast.getJSONObject("astro");
                    String dailyForecastAstroSr = dailyForecastAstro.getString("sr");
                    String dailyForecastAstroSs = dailyForecastAstro.getString("ss");
                    JSONObject dailyForecastCond = dailyForecast.getJSONObject("cond");
                    String dailyForecastCondCodeD = dailyForecastCond.getString("code_d");
                    String dailyForecastCondCodeN = dailyForecastCond.getString("code_n");
                    String dailyForecastCondTxtD = dailyForecastCond.getString("txt_d");
                    String dailyForecastCondTxtN = dailyForecastCond.getString("txt_n");
                    String dailyForecastHum = dailyForecast.getString("hum");
                    String dailyForecastPcpn = dailyForecast.getString("pcpn");
                    String dailyForecastPop = dailyForecast.getString("pop");
                    String dailyForecastPres = dailyForecast.getString("pres");
                    JSONObject dailyForecastTmp = dailyForecast.getJSONObject("tmp");
                    String dailyForecastTmpMax = dailyForecastTmp.getString("max");
                    String dailyForecastTmpMin = dailyForecastTmp.getString("min");
                    String dailyForecastVis = dailyForecast.getString("vis");
                    JSONObject dailyForecastWind = dailyForecast.getJSONObject("wind");
                    String dailyForecastWindDeg = dailyForecastWind.getString("deg");
                    String dailyForecastWindDir = dailyForecastWind.getString("dir");
                    String dailyForecastWindSc = dailyForecastWind.getString("sc");
                    String dailyForecastWindSpd = dailyForecastWind.getString("spd");
                    Log.d("DailyForecastResponse","date is " + dailyForecastDate + ",weatherDay is " + dailyForecastCondTxtD);
                    //save data
                }
                JSONArray hF = datas.getJSONArray("hourly_forecast");
                for(int k = 0;k < hF.length();k++){
                    JSONObject hourlyForecast = hF.getJSONObject(k);
                    String hourlyForecastDate = hourlyForecast.getString("date");
                    String hourlyForecastHum = hourlyForecast.getString("hum");
                    String hourlyForecastPop = hourlyForecast.getString("pop");
                    String hourlyForecastPres = hourlyForecast.getString("pres");
                    String hourlyForecastTmp = hourlyForecast.getString("tmp");
                    JSONObject hourlyForecastWind = hourlyForecast.getJSONObject("wind");
                    String hourlyForecastWindDeg = hourlyForecastWind.getString("deg");
                    String hourlyForecastWindDir = hourlyForecastWind.getString("dir");
                    String hourlyForecastWindSc = hourlyForecastWind.getString("sc");
                    String hourlyForecastWindSpd = hourlyForecastWind.getString("spd");
                    Log.d("HourlyForecastResponse","time is " + hourlyForecastDate + ", wind is " + hourlyForecastWindDir);
                    //save data
                }
                JSONObject now = datas.getJSONObject("now");
                JSONObject nowCond = now.getJSONObject("cond");
                String nowCondCode = nowCond.getString("code");
                String nowCondTxt = nowCond.getString("txt");
                String nowFl = now.getString("fl");
                String nowHum = now.getString("hum");
                String nowPcpn = now.getString("pcpn");
                String nowPres = now.getString("pres");
                String nowTmp = now.getString("tmp");
                String nowVis = now.getString("vis");
                JSONObject nowWind = now.getJSONObject("wind");
                String nowWindDeg = nowWind.getString("deg");
                String nowWindDir = nowWind.getString("dir");
                String nowWindSc = nowWind.getString("sc");
                String nowWindSpd = nowWind.getString("spd");
                Log.d("NowResponse", "now cond is " + nowCondTxt + ", fl is " + nowFl + ", wind dis is " + nowWindDir);
                saveNow(context, nowCondCode, nowCondTxt, nowFl, nowHum, nowPcpn, nowPres, nowTmp, nowVis, nowWindDeg, nowWindDir, nowWindSc, nowWindSpd);
                String status = datas.getString("status");
                JSONObject suggestion = datas.getJSONObject("suggestion");
                JSONObject suggestionComf = suggestion.getJSONObject("comf");
                String suggestionComfBrf = suggestionComf.getString("brf");
                String suggestionComfTxt = suggestionComf.getString("txt");
                JSONObject suggestionCw = suggestion.getJSONObject("cw");
                String suggestionCwBrf = suggestionCw.getString("brf");
                String suggestionCwTxt = suggestionCw.getString("txt");
                JSONObject suggestionDrsg = suggestion.getJSONObject("drsg");
                String suggsetionDrsgBrf = suggestionDrsg.getString("brf");
                String suggestionDrsgTxt = suggestionDrsg.getString("txt");
                JSONObject suggestionFlu = suggestion.getJSONObject("flu");
                String suggestionFluBrf = suggestionFlu.getString("brf");
                String suggestionFluTxt = suggestionFlu.getString("txt");
                JSONObject suggestionSport = suggestion.getJSONObject("sport");
                String suggestionSportBrf = suggestionSport.getString("brf");
                String suggestionSportTxt = suggestionSport.getString("txt");
                JSONObject suggestionTrav = suggestion.getJSONObject("trav");
                String suggestionTravBrf = suggestionTrav.getString("brf");
                String suggestionTravTxt = suggestionTrav.getString("txt");
                JSONObject suggestionUv = suggestion.getJSONObject("uv");
                String suggestionUvBrf = suggestionUv.getString("brf");
                String suggestionUvTxt = suggestionUv.getString("txt");
                Log.d("SuggestionResponse", "comf is " + suggestionComfBrf + ":" + suggestionComfTxt);
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
    public static void saveAqi(Context context,String aqi,String co,String no2,String o3,String pm10,String pm25,String qlty,String so2){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("aqi",aqi);
        editor.putString("co",co);
        editor.putString("no2",no2);
        editor.putString("o3",o3);
        editor.putString("pm10",pm10);
        editor.putString("pm25",pm25);
        editor.putString("qlty",qlty);
        editor.putString("so2",so2);
        editor.commit();
    }
    /*public static void saveAlarms(Context context,String level,String stat,String alarmsTitle,String alarmsTxt,String alarmsType){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("level",level);
        editor.putString("stat",stat);
        editor.putString("alarmsTitle",alarmsTitle);
        editor.putString("alarmsTxt",alarmsTxt);
        editor.putString("alarmsType",alarmsType);
        editor.commit();
    }*/
    public static void saveNow(Context context,String nowCondCode,String nowCondTxt,String nowFl,String nowHum,String nowPcpn,String nowPres,String nowTmp,String nowVis,String nowWindDeg,String nowWindDir,String nowWindSc,String nowWindSpd){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("nowCondCode",nowCondCode);
        editor.putString("nowCondTxt",nowCondTxt);
        editor.putString("nowFl",nowFl);
        editor.putString("nowHum",nowHum);
        editor.putString("nowPcpn",nowPcpn);
        editor.putString("nowPres",nowPres);
        editor.putString("nowTmp",nowTmp);
        editor.putString("nowVis",nowVis);
        editor.putString("nowWindDeg",nowWindDeg);
        editor.putString("nowWindDir",nowWindDir);
        editor.putString("nowWindSc",nowWindSc);
        editor.putString("nowWindSpd",nowWindSpd);
        editor.commit();
    }

}
