package com.tryking.trykingcitychoose;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tryking on 2016/4/20.
 */
public class CityDBManager {
    private Context mContext;
    private static final String DBPath = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/com.tryking.trykingcitychoose";
    private static final String DBName = "t_prov_city_area_street_cn.db";
    private SQLiteDatabase database;
    private File file = null;

    public CityDBManager(Context context) {
        this.mContext = context;
    }

    public void openDatabase() {
        this.database = openDatabase(DBPath + "/" + DBName);
    }

    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    public void closeDatabase() {
        if (database != null) {
            database.close();
        }
    }

    private SQLiteDatabase openDatabase(String DBFile) {
        try {
            file = new File(DBFile);
            if (!file.exists()) {
                InputStream fis = mContext.getResources().openRawResource(R.raw.t_prov_city_area_street);
                if (fis == null) {
                    Log.e("database", "fis null");
                }
                FileOutputStream fos = new FileOutputStream(DBFile);
                if (fos == null) {
                    Log.e("database", "fos null");
                }
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                    fos.flush();
                }
                fos.close();
                fis.close();
            }
            database = SQLiteDatabase.openOrCreateDatabase(DBFile, null);
            return database;
        } catch (FileNotFoundException e) {
            Log.e("database", "File not found Exception");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("database", "IO Exception");
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("database", "Exception" + e.toString());
        }
        return null;
    }
}
