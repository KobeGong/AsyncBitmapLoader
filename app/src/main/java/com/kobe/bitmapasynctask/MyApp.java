package com.kobe.bitmapasynctask;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by kobe-mac on 15/5/20.
 */
public class MyApp extends Application {

    public static RefWatcher getRefWatcher(Context context) {
        MyApp application = (MyApp) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
