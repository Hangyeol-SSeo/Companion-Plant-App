package com.eywa.myplant;

import android.app.Application;

public class Global extends Application {
    public static final String SERVER_URL = "http://ec2-15-165-116-114.ap-northeast-2.compute.amazonaws.com:3000";
    public static final String PREFERENCES_NAME = "UserData";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
