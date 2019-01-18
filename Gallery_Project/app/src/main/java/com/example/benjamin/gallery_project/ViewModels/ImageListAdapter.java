package com.example.benjamin.gallery_project.ViewModels;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benjamin.gallery_project.Database.Image;
import com.example.benjamin.gallery_project.GetInformationsActivity;
import com.example.benjamin.gallery_project.R;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageItemView;
        private final TextView textItemView;

        private ImageViewHolder(View itemView) {
            super(itemView);
            imageItemView = itemView.findViewById(R.id.imageView);
            textItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Image> mImages; // Cached copy of images

    public ImageListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_image_item, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        if (mImages != null) {
            Image current = mImages.get(position);
            holder.imageItemView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.imageItemView.setImageURI(current.getMUri());
            /*holder.imageItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent getInformationsIntent = new Intent(this, GetInformationsActivity.class);
                }
            });*/
            String string = "Picture URI : " + current.getMUri().toString();
            holder.textItemView.setText(string);
            /*string = "\nPeople tagged : " + current.getMContacts().toString();
            holder.textItemView.append(string);
            string = "\nEvent : " + current.getMEvent();
            holder.textItemView.append(string);
            string = "\nPlace tags : " + current.getMPlace().toString();
            holder.textItemView.append(string);
            string = "\nAbout : " + current.getMElements().toString();
            holder.textItemView.append(string);
            /*if (!current.getMContacts().isEmpty()) {
                string = "\nPeople tagged : " + current.getMContacts().toString();
                holder.textItemView.append(string);
            }
            if (current.getMEvent() != "") {
                string = "\nEvent : " + current.getMEvent();
                holder.textItemView.append(string);
            }
            if (!current.getMPlace().isEmpty()) {
                string = "\nPlace tags : " + current.getMPlace().toString();
                holder.textItemView.append(string);
            }
            if (!current.getMElements().isEmpty()) {
                string = "\nAbout : " + current.getMElements().toString();
                holder.textItemView.append(string);
            }*/


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