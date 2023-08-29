package com.eywa.myplant.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.eywa.myplant.tab.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Plants.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "plants";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "userId";
    private static final String COLUMN_NICKNAME = "nickname";
    private static final String COLUMN_REALNAME = "realname";
    private static final String COLUMN_IMG = "plantImageUri";
    private static final String COLUMN_LIGHT_INTENSITY = "light_intensity";
    private static final String COLUMN_SOIL_MOISTURE = "soil_moisture";
    private static final String COLUMN_TEMPERATURE = "temperature";
    private static final String COLUMN_HUMIDITY = "humidity";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_INTIMACY = "intimacy";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_USER_ID + " TEXT, " +
                COLUMN_NICKNAME + " TEXT, " +
                COLUMN_REALNAME + " TEXT, " +
                COLUMN_IMG + " TEXT, " +
                COLUMN_LIGHT_INTENSITY + " REAL, " +
                COLUMN_SOIL_MOISTURE + " REAL, " +
                COLUMN_TEMPERATURE + " REAL, " +
                COLUMN_HUMIDITY + " REAL, " +
                COLUMN_STATUS + " INTEGER, " +
                COLUMN_INTIMACY + " REAL)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addPlant(PlaceholderContent.PlaceholderItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, item.id);
        values.put(COLUMN_USER_ID, item.userId);
        values.put(COLUMN_NICKNAME, item.nickname);
        values.put(COLUMN_REALNAME, item.realname);
        values.put(COLUMN_IMG, item.plantImageUri.toString());
        values.put(COLUMN_LIGHT_INTENSITY, item.light_intensity);
        values.put(COLUMN_SOIL_MOISTURE, item.soil_moisture);
        values.put(COLUMN_TEMPERATURE, item.temperature);
        values.put(COLUMN_HUMIDITY, item.humidity);
        values.put(COLUMN_STATUS, item.status ? 1 : 0);
        values.put(COLUMN_INTIMACY, item.intimacy);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deletePlant(PlaceholderContent.PlaceholderItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{item.id});
        db.close();
    }

    public List<PlaceholderContent.PlaceholderItem> getAllPlantsByUserId(String userId) {
        List<PlaceholderContent.PlaceholderItem> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_USER_ID + " = ?", new String[]{userId}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String nickname = cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME));
                String realname = cursor.getString(cursor.getColumnIndex(COLUMN_REALNAME));
                Uri plantImageUri = Uri.parse(cursor.getString(cursor.getColumnIndex(COLUMN_IMG)));
                float light_intensity = cursor.getFloat(cursor.getColumnIndex(COLUMN_LIGHT_INTENSITY));
                float soil_moisture = cursor.getFloat(cursor.getColumnIndex(COLUMN_SOIL_MOISTURE));
                float temperature = cursor.getFloat(cursor.getColumnIndex(COLUMN_TEMPERATURE));
                float humidity = cursor.getFloat(cursor.getColumnIndex(COLUMN_HUMIDITY));
                boolean status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)) == 1;

                PlaceholderContent.PlaceholderItem item = new PlaceholderContent.PlaceholderItem(id, userId, nickname, realname, plantImageUri);
                item.light_intensity = light_intensity;
                item.soil_moisture = soil_moisture;
                item.temperature = temperature;
                item.humidity = humidity;
                item.status = status;

                items.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return items;
    }


}

