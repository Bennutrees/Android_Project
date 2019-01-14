package com.example.eliesmba.upload;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class view extends AppCompatActivity implements View.OnClickListener {
    Button upB, downB;
    EditText upT, downT;
    ImageView upIm, downIm;

    private static final int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        upB = (Button) findViewById(R.id.buttonUpload);
        downB = (Button) findViewById(R.id.buttonUpload);

        upT = (EditText) findViewById(R.id.uploadTitle);
        downT = (EditText) findViewById(R.id.downloadTitle);

        upIm = (ImageView) findViewById(R.id.imageUpload);
        downIm = (ImageView) findViewById(R.id.imageDownload);


        // l'image à upload lorsqu'on clique dessus

        upIm.setOnClickListener(this);

        // Le bouton d'upload

        upB.setOnClickListener(this);

        // Le bouton de download

        downB.setOnClickListener(this);
    }

    public void onClick(View v){
        if(v.getId() == upIm.getId()){

        }

        // Si le bouton upload est cliqué

        else if(v.getId() == upB.getId()){
            Intent gallerieIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallerieIntent, RESULT_LOAD_IMAGE);
        }
    }

    // Après avoir selectionné une image on l'affiche sur l'ImageView

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            upIm.setImageURI(selectedImage);
        }
    }
}
