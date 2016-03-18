package com.yomj.myweather.myweather;

/**
 * Created by Administrator on 16-3-18.
 */
public class WeatherCode {
    private int id;
    private String weatherCode;
    private String weatherText;
    private String weatherIcon;
    public int getId(){
        return id;
    }
    public  void setId(int id){
        this.id = id;
    }
    public String getWeatherCode(){
        return weatherCode;
    }
    public void setWeatherCode(String weatherCode){
        this.weatherCode = weatherCode;
    }
    public String getWeatherText(){
        return weatherText;
    }
    public void setWeatherText(String weatherText){
        this.weatherText = weatherText;
    }
    public  String getWeatherIcon(){
        return weatherIcon;
    }
    public void setWeatherIcon(String weatherIcon){
        this.weatherIcon = weatherIcon;
    }
}
