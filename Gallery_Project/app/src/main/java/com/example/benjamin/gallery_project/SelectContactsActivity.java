package com.example.benjamin.gallery_project;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import static com.example.benjamin.gallery_project.UploadImageActivity.EXTRA_REPLY;

public class SelectContactsActivity extends AppCompatActivity {

    private Button save;
    private ListView listContact;
    private TextView nomContact;
    private ArrayList<String> taggedContacts;
    private String itemContact;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts);

        taggedContacts = getIntent().getStringArrayListExtra("taggedContacts");
        nomContact = findViewById(R.id.nomContact);

        listContact = findViewById(R.id.listContact);
        listContact.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                itemContact = listContact.getItemAtPosition(position).toString();
                if (itemContact != null) {
                    //if (taggedContacts == null) { taggedContacts = new ArrayList<String>(); }

                    Boolean contactAlreadyTagged = false;
                    for (String contact : taggedContacts) {
                        if (itemContact == contact) { contactAlreadyTagged = true; }
                    }
                    if (contactAlreadyTagged == false) {
                        taggedContacts.add(itemContact);
                    } else {
                        taggedContacts.remove(itemContact);
                    }
                    nomContact.setText(taggedContacts.toString());
                }
            }
        });

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (taggedContacts.isEmpty()) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    replyIntent.putStringArrayListExtra(EXTRA_REPLY, taggedContacts);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        showContacts();
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            List<String> contacts = getContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            listContact.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return contacts;
    }
}

