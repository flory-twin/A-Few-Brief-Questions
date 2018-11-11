package com.invaliddomain.myfirstproject;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;
import com.invaliddomain.myfirstproject.questions.DateTimeQuestion;
import com.invaliddomain.myfirstproject.questions.DateTimeQuestionLayout;
import com.invaliddomain.myfirstproject.questions.base.Question;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String message = "This is a message.";

    private InMemoryDataRecord questionRecords;
    private ArrayList<DateTimeQuestionLayout> dtQuestionViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.setContentView(dtqv.getQuestionAnswerLayout());
        this.setContentView(R.layout.activity_main);

        this.dtQuestionViews = new ArrayList<DateTimeQuestionLayout>();
        this.setUpQuestions();
        this.addChildElements();


    }


    protected void addChildElements() {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // fill in any details dynamically here
        LinearLayout root = (LinearLayout) this.findViewById(R.id.RootLayout);
        ArrayList<Question> questions = questionRecords.getQuestionList();

        ConstraintSet constraints = new ConstraintSet();
        int margin = 10;
        DateTimeQuestionLayout lastView = null;
        for (int i = 0; i < questions.size(); i++) {
            //Needs a better way once other question types are added!
            DateTimeQuestionLayout qView = new DateTimeQuestionLayout(
                    this.getApplicationContext(),
                    questions.get(i).getQuestionAsText());
            int x = 1+2;
            this.dtQuestionViews.add(qView);
            root.addView(qView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        root.invalidate();
    }

            /*
    // use this to start and trigger a service
Intent i= new Intent(context, DataSyncService.class);
// potentially add data to the intent
i.putExtra("KEY1", "Value to be used by the service");
context.startService(i);
     */


        //Use Intent to communicate start and destroy.


    private void setUpQuestions()
    {
        questionRecords = new InMemoryDataRecord();
        Question a = new DateTimeQuestion("What time is it?");
        this.questionRecords.addQuestion(a);
        Question b = new DateTimeQuestion("What time was it?");
        this.questionRecords.addQuestion(b);
//        Question c = new DateTimeQuestion("What time shall it be?");
//        this.questionRecords.addQuestion(c);

    }

}
