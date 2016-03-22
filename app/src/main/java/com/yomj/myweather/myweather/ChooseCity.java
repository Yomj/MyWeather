package com.yomj.myweather.myweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChooseCity extends AppCompatActivity {

    //String cityId;
    //String httpUrl = "https://api.heweather.com/x3/weather?city=jiaxing&key=3f059ab3038f499089810a0b28029f75";
    String getCityId = "https://api.heweather.com/x3/citylist?search=allchina&key=3f059ab3038f499089810a0b28029f75";
    //String getWeatherId = "https://api.heweather.com/x3/condition?search=allcond&key=3f059ab3038f499089810a0b28029f75";

    private EditText searchEditText;
    private Button searchButton;
    private ListView cityListView;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> dataList = new ArrayList<String>();
    private MyDB myDB;
    private List<City> cityList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean("city_selected",false)){
            Intent intent = new Intent(this,Weather.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_choose_city);
        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = searchEditText.getText().toString();

            }
        });
        cityListView = (ListView) findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
        cityListView.setAdapter(arrayAdapter);
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //to do 将获取的cityId传递出去
                String cityId = cityList.get(position).getCityId();
                Log.d("Response",cityId);
                Intent intent = new Intent(ChooseCity.this,Weather.class);
                intent.putExtra("cityId",cityId);
                startActivity(intent);
                finish();
            }
        });
        queryCities();
    }

    private void queryCities(){
        myDB = MyDB.getInstance(this);
        cityList = myDB.loadCity();
        if(cityList.size() > 0){
            dataList.clear();
            for(City city :cityList){
                dataList.add(city.getCityName());
            }
            arrayAdapter.notifyDataSetChanged();
            cityListView.setSelection(0);
        }else{
            queryFromServer();
        }
    }

    private void queryFromServer(){
        showProgressDialog();
        HttpUtil.sendHttpRequset(getCityId, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("Response",response);
                boolean result = false;
                result = Utility.handleCityResponse(myDB,response);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseCity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
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
