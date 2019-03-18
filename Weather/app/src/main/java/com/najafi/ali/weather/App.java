package com.najafi.ali.weather;

import android.app.Application;

import com.najafi.ali.weather.data.AssetsDataBaseHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new AssetsDataBaseHelper(getApplicationContext()).checkDb();
    }
}
