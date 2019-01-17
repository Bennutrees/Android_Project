package com.example.benjamin.gallery_project;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UploadImageActivity extends AppCompatActivity {

    private ImageView uploadedImage;
    private Button uploadButton, takePicButton;
    private TextView contactTags, eventTag, placeTags, elementTags;
    private Button contactsButton, eventButton, placeButton, elementsButton;
    private Button saveButton, cancelButton;
    private static final int REQUEST_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_CONTACT = 1;
    private Uri IMAGE_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // Either upload or take a photo
        uploadedImage = findViewById(R.id.uploaded_image);
        uploadedImage.setVisibility(View.GONE);

        uploadButton = findViewById(R.id.upload_button);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_LOAD_IMAGE);
            }
        });
        takePicButton = findViewById(R.id.take_photo_button);
        takePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });


        // Add contacts tags
        contactTags = findViewById(R.id.contact_tags);
        contactTags.setVisibility(View.GONE);

        contactsButton = findViewById(R.id.contacts_button);
        contactsButton.setVisibility(View.GONE);
        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactsIntent = new Intent(UploadImageActivity.this, SelectContactsActivity.class);
                contactsIntent.putExtra("IMAGE_URI", IMAGE_URI.toString());
                startActivityForResult(contactsIntent, REQUEST_LOAD_CONTACT);

                // TODO get the ArrayList<String> of contacts
            }
        });


        // Add event tag
        eventTag = findViewById(R.id.event_tag);
        eventTag.setVisibility(View.GONE);

        eventButton = findViewById(R.id.event_button);
        eventButton.setVisibility(View.GONE);
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO link SelectEventActivity

            }
        });


        // Add place tags
        placeTags = findViewById(R.id.place_tags);
        placeTags.setVisibility(View.GONE);

        placeButton = findViewById(R.id.place_button);
        placeButton.setVisibility(View.GONE);
        placeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO write place tags

            }
        });


        // Add elements tags
        elementTags = findViewById(R.id.element_tags);
        elementTags.setVisibility(View.GONE);

        elementsButton = findViewById(R.id.elements_button);
        elementsButton.setVisibility(View.GONE);
        elementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO write elements tags

            }
        });


        // Save data and terminate the activity
        saveButton = findViewById(R.id.save_upload_button);
        saveButton.setVisibility(View.GONE);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IMAGE_URI != null) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        cancelButton = findViewById(R.id.cancel_upload_button);
        saveButton.setVisibility(View.GONE);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == REQUEST_LOAD_IMAGE | requestCode == REQUEST_IMAGE_CAPTURE)
                && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            IMAGE_URI = selectedImage;
            uploadedImage.setImageURI(selectedImage);
            setNewVisibility();
        }
        if(requestCode == REQUEST_LOAD_CONTACT && resultCode == RESULT_OK && data != null){

            // TODO set textview

        }
    }

    private void setNewVisibility() {
        uploadButton.setVisibility(View.GONE);
        takePicButton.setVisibility(View.GONE);

        uploadedImage.setVisibility(View.VISIBLE);
        contactTags.setVisibility(View.VISIBLE);
        contactsButton.setVisibility(View.VISIBLE);
        eventTag.setVisibility(View.VISIBLE);
        eventButton.setVisibility(View.VISIBLE);
        placeTags.setVisibility(View.VISIBLE);
        placeButton.setVisibility(View.VISIBLE);
        elementTags.setVisibility(View.VISIBLE);
        elementsButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
    }
}
