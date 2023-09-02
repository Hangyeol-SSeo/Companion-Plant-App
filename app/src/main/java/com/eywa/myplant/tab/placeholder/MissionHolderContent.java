package com.eywa.myplant.tab.placeholder;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MissionHolderContent {

    public static final List<MissionHolderContent.MissionHolderItem> ITEMS = new ArrayList<MissionHolderContent.MissionHolderItem>();
    public static final Map<String, MissionHolderContent.MissionHolderItem> ITEM_MAP = new HashMap<String, MissionHolderContent.MissionHolderItem>();

    public static void addItem(MissionHolderContent.MissionHolderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.missionId, item);
    }

    public static void removeItem(MissionHolderContent.MissionHolderItem item) {
        ITEMS.remove(item);
        ITEM_MAP.remove(item.missionId);
    }

    public static class MissionHolderItem {
        public final String missionId;
        public final String userId;
        public String plantId;
        public String plantname;
        public String missionName;
        public String missionIcon;
        public String missionPoint;
        public float intimacyPoint;

        public MissionHolderItem(String plantName, String missionName, String missionIcon, String missionPoint, float intimacyPoint) {
            this.missionId = UUID.randomUUID().toString();
            this.userId = "2";
            this.plantId = "0";
            this.plantname = plantName;
            this.missionName = missionName;
            this.missionIcon = missionIcon;
            this.missionPoint = missionPoint + " Point";
            this.intimacyPoint = intimacyPoint;
        }
    }
}
