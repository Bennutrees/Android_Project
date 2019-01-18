package com.example.benjamin.gallery_project.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.arch.persistence.room.ColumnInfo;
import android.support.annotation.Nullable;

import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "images_table")
public class Image {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Uri")
    private Uri mUri;

    @NonNull
    public Uri getMUri() { return mUri; }

    public void setMUri(@NonNull Uri mUri) { this.mUri = mUri; }



    @Nullable
    @ColumnInfo(name = "People tagged")
    private ArrayList<String> mContacts;

    @Nullable
    public ArrayList<String> getMContacts() { return mContacts; }

    public void setMContacts(@Nullable ArrayList<String> mContacts) { this.mContacts = mContacts; }



    @Nullable
    @ColumnInfo(name = "Event associated")
    private String mEvent;

    @Nullable
    public String getMEvent() { return mEvent; }

    public void setMEvent(@Nullable String mEvent) { this.mEvent = mEvent; }



    @Nullable
    @ColumnInfo(name = "Place tags")
    private ArrayList<String> mPlace;

    @Nullable
    public ArrayList<String> getMPlace() { return mPlace; }

    public void setMPlace(@Nullable ArrayList<String> mPlace) { this.mPlace = mPlace; }



    @Nullable
    @ColumnInfo(name = "Elements tagged")
    private ArrayList<String> mElements;

    @Nullable
    public ArrayList<String> getMElements() { return mElements; }

    public void setMElements(@Nullable ArrayList<String> mElements) { this.mElements = mElements; }


    public Image() {
    }

    public Image(@NonNull Uri Uri, @Nullable ArrayList<String> Contacts, @Nullable String Event,
                 @Nullable ArrayList<String> PlaceTags, @Nullable ArrayList<String> Elements) {
        this.mUri = Uri;
        this.mContacts = Contacts;
        this.mEvent = Event;
        this.mPlace = PlaceTags;
        this.mElements = Elements;
    }
}
