package com.noon.napp.helper;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.noon.napp.dao.SubjectDao;
import com.noon.napp.dao.UserDao;
import com.noon.napp.model.Subject;
import com.noon.napp.model.User;

/**
 * Created by ril on 3/2/18.
 */

@Database(entities = {User.class, Subject.class}, version = 1)
public abstract class DBHelper extends RoomDatabase{
    public abstract UserDao userDao();
    public abstract SubjectDao subjectDao();
}

