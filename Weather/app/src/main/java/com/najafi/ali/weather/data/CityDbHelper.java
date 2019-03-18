package com.najafi.ali.weather.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.najafi.ali.weather.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class CityDbHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String TAG = "DATABASE";
    private static final int DBVERSION = 1;
    public static final String TABLE_CITY = "tb_city";
    public static final String DBNAME = "db_city";
    public static final String[] allColumns = {"id", "name", "lat", "lon", "countryCode"};

    private static final String CD_CREATE_CITY_TB = "CREATE TABLE IF NOT EXISTS '" + TABLE_CITY + "'(" +
            "'id' INTEGER PRIMARY KEY NOT NULL," +
            "'name' TEXT," +
            "'lat' DOUBLE," +
            "'lon' DOUBLE," +
            "'countryCode' TEXT," +
            "'selected' INTEGER  " +
            ")";


    public CityDbHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
        this.context = context;
        if (getCities(null, null).isEmpty()) {
            initContents(getWritableDatabase());
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CD_CREATE_CITY_TB);
        Log.i(TAG, "TABLE CREATED!");
        initContents(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
        onCreate(db);
    }

    public void initContents(final SQLiteDatabase db) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    InputStream stream = context.getResources().openRawResource(R.raw.city_list);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] data = line.split("\t");
                        if (data.length < 5) {
                            continue;
                        }
                        long insertID = db.insert(TABLE_CITY, null,
                                CityModel.createContentValues(Long.valueOf(data[0]), data[1],
                                        Double.valueOf(data[2]), Double.valueOf(data[3]), data[4], false));
                        Log.i("dbHelper", "city inserted with id = " + insertID);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


    }

//    public void insertCityToDb(CityModel cityModel) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.insert(TABLE_CITY, null, cityModel.getContentValuesForDb());
////        Log.i("dbHelper", "city inserted with id = " + insertID);
//        db.close();
//    }

    public List<CityModel> getCities(String selection, String[] selectionArgs) {
        List<CityModel> cityList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_CITY, allColumns, selection, selectionArgs, null, null, "countryCode, name");
        if (cursor.moveToFirst()) {
            do {
                cityList.add(CityModel.fromCursor(cursor));
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return cityList;
    }
}