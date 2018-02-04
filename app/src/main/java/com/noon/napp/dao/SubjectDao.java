package com.noon.napp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.noon.napp.model.Subject;

import java.util.List;

/**
 * Created by ril on 3/2/18.
 */

@Dao
public interface SubjectDao {

    @Query("select *from subject")
    List<Subject> getAll();

    @Insert
    List<Long> insert(Subject... subject);

    @Delete
    int delete(Subject... subjects);

}
