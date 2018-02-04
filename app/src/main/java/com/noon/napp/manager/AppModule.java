package com.noon.napp.manager;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ril on 5/2/18.
 */

@Module
public class AppModule {

    Application application;

    public AppModule(Application mApplication) {
        this.application = mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }
}
