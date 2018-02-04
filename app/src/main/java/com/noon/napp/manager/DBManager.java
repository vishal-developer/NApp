package com.noon.napp.manager;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.noon.napp.R;
import com.noon.napp.helper.DBHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ril on 4/2/18.
 */

@Module
public class DBManager {

    private DBHelper dbHelper;

    public DBManager(Application application){
        dbHelper= Room.databaseBuilder(application,
                DBHelper.class, application.getString(R.string.app_name)).build();
    }

    @Singleton
    @Provides
    DBHelper provideDBHelper(){
        return dbHelper;
    }
}
