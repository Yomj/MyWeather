package com.yomj.myweather.myweather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 16-3-9.
 */
public class HttpUtil {
    public static void sendHttpRequset(final String address,final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try{
                    URL url = new URL(address);
                    httpURLConnection = (HttpsURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                    StringBuilder stringBuilder =new StringBuilder();
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line);
                    }
                    if(listener != null){
                        listener.onFinish(stringBuilder.toString());
                    }
                }catch (Exception e){
                    if(listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if(httpURLConnection != null){
                        httpURLConnection.disconnect();
                    }
                }
            }
        }).start();
    }
}
