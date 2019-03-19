package com.najafi.ali.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.weather_fragment,
                WeatherFragment.newInstance("Tabriz")).commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Locations").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(MainActivity.this, CitiesActivity.class));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
