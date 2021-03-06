package com.najafi.ali.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class WeatherFragment extends Fragment {


    TextView tv_city, tv_temperature, tv_details;
    TextViewWeather tv_weatherIcon;

    private static final String TAG = WeatherFragment.class.getSimpleName();

    private String cityName, details;
    private double temperature;
    private long sunrise, sunset;
    private int weatherId;

    public static WeatherFragment newInstance(String city) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString("city", city);
        fragment.setArguments(args);

        return fragment;
    }

    public static WeatherFragment newInstance(Bundle args) {
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        cityName = args.getString("cityName");
        temperature = args.getDouble("temperature");
        sunrise = args.getLong("sunrise");
        sunset = args.getLong("sunset");
        weatherId = args.getInt("weatherId");
        details = args.getString("details");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_card, container, false);

        tv_city = view.findViewById(R.id.city);
        tv_temperature = view.findViewById(R.id.temperature);
        tv_details = view.findViewById(R.id.details);
        tv_weatherIcon = view.findViewById(R.id.weather_icon);

        fill();
        return view;
    }

    private void fill() {

        tv_city.setText(cityName);
        tv_temperature.setText(String.format(Locale.getDefault(), "%.0f %s", temperature, Html.fromHtml("&#8451;")));
        tv_details.setText(details);
        tv_weatherIcon.setWeatherIcon(weatherId, sunrise, sunset);


    }


    private void requestData(String cityName) {
        String URL = String.format(Locale.getDefault(), App.URL_FORMAT_BY_CITY_NAME, cityName);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray details = response.getJSONArray("weather");
                    JSONObject sys = response.getJSONObject("sys");
                    tv_city.setText(response.getString("name").toUpperCase() + ", " + response.getJSONObject("sys").getString("country"));


                    double temp = response.getJSONObject("main").getDouble("temp");
                    tv_temperature.setText(String.format(Locale.US, "%.0f %s", temp, Html.fromHtml("&#8451;")));


                    tv_details.setText(details.getJSONObject(0).getString("description"));

                    long sunrise = sys.getLong("sunrise");
                    long sunset = sys.getLong("sunset");
                    int weatherId = details.getJSONObject(0).getInt("id");

                    tv_weatherIcon.setWeatherIcon(weatherId, sunrise, sunset);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error :  \n " + error.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }

    private void requestData(long cityId) {
        String URL = String.format(Locale.getDefault(), App.URL_FORMAT_BY_CITY_ID, cityId);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray details = response.getJSONArray("weather");
                    JSONObject sys = response.getJSONObject("sys");
                    tv_city.setText(response.getString("name").toUpperCase() + ", " + response.getJSONObject("sys").getString("country"));


                    double temp = response.getJSONObject("main").getDouble("temp");
                    tv_temperature.setText(String.format(Locale.US, "%.0f %s", temp, Html.fromHtml("&#8451;")));


                    tv_details.setText(details.getJSONObject(0).getString("description"));

                    long sunrise = sys.getLong("sunrise");
                    long sunset = sys.getLong("sunset");
                    int weatherId = details.getJSONObject(0).getInt("id");

                    tv_weatherIcon.setWeatherIcon(weatherId, sunrise, sunset);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error :  \n " + error.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }

}
