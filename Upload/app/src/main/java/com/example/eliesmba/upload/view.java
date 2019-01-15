package com.example.eliesmba.upload;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class view extends AppCompatActivity implements View.OnClickListener {
    Button upB, next;
    ImageView upIm;


    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        upB = (Button) findViewById(R.id.buttonUpload);

        upIm = (ImageView) findViewById(R.id.imageUpload);

        next = (Button) findViewById(R.id.buttonNext);
        next.setVisibility(View.GONE);

        // Le bouton d'upload d'image

        upB.setOnClickListener(this);
        next.setOnClickListener(this);

    }

    public void onClick(View v){
        if(v.getId() == upB.getId()){
            Intent gallerieIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallerieIntent, RESULT_LOAD_IMAGE);
        }
        if(v.getId() == next.getId()){
            Intent i = new Intent(view.this, Contacts.class);
            startActivity(i);
        }
    }

    // Après avoir selectionné une image on l'affiche sur l'ImageView

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            upIm.setImageURI(selectedImage);
            next.setVisibility(View.VISIBLE);
        }
    }

}
