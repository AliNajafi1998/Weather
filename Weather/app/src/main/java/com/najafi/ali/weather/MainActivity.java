package com.najafi.ali.weather;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.najafi.ali.weather.data.CityDbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.weather_fragment,
                 WeatherFragment.newInstance("Tabriz")).commit();

        CityDbHelper cityDbHelper = new CityDbHelper(this);


    }
}
