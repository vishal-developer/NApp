package com.noon.napp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.noon.napp.model.User;

import java.util.List;

/**
 * Created by ril on 3/2/18.
 */

@Dao
public interface UserDao {

    @Query("select *from user")
    List<User> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(User... user);
}
