package com.invaliddomain.myfirstproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.message);

        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }

}


/*
//Set up questions & response options
//Now as default, "edit time"
//Fetch current to-date record for display on open

//On Android 7.1 (API level 25) and below, importance of each notification is determined by the notification's priority.

https://developer.android.com/topic/libraries/architecture/workmanager/

https://developers.google.com/drive/api/v3/manage-uploads

File fileMetadata = new File();
fileMetadata.setName("photo.jpg");
java.io.File filePath = new java.io.File("files/photo.jpg");
FileContent mediaContent = new FileContent("image/jpeg", filePath);
File file = driveService.files().create(fileMetadata, mediaContent)
    .setFields("id")
    .execute();
System.out.println("File ID: " + file.getId());

 */