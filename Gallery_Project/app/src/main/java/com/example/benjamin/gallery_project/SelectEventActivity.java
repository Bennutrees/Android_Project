package com.example.benjamin.gallery_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.CalendarView;

import com.example.benjamin.gallery_project.ViewModels.EventAdapter;

import java.util.Calendar;

import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.OnItemActivatedListener;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;

import static com.example.benjamin.gallery_project.UploadImageActivity.EXTRA_REPLY;

public class SelectEventActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener, OnItemActivatedListener<Long> {

    private static final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 1 ;
    private EventAdapter adapter;

    private CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_event);

        adapter = new EventAdapter(this);
        RecyclerView eventList = (RecyclerView) findViewById(R.id.eventList);
        eventList.setAdapter(adapter);
        eventList.setLayoutManager(new LinearLayoutManager(this));

        SelectionTracker tracker = new SelectionTracker.Builder<>(
                "my-long-selection",
                eventList,
                new StableIdKeyProvider(eventList),
                new EventAdapter.EventDetailsLookup(eventList),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.<Long>createSelectSingleAnything())
                .withOnItemActivatedListener(this)
                .build();

        adapter.setSelectionTracker(tracker);
        calendar = findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(this);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CALENDAR)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.


                //

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALENDAR},
                        MY_PERMISSIONS_REQUEST_READ_CALENDAR);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(calendar.getDate());
            c.set(Calendar.HOUR_OF_DAY,0);
            c.set(Calendar.MINUTE,0);
            adapter.readEvents(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        }
        //adapter.readEvents();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CALENDAR: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    adapter.readEvents(calendar.getDate());
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    this.finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        // année, jour et mois de la date selectionnée
        adapter.readEvents(year,month,dayOfMonth);
    }

    @Override
    public boolean onItemActivated(@NonNull ItemDetailsLookup.ItemDetails<Long> itemDetails, @NonNull MotionEvent motionEvent) {

        Log.d("onItemActivated","Activated : "+itemDetails.getSelectionKey());
        String id = itemDetails.getSelectionKey().toString();

        Uri selected= CalendarContract.Events.CONTENT_URI.buildUpon().appendPath(id).build();
        Log.d("onItemActivated",selected.toString());

        String stringEvent = adapter.getStringEvent();
        Intent replyIntent = new Intent();
        if (stringEvent.length() == 0) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            replyIntent.putExtra(EXTRA_REPLY, stringEvent);
            setResult(RESULT_OK, replyIntent);
        }
        finish();

        return false;
    }
}

