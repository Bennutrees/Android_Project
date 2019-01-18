package com.example.benjamin.gallery_project;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.benjamin.gallery_project.Database.Image;
import com.example.benjamin.gallery_project.ViewModels.ImageListAdapter;
import com.example.benjamin.gallery_project.ViewModels.ImageViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageViewModel mImageViewModel;
    private Uri imageURIResult = null;
    private ArrayList<String> contactsResult = new ArrayList<String>() {};
    private String eventResult = "";
    private ArrayList<String> placeResult = new ArrayList<String>() {};
    private ArrayList<String> elementsResult = new ArrayList<String>() {};
    //private String imageStringResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadImageActivity.class);

                startActivityForResult(intent, UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ImageListAdapter adapter = new ImageListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mImageViewModel = ViewModelProviders.of(this).get(ImageViewModel.class);
        mImageViewModel.getAllImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> images) {
                // Update the cached copy of the words in the adapter
                adapter.setImages(images);
            }
        });
    }

    public static final int UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE = 1;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            imageURIResult = Uri.parse(data.getStringExtra("uploadedImageURI"));
            contactsResult.addAll(data.getStringArrayListExtra("taggedContacts"));
            eventResult = data.getStringExtra("taggedEvent");
            placeResult.addAll(data.getStringArrayListExtra("taggedPlaces"));
            elementsResult.addAll(data.getStringArrayListExtra("taggedElements"));

            Image image = new Image(imageURIResult, contactsResult, eventResult, placeResult, elementsResult);
            mImageViewModel.insert(image);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> putStringToArrayList(String stringlist, String splitter){
        String[] array = stringlist.split(splitter);
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));
        return arrayList;
    }
}
