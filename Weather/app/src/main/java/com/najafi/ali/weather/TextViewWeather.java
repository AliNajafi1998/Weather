package com.najafi.ali.weather;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;
public class TextViewWeather extends AppCompatTextView {


    public TextViewWeather(Context context) {
        super(context);
        init(context);
    }

    public TextViewWeather(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextViewWeather(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    private void init(Context context) {
        Typeface weatherFont = Typeface.createFromAsset(context.getResources().getAssets(),"fonts/weathericons-regular-webfont.ttf");
        setTypeface(weatherFont);
    }


}
