package com.example.benjamin.gallery_project;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Database(entities = {Image.class}, version = 1, exportSchema = false)
@TypeConverters({ImageRoomDatabase.Converters.class})
public abstract class ImageRoomDatabase extends RoomDatabase {

    public abstract ImageDao imageDao();

    private static volatile ImageRoomDatabase INSTANCE;

    static ImageRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ImageRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ImageRoomDatabase.class, "image_database")
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Converters to store into database
    public static class Converters {
        @TypeConverter
        public static Uri uriFromString(String value) {
            Type type = new TypeToken<List<Uri>>() {}.getType();
            return new Gson().fromJson(value, type);
        }
        @TypeConverter
        public static String fromUri(Uri uri) {
            Gson gson = new Gson();
            String json = gson.toJson(uri);
            return json;
        }
        @TypeConverter
        public static String[] arrayFromString(String value) {
            Type type = new TypeToken<List<String[]>>() {}.getType();
            return new Gson().fromJson(value, type);
        }
        @TypeConverter
        public static String fromStringArray(String[] stringArray) {
            Gson gson = new Gson();
            String json = gson.toJson(stringArray);
            return json;
        }
    }



    // To delete all content and repopulate the database whenever the app is started
    private static RoomDatabase.Callback sRoomDatabaseCallback =
        new RoomDatabase.Callback() {

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new DepopulateDbAsync(INSTANCE).execute();
        }
    };

    // AsyncTask that deletes the contents of the database
    private static class DepopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ImageDao mDao;

        DepopulateDbAsync(ImageRoomDatabase db) {
            mDao = db.imageDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deletAll();
            //Image image = new Image(...);
            //mDao.insert(image);
            return null;
        }
    }
}
