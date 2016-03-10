package com.yomj.myweather.myweather;

/**
 * Created by Administrator on 16-3-10.
 */
public class City {
    private int id;
    private String cityName;
    private String cityId;

    public int getId(){
        return id;
    }
    public  void setId(int id){
        this.id = id;
    }
    public String getCityName(){
        return cityName;
    }
    public void setCityName(String cityName){
        this.cityName = cityName;
    }
    public String getCityId(){
        return cityId;
    }
    public void setCityId(String cityId){
        this.cityId = cityId;
    }
}
