package com.najafi.ali.weather;

import android.app.Application;

import com.najafi.ali.weather.data.AssetsDataBaseHelper;

public class App extends Application {
    public static final String API_KEY =  "acc64bde9b30d3374d499cc8dc2c0769";
    public static final String URL_FORMAT_BY_CITY_NAME = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&APPID=" + API_KEY;
    public static final String URL_FORMAT_BY_CITY_ID = "http://api.openweathermap.org/data/2.5/weather?id=%d&units=metric&APPID=" + API_KEY;
    @Override
    public void onCreate() {
        super.onCreate();
        new AssetsDataBaseHelper(getApplicationContext()).checkDb();
    }
}
