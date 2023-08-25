package com.eywa.myplant.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eywa.myplant.tab.placeholder.PlaceholderContent;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Plants.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "plants";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NICKNAME = "nickname";
    private static final String COLUMN_REALNAME = "realname";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_NICKNAME + " TEXT, " +
                COLUMN_REALNAME + " TEXT)";
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
        values.put(COLUMN_NICKNAME, item.nickname);
        values.put(COLUMN_REALNAME, item.realname);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Add more methods to update, delete, retrieve as needed...
}

