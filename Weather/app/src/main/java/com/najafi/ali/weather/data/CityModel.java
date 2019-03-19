package com.najafi.ali.weather.data;

import android.content.ContentValues;
import android.database.Cursor;

public class CityModel {
    private long id;
    private double lat;
    private double lon;
    private String name = "";
    private String countryCode = "";
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return name.toUpperCase() + ", " + countryCode;
    }

    public ContentValues getContentValuesForDb() {
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("lat", lat);
        cv.put("lon", lon);
        cv.put("countryCode", countryCode);
        cv.put("selected", selected ? 1 : 0);
        return cv;
    }

    public static ContentValues createContentValues
            (long id, String name, double lat, double lon, String countryCode, boolean selected) {
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("lat", lat);
        cv.put("lon", lon);
        cv.put("countryCode", countryCode);
        cv.put("selected", selected ? 1 : 0);
        return cv;
    }

    public static CityModel fromCursor(Cursor cursor) {
        CityModel city = new CityModel();
        city.setId(cursor.getLong(cursor.getColumnIndex("id")));
        city.setName(cursor.getString(cursor.getColumnIndex("name")));
        city.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
        city.setLon(cursor.getDouble(cursor.getColumnIndex("lon")));
        city.setCountryCode(cursor.getString(cursor.getColumnIndex("countryCode")));

        return city;
    }
}
