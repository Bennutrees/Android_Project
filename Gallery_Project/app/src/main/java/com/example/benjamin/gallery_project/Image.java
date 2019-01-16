package com.example.benjamin.gallery_project;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.arch.persistence.room.ColumnInfo;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.Date;

@Entity(tableName = "images_table")
public class Image {

    @PrimaryKey/*(autoGenerate = true)
    private int id;
    */
    @NonNull
    @ColumnInfo(name = "Uri")
    private Uri mUri;

    @Nullable
    @ColumnInfo(name = "Contacts")
    private String[] mContacts;

    /*@Nullable
    @ColumnInfo(name = "Event")
    private long mTime;*/

    public Image(Uri uri, String[] contacts) {//, long time) {
        this.mUri = uri;
        this.mContacts = contacts;
        //this.mTime = time;
    }

    //public int getId() { return this.id; }
    //public void setId(int id) { this.id = id; }

    @NonNull
    public Uri getUri() { return mUri; }

    public void setUri(@NonNull Uri mUri) { this.mUri = mUri; }

    @Nullable
    public String[] getContacts() { return mContacts; }

    public void setContacts(@Nullable String[] mContacts) { this.mContacts = mContacts; }

}
