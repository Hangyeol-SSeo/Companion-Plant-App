package com.eywa.myplant.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.eywa.myplant.tab.placeholder.ArchiveHolderContent;
import com.eywa.myplant.tab.placeholder.ItemHolderContent;
import com.eywa.myplant.tab.placeholder.MissionHolderContent;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final java.lang.String DATABASE_NAME = "Plants.db";
    private static final int DATABASE_VERSION = 1;

    // item
    private static final java.lang.String TABLE_NAME_ITEM = "plants";
    private static final java.lang.String COLUMN_ID = "id";
    private static final java.lang.String COLUMN_USER_ID = "userId";
    private static final java.lang.String COLUMN_NICKNAME = "nickname";
    private static final java.lang.String COLUMN_REALNAME = "realname";
    private static final java.lang.String COLUMN_IMG = "plantImageUri";
    private static final java.lang.String COLUMN_LIGHT_INTENSITY = "light_intensity";
    private static final java.lang.String COLUMN_SOIL_MOISTURE = "soil_moisture";
    private static final java.lang.String COLUMN_TEMPERATURE = "temperature";
    private static final java.lang.String COLUMN_HUMIDITY = "humidity";
    private static final java.lang.String COLUMN_STATUS = "status";
    private static final java.lang.String COLUMN_INTIMACY = "intimacy";

    // Archive
    private static final java.lang.String TABLE_NAME_ARCHIVE = "archive";
    private static final java.lang.String COLUMN_IMAGE_URI = "image_uri";
    private static final java.lang.String COLUMN_PAGE_URI = "page_uri";
    private static final java.lang.String COLUMN_PLANTNAME_KOR = "plantname_kor";
    private static final java.lang.String COLUMN_SPECIES = "species";
    private static final java.lang.String COLUMN_SCIENTIFICNAME = "scientificname";
    private static final java.lang.String COLUMN_TEMPERATURE_MIN = "temperature_min";
    private static final java.lang.String COLUMN_TEMPERATURE_MAX = "temperature_max";
    private static final java.lang.String COLUMN_HUMIDITY_MIN = "humidity_min";
    private static final java.lang.String COLUMN_HUMIDITY_MAX = "humidity_max";
    private static final java.lang.String COLUMN_LIGHT_INTENSITY_MIN = "light_intensity_min";
    private static final java.lang.String COLUMN_LIGHT_INTENSITY_MAX = "light_intensity_max";
    private static final java.lang.String COLUMN_SOIL_MOISTURE_LIMIT = "soil_moisture_limit";

    // Mission
    private static final java.lang.String TABLE_NAME_MISSION = "mission";
    private static final java.lang.String COLUMN_MISSION_ID = "missionId";

    private static final java.lang.String COLUMN_PLANT_ID = "plantId";
    private static final java.lang.String COLUMN_TIME = "time";
    private static final java.lang.String COLUMN_MISSION_NAME = "mission_name";
    private static final java.lang.String COLUMN_MISSION_ICON = "mission_icon";
    private static final java.lang.String COLUMN_MISSION_POINT = "mission_point";
    private static final java.lang.String COLUMN_INTIMACY_POINT = "intimacy_point";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        java.lang.String createTableItem = "CREATE TABLE " + TABLE_NAME_ITEM + " (" +
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

        java.lang.String createTableArchive = "CREATE TABLE " + TABLE_NAME_ARCHIVE + " (" +
                COLUMN_IMAGE_URI + " TEXT PRIMARY KEY, " +
                COLUMN_PAGE_URI + " TEXT, " +
                COLUMN_PLANTNAME_KOR + " TEXT, " +
                COLUMN_SPECIES + " TEXT, " +
                COLUMN_SCIENTIFICNAME + " TEXT, " +
                COLUMN_TEMPERATURE_MIN + " REAL, " +
                COLUMN_TEMPERATURE_MAX + " REAL, " +
                COLUMN_HUMIDITY_MIN + " REAL, " +
                COLUMN_HUMIDITY_MAX + " REAL, " +
                COLUMN_LIGHT_INTENSITY_MIN + " REAL, " +
                COLUMN_LIGHT_INTENSITY_MAX + " REAL, " +
                COLUMN_SOIL_MOISTURE_LIMIT + " REAL)";

        java.lang.String createTableMission = "CREATE TABLE " + TABLE_NAME_MISSION + " (" +
                COLUMN_MISSION_ID + " TEXT PRIMARY KEY, " +
                COLUMN_PLANT_ID + " TEXT, " +
                COLUMN_USER_ID + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_MISSION_NAME + " TEXT, " +
                COLUMN_MISSION_ICON + " TEXT, " +
                COLUMN_MISSION_POINT + " TEXT, " +
                COLUMN_INTIMACY_POINT + " REAL)";

        db.execSQL(createTableItem);
        db.execSQL(createTableArchive);
        db.execSQL(createTableMission);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ITEM);
        onCreate(db);
    }

    public void addPlantItem(ItemHolderContent.PlaceholderItem item) {
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

        db.insert(TABLE_NAME_ITEM, null, values);
        db.close();
    }

    public void addPlantArchive(ArchiveHolderContent item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_URI, item.imageUri.toString());
        values.put(COLUMN_PAGE_URI, item.pageUri.toString());
        values.put(COLUMN_PLANTNAME_KOR, item.plantNameKor);
        values.put(COLUMN_SPECIES, item.species);
        values.put(COLUMN_SCIENTIFICNAME, item.scientificName);
        values.put(COLUMN_TEMPERATURE_MIN, item.temperature_min);
        values.put(COLUMN_TEMPERATURE_MAX, item.temperature_max);
        values.put(COLUMN_HUMIDITY_MIN, item.humidity_min);
        values.put(COLUMN_HUMIDITY_MAX, item.humidity_max);
        values.put(COLUMN_LIGHT_INTENSITY_MIN, item.light_intensity_min);
        values.put(COLUMN_LIGHT_INTENSITY_MAX, item.light_intensity_max);
        values.put(COLUMN_SOIL_MOISTURE_LIMIT, item.soil_moisture_limit);

        db.insert(TABLE_NAME_ARCHIVE, null, values);
        db.close();
    }

    public void addMission(MissionHolderContent.MissionHolderItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MISSION_ID, item.missionId);
        values.put(COLUMN_PLANT_ID, item.plantId);
        values.put(COLUMN_USER_ID, item.userId);
        values.put(COLUMN_TIME, item.time);
        values.put(COLUMN_MISSION_NAME, item.missionName);
        values.put(COLUMN_MISSION_ICON, item.missionIcon);
        values.put(COLUMN_MISSION_POINT, item.missionPoint);
        values.put(COLUMN_INTIMACY_POINT, item.intimacyPoint);

        db.insert(TABLE_NAME_MISSION, null, values);
        db.close();
    }

    public void deletePlant(ItemHolderContent.PlaceholderItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_ITEM, COLUMN_ID + " = ?", new java.lang.String[]{item.id});
        db.close();
    }

    public List<ItemHolderContent.PlaceholderItem> getAllPlantsByUserId(java.lang.String userId) {
        List<ItemHolderContent.PlaceholderItem> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_ITEM, null, COLUMN_USER_ID + " = ?", new java.lang.String[]{userId}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                java.lang.String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                java.lang.String nickname = cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME));
                java.lang.String realname = cursor.getString(cursor.getColumnIndex(COLUMN_REALNAME));
                Uri plantImageUri = Uri.parse(cursor.getString(cursor.getColumnIndex(COLUMN_IMG)));
                float light_intensity = cursor.getFloat(cursor.getColumnIndex(COLUMN_LIGHT_INTENSITY));
                float soil_moisture = cursor.getFloat(cursor.getColumnIndex(COLUMN_SOIL_MOISTURE));
                float temperature = cursor.getFloat(cursor.getColumnIndex(COLUMN_TEMPERATURE));
                float humidity = cursor.getFloat(cursor.getColumnIndex(COLUMN_HUMIDITY));
                boolean status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)) == 1;

                ItemHolderContent.PlaceholderItem item = new ItemHolderContent.PlaceholderItem(id, userId, nickname, realname, plantImageUri);
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

    public ItemHolderContent.PlaceholderItem getPlantById(java.lang.String plantId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_ITEM, null, COLUMN_ID + " = ?", new java.lang.String[]{plantId}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        java.lang.String userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
        java.lang.String nickname = cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME));
        java.lang.String realname = cursor.getString(cursor.getColumnIndex(COLUMN_REALNAME));
        Uri plantImageUri = Uri.parse(cursor.getString(cursor.getColumnIndex(COLUMN_IMG)));
        float light_intensity = cursor.getFloat(cursor.getColumnIndex(COLUMN_LIGHT_INTENSITY));
        float soil_moisture = cursor.getFloat(cursor.getColumnIndex(COLUMN_SOIL_MOISTURE));
        float temperature = cursor.getFloat(cursor.getColumnIndex(COLUMN_TEMPERATURE));
        float humidity = cursor.getFloat(cursor.getColumnIndex(COLUMN_HUMIDITY));
        boolean status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)) == 1;

        cursor.close();
        db.close();

        ItemHolderContent.PlaceholderItem plant = new ItemHolderContent.PlaceholderItem(plantId, userId, nickname, realname, plantImageUri);
        plant.light_intensity = light_intensity;
        plant.soil_moisture = soil_moisture;
        plant.temperature = temperature;
        plant.humidity = humidity;
        plant.status = status;

        return plant;
    }

    public void updateDataByPlantId(java.lang.String plantId, float light_intensity, float soil_moisture, float temperature, float humidity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LIGHT_INTENSITY, light_intensity);
        values.put(COLUMN_SOIL_MOISTURE, soil_moisture);
        values.put(COLUMN_TEMPERATURE, temperature);
        values.put(COLUMN_HUMIDITY, humidity);

        db.update(TABLE_NAME_ITEM, values, COLUMN_ID + " = ?", new java.lang.String[]{plantId});
        db.close();
    }

    public List<java.lang.String> getAllPlantNamesKor(SQLiteDatabase db) {
        List<java.lang.String> plantNamesKor = new ArrayList<>();
        java.lang.String[] columns = {COLUMN_PLANTNAME_KOR};
        Cursor cursor = db.query(TABLE_NAME_ARCHIVE, columns, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    java.lang.String plantNameKor = cursor.getString(cursor.getColumnIndex(COLUMN_PLANTNAME_KOR));
                    plantNamesKor.add(plantNameKor);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return plantNamesKor;
    }

    public List<ArchiveHolderContent> getItemsByPlantNameKor(java.lang.String plantNameKor) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ArchiveHolderContent> matchingItems = new ArrayList<>();

        Cursor cursor = db.query(
                TABLE_NAME_ARCHIVE,
                null,
                COLUMN_PLANTNAME_KOR + " = ?",
                new java.lang.String[]{plantNameKor},
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Uri imageUri = Uri.parse(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URI)));
                    Uri pageUri = Uri.parse(cursor.getString(cursor.getColumnIndex(COLUMN_PAGE_URI)));
                    java.lang.String species = cursor.getString(cursor.getColumnIndex(COLUMN_SPECIES));
                    java.lang.String scientificName = cursor.getString(cursor.getColumnIndex(COLUMN_SCIENTIFICNAME));
                    int temperature_min = cursor.getInt(cursor.getColumnIndex(COLUMN_TEMPERATURE_MIN));
                    int temperature_max = cursor.getInt(cursor.getColumnIndex(COLUMN_TEMPERATURE_MAX));
                    int humidity_min = cursor.getInt(cursor.getColumnIndex(COLUMN_HUMIDITY_MIN));
                    int humidity_max = cursor.getInt(cursor.getColumnIndex(COLUMN_HUMIDITY_MAX));
                    int light_intensity_min = cursor.getInt(cursor.getColumnIndex(COLUMN_LIGHT_INTENSITY_MIN));
                    int light_intensity_max = cursor.getInt(cursor.getColumnIndex(COLUMN_LIGHT_INTENSITY_MAX));
                    int soil_moisture_limit = cursor.getInt(cursor.getColumnIndex(COLUMN_SOIL_MOISTURE_LIMIT));

                    ArchiveHolderContent item = new ArchiveHolderContent(
                            imageUri,
                            pageUri,
                            plantNameKor,
                            species,
                            scientificName,
                            temperature_min,
                            temperature_max,
                            humidity_min,
                            humidity_max,
                            light_intensity_min,
                            light_intensity_max,
                            soil_moisture_limit
                    );

                    matchingItems.add(item);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return matchingItems;
    }

    public MissionHolderContent.MissionHolderItem getMissionById(String missionId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME_MISSION,  // The table to query
                null,                // The columns to return; null returns all columns
                COLUMN_MISSION_ID + " = ?",  // The columns for the WHERE clause
                new String[]{missionId},     // The values for the WHERE clause
                null,                // Don't group the rows
                null,                // Don't filter by row groups
                null                 // The sort order
        );

        if (cursor != null && cursor.moveToFirst()) {
            String plantId = cursor.getString(cursor.getColumnIndex(COLUMN_PLANT_ID));
            String userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
            String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
            String missionName = cursor.getString(cursor.getColumnIndex(COLUMN_MISSION_NAME));
            String missionIcon = cursor.getString(cursor.getColumnIndex(COLUMN_MISSION_ICON));
            String missionPoint = cursor.getString(cursor.getColumnIndex(COLUMN_MISSION_POINT));
            float intimacyPoint = cursor.getFloat(cursor.getColumnIndex(COLUMN_INTIMACY_POINT));

            cursor.close();

            MissionHolderContent.MissionHolderItem missionItem = new MissionHolderContent.MissionHolderItem(
                    time, missionName, missionIcon, missionPoint, intimacyPoint
            );

            return missionItem;
        } else {
            if(cursor != null) {
                cursor.close();
            }
            return null;
        }
    }

}

