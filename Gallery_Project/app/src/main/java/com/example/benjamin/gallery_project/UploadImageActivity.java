package com.example.benjamin.gallery_project;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class UploadImageActivity extends AppCompatActivity {

    private Button upload;
    private ImageView uploadedImage;
    private static final int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadedImage = findViewById(R.id.uploaded_image);
        uploadedImage.setVisibility(View.GONE);

        upload = findViewById(R.id.upload_button);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallerieIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallerieIntent, RESULT_LOAD_IMAGE);
            }
        });

        //final Button setcontact = findViewById(R.id.contact);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            uploadedImage.setVisibility(View.VISIBLE);
            uploadedImage.setImageURI(selectedImage);
            //setResult(RESULT_OK);
        }
        //finish();
    }

    /*@Override
    public void onClick(View v) {
        Intent i = new Intent(UploadImageActivity.this, ImageViewModel.class);
    }*/
}
