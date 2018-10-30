package com.invaliddomain.myfirstproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String message = "This is a message.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
     * "Called when the user taps the Send button."
     */
    public void onButtonPress(View view)
    {
        //Intent(Context sendingContext, <Class toWhichToDispatchIntent>)
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(MainActivity.message, message);
        startActivity(intent);
    }
}
