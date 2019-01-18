package com.example.benjamin.gallery_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UploadImageActivity extends AppCompatActivity {

    private ImageView uploadedImage;
    private Button uploadButton, takePicButton;
    private TextView contactTags, eventTag, placeTags, elementsTags;
    private Button contactsButton, eventButton, placeButton, elementsButton;
    private Button save_cancelButton;
    private static final int REQUEST_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static Uri imageURIResult;
    private boolean isImageLoaded;
    private static final int REQUEST_LOAD_CONTACT = 3;
    private static ArrayList<String> contactsResult = new ArrayList<String>() {};
    private static final int REQUEST_LOAD_EVENT = 4;
    private static String eventResult = "";
    private static final int REQUEST_LOAD_PLACE = 5;
    private static ArrayList<String> placeResult = new ArrayList<String>() {};
    private static final int REQUEST_LOAD_ELEMENTS = 6;
    private static ArrayList<String> elementsResult = new ArrayList<String>() {};

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // Either upload or take a photo
        uploadedImage = findViewById(R.id.uploaded_image);
        uploadedImage.setVisibility(View.GONE);
        isImageLoaded = false;

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
                contactsIntent.putStringArrayListExtra("taggedContacts", contactsResult);
                startActivityForResult(contactsIntent, REQUEST_LOAD_CONTACT);
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
                Intent eventIntent = new Intent(UploadImageActivity.this, SelectEventActivity.class);
                startActivityForResult(eventIntent, REQUEST_LOAD_EVENT);
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
                Intent writePlaceIntent = new Intent(UploadImageActivity.this, WriteTagActivity.class);
                writePlaceIntent.putStringArrayListExtra("tagList", placeResult);
                startActivityForResult(writePlaceIntent, REQUEST_LOAD_PLACE);
            }
        });


        // Add elements tags
        elementsTags = findViewById(R.id.element_tags);
        elementsTags.setVisibility(View.GONE);

        elementsButton = findViewById(R.id.elements_button);
        elementsButton.setVisibility(View.GONE);
        elementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent writeElementsIntent = new Intent(UploadImageActivity.this, WriteTagActivity.class);
                writeElementsIntent.putStringArrayListExtra("tagList", elementsResult);
                startActivityForResult(writeElementsIntent, REQUEST_LOAD_ELEMENTS);
            }
        });


        // Save data and terminate the activity
        save_cancelButton = findViewById(R.id.save_upload_button);
        save_cancelButton.setText("Cancel");
        save_cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (!isImageLoaded) {
                    setResult(RESULT_CANCELED,replyIntent);
                } else {
                    Bundle extras = new Bundle();
                    extras.putString("uploadedImageURI",imageURIResult.toString());
                    extras.putStringArrayList("taggedContacts", contactsResult);
                    extras.putString("taggedEvent", eventResult);
                    extras.putStringArrayList("taggedPlaces", placeResult);
                    extras.putStringArrayList("taggedElements", elementsResult);
                    replyIntent.putExtras(extras);
                    setResult(RESULT_OK,replyIntent);
                }
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            this.setNewUri(data.getData());
            this.uploadedImage.setImageURI(data.getData());
            this.setNewVisibility();
            this.isImageLoaded = true;
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            this.setNewUri(data.getData());
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            this.uploadedImage.setImageBitmap(imageBitmap);
            this.setNewVisibility();
            this.isImageLoaded = true;
        }
        if (requestCode == REQUEST_LOAD_CONTACT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> contactsArray = data.getStringArrayListExtra(EXTRA_REPLY);
            this.contactsResult.addAll(contactsArray);
            String contacts = contactsArray.toString();
            contactTags.setText(contacts);
        }
        if (requestCode == REQUEST_LOAD_EVENT && resultCode == RESULT_OK && data != null) {
            String event = data.getStringExtra(EXTRA_REPLY);
            eventTag.setText(event);
        }
        if (requestCode == REQUEST_LOAD_PLACE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> placesArray = data.getStringArrayListExtra(EXTRA_REPLY);
            this.placeResult.addAll(placesArray);
            String places = placesArray.toString();
            placeTags.setText(places);
        }
        if (requestCode == REQUEST_LOAD_ELEMENTS && resultCode == RESULT_OK && data != null) {
            ArrayList<String> elementsArray = data.getStringArrayListExtra(EXTRA_REPLY);
            this.elementsResult.addAll(elementsArray);
            String contacts = elementsArray.toString();
            elementsTags.setText(contacts);
        }
    }

    private void setNewUri(Uri newUri) {
        this.imageURIResult = newUri;
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
        elementsTags.setVisibility(View.VISIBLE);
        elementsButton.setVisibility(View.VISIBLE);
        save_cancelButton.setVisibility(View.VISIBLE);
        save_cancelButton.setText("Save");
    }

    /*String everyDataString = "";
    everyDataString += imageURIResult.toString() + ";";
    everyDataString += putArrayToString(contactsResult) + ";";
    everyDataString += eventResult + ";";
    everyDataString += putArrayToString(placeResult) + ";";
    everyDataString += putArrayToString(elementsResult) + ";";
    replyIntent.putExtra("imageResult", everyDataString);

    public String putArrayToString(ArrayList<String> arrayList) {
        String dataString = "";
        int i = 1;
        for (String element : arrayList) {
            dataString += element;
            if (i < arrayList.size()) dataString += ",";
            i += 1;
        }
        return dataString;
    }*/
}
