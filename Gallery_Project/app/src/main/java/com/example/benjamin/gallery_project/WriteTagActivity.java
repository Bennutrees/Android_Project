package com.example.benjamin.gallery_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.benjamin.gallery_project.UploadImageActivity.EXTRA_REPLY;

public class WriteTagActivity extends AppCompatActivity {

    private EditText mEditWordView;
    private FloatingActionButton addTag;
    private Button saveTags;
    private LinearLayout tagContainer;
    private ArrayList<String> taggedList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_written_tag);
        mEditWordView = findViewById(R.id.edit_word);
        tagContainer = (LinearLayout) findViewById(R.id.container);
        taggedList.addAll(getIntent().getStringArrayListExtra("tagList"));

        for (String tag : taggedList) {
            showItem(tag);
        }

        addTag = findViewById(R.id.add_button);
        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mEditWordView.getText())) {

                    final String currentTag = mEditWordView.getText().toString();
                    boolean isItemTagged = false;
                    for (String tag: taggedList) {
                        if (currentTag.toLowerCase() ==  tag.toLowerCase()) { isItemTagged = true; }
                    }
                    if (!isItemTagged) {
                        showItem(currentTag);
                        taggedList.add(currentTag);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(WriteTagActivity.this).create();
                        alertDialog.setTitle("Woops !");
                        alertDialog.setMessage("Write a tag not already added !");
                        alertDialog.show();
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(WriteTagActivity.this).create();
                    alertDialog.setTitle("Woops !");
                    alertDialog.setMessage("Write a tag before adding it !");
                    alertDialog.show();
                }
            }
        });

        saveTags = findViewById(R.id.save_tags_button);
        saveTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (taggedList.isEmpty()) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    replyIntent.putStringArrayListExtra(EXTRA_REPLY, taggedList);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    private void showItem(final String tag) {
        LayoutInflater layoutInflater =(LayoutInflater)
                getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View tagView = layoutInflater.inflate(R.layout.tag_item, null);

        final TextView tagItem = (TextView) tagView.findViewById(R.id.tag_item);
        tagItem.setText(tag);

        FloatingActionButton deleteButton = (FloatingActionButton) tagView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LinearLayout)tagView.getParent()).removeView(tagView);
                taggedList.remove(tag);
            }
        });
        tagContainer.addView(tagView);
    }
}
