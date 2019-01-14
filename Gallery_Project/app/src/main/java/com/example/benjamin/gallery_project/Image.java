package com.example.benjamin.gallery_project;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

@Entity(tableName = "images_table")

public class Image {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "image")
    private Drawable mImage;

    public Image(@NonNull Drawable image) {
        this.mImage = image;
    }

    public Drawable getImage() {
        return this.mImage;
    }
}
