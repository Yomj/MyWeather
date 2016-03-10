package com.yomj.myweather.myweather;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ChooseCity extends AppCompatActivity {

    String cityId;
    String httpUrl = "https://api.heweather.com/x3/weather?city=jiaxing&key=3f059ab3038f499089810a0b28029f75";
    String getCityId = "https://api.heweather.com/x3/citylist?search=allchina&key=3f059ab3038f499089810a0b28029f75";
    String getWeatherId = "https://api.heweather.com/x3/condition?search=allcond&key=3f059ab3038f499089810a0b28029f75";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        cityId = "CN101010100";
        HttpUtil.sendHttpRequset(getCityId, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("Response",response);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(ChooseCity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_city, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
