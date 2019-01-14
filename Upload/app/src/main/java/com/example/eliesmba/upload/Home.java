package com.example.eliesmba.upload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity implements View.OnClickListener {
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //contact = (Button) findViewById(R.id.contact);
        create = (Button) findViewById(R.id.create);
        //event = (Button) findViewById(R.id.event);

        create.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == create.getId()){
            Intent i = new Intent(Home.this, view.class);
            startActivity(i);
        }
    }
}
