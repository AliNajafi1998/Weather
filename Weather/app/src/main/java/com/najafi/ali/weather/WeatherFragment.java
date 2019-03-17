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

import org.json.JSONObject;

import java.util.Locale;

public class WeatherFragment extends Fragment {


    TextView city, temperature, details, weatherIcon;

    private static final String TAG = WeatherFragment.class.getSimpleName();

    public static final String APP_KEY = "acc64bde9b30d3374d499cc8dc2c0769";
    public static final String URL_FORMAT = "http://api.openweathermap.org/data/2.5/find?q=%s&units=metric&APPID=" + APP_KEY;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_card, container, false);

        city = view.findViewById(R.id.city);
        temperature = view.findViewById(R.id.temperature);
        details = view.findViewById(R.id.details);
        weatherIcon = view.findViewById(R.id.weather_icon);
        requestData("Tabriz");

        return view;
    }



    private void requestData(String city) {
        String URL = String.format(Locale.getDefault(), URL_FORMAT, city);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG,"response :  \n"  + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"error :  \n "  + error.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }
}
