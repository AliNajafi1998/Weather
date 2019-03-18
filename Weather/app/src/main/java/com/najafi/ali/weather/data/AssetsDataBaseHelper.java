package com.najafi.ali.weather.data;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssetsDataBaseHelper {

    private Context context;
    private String dbName = "db_city";

    public AssetsDataBaseHelper(Context context) {
        this(context, "db_city");
    }

    public AssetsDataBaseHelper(Context context, String dbName) {
        this.context = context;
        this.dbName = dbName;
    }

    //Whether db exists or not?
    public void checkDb() {
        File dbFile = context.getDatabasePath(dbName);
        if (!dbFile.exists()) {
            try {
                copyDataBase(dbFile);
                Log.i("AssetsDataBaseHelper","dataBase copied!");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source DataBase", e);
            }
        }
    }

    private void copyDataBase(File dbFile) throws IOException {
        InputStream is = context.getAssets().open(dbName);
        dbFile.getParentFile().mkdirs();
        OutputStream os = new FileOutputStream(dbFile);

        int len = 0;
        byte[] buffer = new byte[1024];

        while ((len = is.read(buffer)) > 0) {
            os.write(buffer,0,len);
        }

        os.flush();
        os.close();
        is.close();
    }

}
