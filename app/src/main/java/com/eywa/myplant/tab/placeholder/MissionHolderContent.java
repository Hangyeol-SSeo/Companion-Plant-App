package com.eywa.myplant.tab.placeholder;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MissionHolderContent {

    public static final List<MissionHolderContent.MissionHolderItem> ITEMS = new ArrayList<MissionHolderContent.MissionHolderItem>();
    public static final Map<java.lang.String, MissionHolderContent.MissionHolderItem> ITEM_MAP = new HashMap<java.lang.String, MissionHolderContent.MissionHolderItem>();

    public static void addItem(MissionHolderContent.MissionHolderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.missionId, item);
    }

    public static void removeItem(MissionHolderContent.MissionHolderItem item) {
        ITEMS.remove(item);
        ITEM_MAP.remove(item.missionId);
    }

    public static class MissionHolderItem {
        public final java.lang.String missionId;
        public final java.lang.String userId;
        public java.lang.String plantId;
        public java.lang.String time;
        public java.lang.String missionName;
        public java.lang.String missionIcon;
        public java.lang.String missionPoint;
        public float intimacyPoint;

        public MissionHolderItem(java.lang.String time, java.lang.String missionName, java.lang.String missionIcon, java.lang.String missionPoint, float intimacyPoint) {
            this.missionId = UUID.randomUUID().toString();
            this.userId = "2"; // 기본값으로 "2"를 사용
            this.plantId = "0";
            this.time = time;
            this.missionName = missionName;
            this.missionIcon = missionIcon;
            this.missionPoint = missionPoint + " Point";
            this.intimacyPoint = intimacyPoint;
        }
    }
}
