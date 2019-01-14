package com.example.benjamin.roomwordsample;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ImageDao {

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    void insert(Image image);

    @Query("DELETE FROM Image")
    void deleteAll();

    @Query("SELECT * from Image ORDER BY word ASC")
    LiveData<List<Image>> getAllImages();
}
