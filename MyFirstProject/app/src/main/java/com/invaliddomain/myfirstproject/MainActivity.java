package com.invaliddomain.myfirstproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String message = "This is a message.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.setContentView(dtqv.getQuestionAnswerLayout());
        this.setContentView(R.layout.activity_main);
    }



    //Use Intent to communicate start and destroy.
}
