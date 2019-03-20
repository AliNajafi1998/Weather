package com.najafi.ali.weather;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.najafi.ali.weather.data.CityDbHelper;
import com.najafi.ali.weather.data.CityModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    MyPagerAdapter pagerAdapter;
    List<Fragment> weatherFragments;
    JsonObjectRequest request;
    RequestQueue requestQueue;
    List<CityModel> cities;
    ProgressBar pb;
    Handler updateHandler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        pb = findViewById(R.id.pb);
        init();
        updateHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                loadWeatherData();
                updateHandler.postDelayed(this, 10 * 60 * 1000);
            }
        };
    }

    private void init() {
        requestQueue = Volley.newRequestQueue(this);
        CityDbHelper dbHelper = new CityDbHelper(this);
        this.cities = dbHelper.getCities("selected = 1", null);
        weatherFragments = new ArrayList<>();


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateHandler.post(runnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateHandler.removeCallbacks(runnable);
    }

    private void loadWeatherData() {
        String URL = prepareURL();
        request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                weatherFragments.clear();
                pb.setVisibility(View.INVISIBLE);
                try {
                    Log.i("weather", "response : " + response);
                    int cnt = response.getInt("cnt");
                    if (cnt <= 0) {
                        return;
                    }

                    JSONArray jsonList = response.getJSONArray("list");


                    for (int i = 0; i < jsonList.length(); i++) {
                        JSONObject res = jsonList.getJSONObject(i);

                        String cityName = res.getString("name").toUpperCase() + ", " + res.getJSONObject("sys").getString("country");
                        double temp = res.getJSONObject("main").getDouble("temp");
                        JSONObject jsonDetails = res.getJSONArray("weather").getJSONObject(0);
                        String details = jsonDetails.getString("description");
                        JSONObject sys = res.getJSONObject("sys");
                        long sunrise = sys.getLong("sunrise");
                        long sunset = sys.getLong("sunset");
                        int weatherId = jsonDetails.getInt("id");

                        Bundle args = new Bundle();
                        args.putString("cityName", cityName);
                        args.putDouble("temperature", temp);
                        args.putLong("sunrise", sunrise);
                        args.putLong("sunset", sunset);
                        args.putInt("weatherId", weatherId);
                        args.putString("details", details);

                        weatherFragments.add(WeatherFragment.newInstance(args));
                    }
                    updateDisplay();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("weather", "error :  \n " + error.getMessage());
                pb.setVisibility(View.INVISIBLE);

            }
        });
        pb.setVisibility(View.VISIBLE);
        requestQueue.add(request);

    }

    private void updateDisplay() {
        if (weatherFragments == null) weatherFragments = new ArrayList<>();
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), weatherFragments);
        viewPager.setAdapter(pagerAdapter);
    }


    private String prepareURL() {
        StringBuilder sb = new StringBuilder("http://api.openweathermap.org/data/2.5/group?id=");
        for (int i = 0; i < cities.size(); i++) {
            sb.append(String.valueOf(cities.get(i).getId()));
            if (i < cities.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("&APPID=" + App.API_KEY);
        sb.append("&units=metric");

        return sb.toString();

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

    class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
