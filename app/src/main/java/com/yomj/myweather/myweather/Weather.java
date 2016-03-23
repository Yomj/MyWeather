package com.yomj.myweather.myweather;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Weather extends AppCompatActivity {

    String getWeatherCode = "https://api.heweather.com/x3/condition?search=allcond&key=3f059ab3038f499089810a0b28029f75";

    private String cityId;
    private ProgressDialog progressDialog;
    private MyDB myDB;
    private TextView cityText,publishTimeText,weatherText,tmpText,humText,pcpnText,visText,windText;
    private ImageView weatherImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weatherImage = (ImageView) findViewById(R.id.weather_image);
        cityText = (TextView) findViewById(R.id.city_text);
        publishTimeText = (TextView) findViewById(R.id.publish_time_text);
        weatherText = (TextView) findViewById(R.id.weather_text);
        tmpText = (TextView) findViewById(R.id.temp_text);
        humText = (TextView) findViewById(R.id.hum_text);
        pcpnText = (TextView) findViewById(R.id.pcpn_text);
        visText = (TextView) findViewById(R.id.vis_text);
        windText = (TextView) findViewById(R.id.wind_text);
        cityId = getIntent().getStringExtra("cityId");
        String httpUrl = "https://api.heweather.com/x3/weather?cityid=" + cityId +"&key=3f059ab3038f499089810a0b28029f75";
        HttpUtil.sendHttpRequset(httpUrl, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utility.handleWeatherResponse(Weather.this,response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeather();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(Weather.this,"获取失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showWeather(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("WeatherBasicResponse", sharedPreferences.getString("basicCity", ""));
        cityText.setText(sharedPreferences.getString("basicCity",""));
        publishTimeText.setText(sharedPreferences.getString("basicUpdateLoc","") + "发布");
        weatherText.setText(sharedPreferences.getString("nowCondTxt",""));
        tmpText.setText(sharedPreferences.getString("nowTmp","") + "℃");
        humText.setText("湿度：" +sharedPreferences.getString("nowHum",""));
        pcpnText.setText("降雨量：" +sharedPreferences.getString("nowPcpn","") + "mm");
        visText.setText("能见度:" + sharedPreferences.getString("nowVis","") + "km");
        windText.setText(sharedPreferences.getString("nowWindDir","") + ":" + sharedPreferences.getString("nowWindSc",""));
        String weatherCode = sharedPreferences.getString("nowCondCode","");
    }


    private void queryWeatherCodeFromServer(){
        showProgressDialog();
        myDB = MyDB.getInstance(this);
        HttpUtil.sendHttpRequset(getWeatherCode, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                result = Utility.handleWeatherCodeResponse(myDB,response);
                if(result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                closeProgressDialog();
                Toast.makeText(Weather.this,"加载失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载……");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
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
