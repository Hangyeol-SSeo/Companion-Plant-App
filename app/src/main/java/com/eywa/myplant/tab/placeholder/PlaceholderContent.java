package com.eywa.myplant.tab.placeholder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceholderContent {

    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();
    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();

    public static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class PlaceholderItem {
        public final String id;
        public final String user;
        public final String nickname;
        public final String realname;

        public PlaceholderItem(String id, String user, String nickname, String realname) {
            this.id = id;
            this.user = user;
            this.nickname = nickname;
            this.realname = realname;
        }
    }
}