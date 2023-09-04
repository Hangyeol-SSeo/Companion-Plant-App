package com.eywa.myplant;

import android.app.Application;

public class Global extends Application {
    public static final String SERVER_URL = "http://10.0.2.2:3000";
    public static final String PREFERENCES_NAME = "UserData";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
