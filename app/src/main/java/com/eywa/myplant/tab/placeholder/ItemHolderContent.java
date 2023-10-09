package com.eywa.myplant.tab.placeholder;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemHolderContent {

    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();
    public static final Map<java.lang.String, PlaceholderItem> ITEM_MAP = new HashMap<java.lang.String, PlaceholderItem>();

    public static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void removeItem(PlaceholderItem item) {
        ITEMS.remove(item);
        ITEM_MAP.remove(item.id);
    }

    public static class PlaceholderItem {
        public final java.lang.String id;
        public final java.lang.String userId;
        public java.lang.String nickname;
        public java.lang.String realname;
        public Uri plantImageUri;
        public float light_intensity; // 햇빛
        public float soil_moisture; // 토양수분
        public float temperature; // 온도
        public float humidity; // 습도
        public boolean status;
        public float intimacy; // 친밀도(호감도)

        public PlaceholderItem(java.lang.String id, java.lang.String userId, java.lang.String nickname, java.lang.String realname, Uri plantImageUri) {
            this.id = id;
            this.userId = userId;
            this.nickname = nickname;
            this.realname = realname;
            this.plantImageUri = plantImageUri;
            this.light_intensity = 0;
            this.soil_moisture = 0;
            this.temperature = 0;
            this.humidity = 0;
            this.status = false;
            this.intimacy = 0;
        }
    }
}