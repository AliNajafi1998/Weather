package com.najafi.ali.weather;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.najafi.ali.weather.data.CityDbHelper;
import com.najafi.ali.weather.data.CityModel;

import java.util.List;

public class CitiesActivity extends AppCompatActivity implements AddCityFragment.AddCityInterface {

    RecyclerView recyclerView;
    CityRecyclerViewAdapter adapter;
    CityDbHelper dbhelper;
    List<CityModel> citylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCityFragment fragment = new AddCityFragment();
                fragment.show(getSupportFragmentManager(), "addcity");
            }
        });
        dbhelper = new CityDbHelper(this);
        recyclerView =  findViewById(R.id.city_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        updateDisplay();

    }


    private void updateDisplay(){
        String[] args = {"1"};
        citylist = dbhelper.getCities("selected=?", args);
        Log.i("FUCKING BUG", citylist.size() + "");
        adapter = new CityRecyclerViewAdapter(citylist, dbhelper);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void addCity(long id) {
        dbhelper.updateCitySelected(id, true);
        updateDisplay();
    }
}
