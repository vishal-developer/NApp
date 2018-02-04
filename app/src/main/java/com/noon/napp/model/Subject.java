package com.noon.napp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.noon.napp.BR;

import io.reactivex.annotations.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by ril on 3/2/18.
 */

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "fbId",
        childColumns = "userId",
        onDelete = CASCADE))
public class Subject extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int subjectId;

    @Bindable
    @NonNull
    private String name;

    @Bindable
    private String description;

    @Bindable
    private String iconUrl;

    private String userId;


    public Subject() {
    }

    public Subject(String name, String description, String iconUrl, String userId) {
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.userId = userId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        notifyPropertyChanged(BR.iconUrl);
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
