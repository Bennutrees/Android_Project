package com.example.eliesmba.upload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        TextView t = (TextView) findViewById(R.id.title);
        Button upload = (Button) findViewById(R.id.upload);
        t.setText("Upload une photo");

        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(UploadActivity.this, view.class);
    }
}
