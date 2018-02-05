package com.noon.napp;

import android.app.Application;

import com.noon.napp.manager.AppComponent;
import com.noon.napp.manager.AppModule;
import com.noon.napp.manager.DBManager;
import com.noon.napp.manager.DaggerAppComponent;

/**
 * Created by ril on 3/2/18.
 */

public class NApp extends Application {

    public static final String TAG = NApp.class.getSimpleName();
    private AppComponent appComponent;
    private String userId;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dBManager(new DBManager(this))
                .build();
    }


    public AppComponent getAppComponent() {
        return appComponent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
