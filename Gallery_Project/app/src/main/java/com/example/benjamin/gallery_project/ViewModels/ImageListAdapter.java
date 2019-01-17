package com.example.benjamin.gallery_project.ViewModels;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.benjamin.gallery_project.Database.Image;
import com.example.benjamin.gallery_project.R;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageItemView;

        private ImageViewHolder(View itemView) {
            super(itemView);
            imageItemView = itemView.findViewById(R.id.imageView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Image> mImages; // Cached copy of images

    public ImageListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        if (mImages != null) {
            Image current = mImages.get(position);
            holder.imageItemView.setImageURI(current.getUri());
        } else {

            // TODO in case data not being ready yet

        }
    }

    public void setImages(List<Image> images) {
        mImages = images;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        if (mImages != null)
            return mImages.size();
        else return 0;
    }
}