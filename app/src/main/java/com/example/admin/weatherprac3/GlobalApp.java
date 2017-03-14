package com.example.admin.weatherprac3;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by admin on 2017-03-08.
 */

public class GlobalApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
