package com.noon.napp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by ril on 3/2/18.
 */

@Entity
public class User {


    private int userId;

    @PrimaryKey
    @NonNull
    private String fbId;
    private String name;
    private String email;

    public User(){}
    public User(String fbId, String name, String email) {
        this.userId = userId;
        this.fbId = fbId;
        this.name = name;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public String getFbId() {
        return fbId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", fbId='" + fbId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
