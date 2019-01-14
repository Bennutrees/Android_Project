package com.example.benjamin.roomwordsample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import java.util.List;

public class ImageViewModel extends AndroidViewModel {

    private ImageRepository mRepository;
    private LiveData<List<Image>> mAllImages;

    public ImageViewModel(Application application) {
        super(application);
        mRepository = new ImageRepository(application);
        mAllImages = mRepository.getAllImages();
    }

    LiveData<List<Image>> getAllImages() {
        return mAllImages;
    }

    public void insert(Image image) {
        mRepository.insert(image);
    }
}
