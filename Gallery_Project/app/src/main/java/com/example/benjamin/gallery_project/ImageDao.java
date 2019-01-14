package com.example.benjamin.gallery_project;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ImageDao {

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    void insert(Image image);

    @Query("DELETE FROM images_table")
    void deleteAll();

    @Query("SELECT * from images_table ORDER BY image ASC")
    LiveData<List<Image>> getAllImages();
}
