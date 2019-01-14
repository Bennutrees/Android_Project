package com.example.benjamin.gallery_project;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ImageRepository {

    private ImageDao mImageDao;
    private LiveData<List<Image>> mAllImages;

    ImageRepository(Application application) {
        ImageRoomDatabase db = ImageRoomDatabase.getDatabase(application);
        mImageDao = db.imageDao();
        mAllImages = mImageDao.getAllImages();
    }

    LiveData<List<Image>> getAllImages() {
        return mAllImages;
    }

    public void insert(Image image) {
        new insertAsyncTask(mImageDao).execute(image);
    }

    private static class insertAsyncTask extends AsyncTask<Image, Void, Void> {

        private ImageDao mAsyncTaskDao;

        insertAsyncTask(ImageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Image... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
