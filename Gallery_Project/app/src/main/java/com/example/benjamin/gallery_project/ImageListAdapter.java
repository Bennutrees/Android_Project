package com.example.benjamin.gallery_project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private final TextView imageItemView;

        private ImageViewHolder(View itemView) {
            super(itemView);
            imageItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Image> mImages;

    ImageListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder imageViewHolder, int position) {
        if (mImages != null) {
            Image current = mImages.get(position);
            imageViewHolder.imageItemView.setText(current.getImage());
        } else {
            // Covers the case of data not being ready yet.
            imageViewHolder.imageItemView.setText("No Image");
        }
    }

    void setImages(List<Image> images) {
        mImages = images;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mImages has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mImages != null)
            return mImages.size();
        else return 0;
    }
}
