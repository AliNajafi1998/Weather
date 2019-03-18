package com.najafi.ali.weather;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;

import java.util.Date;

public class TextViewWeather extends AppCompatTextView {


    public TextViewWeather(Context context) {
        super(context);
        init(context);
    }

    public TextViewWeather(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextViewWeather(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        Typeface weatherFont = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        setTypeface(weatherFont);
    }

    public void setWeatherIcon(int id, long sunrise, long sunset) {
        String weatherIconCode = "";
        if (id == 800) {// clear
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {//day
                weatherIconCode = getContext().getResources().getString(R.string.weather_sunny);
            } else {
                weatherIconCode = getContext().getResources().getString(R.string.weather_clear_night);
            }

        } else {
            id = id / 100;

            switch (id) {
                case 2:
                    weatherIconCode = getContext().getResources().getString(R.string.weather_thunder);
                    break;
                case 3:
                    weatherIconCode = getContext().getResources().getString(R.string.weather_drizzle);
                    break;
                case 5:
                    weatherIconCode = getContext().getResources().getString(R.string.weather_rainy);
                    break;
                case 6:
                    weatherIconCode = getContext().getResources().getString(R.string.weather_snowy);
                    break;
                case 7:
                    weatherIconCode = getContext().getResources().getString(R.string.weather_foggy);
                    break;
                case 8:
                    weatherIconCode = getContext().getResources().getString(R.string.weather_cloudy);
                    break;
                default:
                    break;
            }
        }

        setText(Html.fromHtml(weatherIconCode));
    }
}
