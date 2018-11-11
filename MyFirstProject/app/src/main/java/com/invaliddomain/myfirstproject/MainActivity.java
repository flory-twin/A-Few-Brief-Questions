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

        this.setUpQuestions();
        this.addChildElements();


    }


    protected void addChildElements() {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // fill in any details dynamically here
        ConstraintLayout root = (ConstraintLayout) this.findViewById(R.id.RootLayout);
        ArrayList<Question> questions = questionRecords.getQuestionList();

        ConstraintSet constraints = new ConstraintSet();
        constraints.clone(root);
        int margin = 10;
        DateTimeQuestionLayout lastView = null;
        for (int i = 0; i < questions.size(); i++) {
            //Needs a better way once other question types are added!
            DateTimeQuestionLayout qView = new DateTimeQuestionLayout(
                    this.getApplicationContext(),
                    questions.get(i).getQuestionAsText());
            root.addView(qView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            //qView.measure(root.getMeasuredWidth(), root.getMeasuredHeight());


            if (i == 0) {
                //add a constraint
                constraints.connect(
                        qView.getId(),
                        ConstraintSet.LEFT,
                        root.getId(),
                        ConstraintSet.LEFT,
                        20);
                root.setMinHeight(qView.getMinHeight() + margin + margin);
                //add in view-level constraints

            } else if (i > 0) {
                //int heightAdded = 8 + lastView.getMinimumHeight() + lastView.preCalculateMinimumHeight()
                //add a constraint
                constraints.connect(
                        lastView.getId(),
                        ConstraintSet.BOTTOM,
                        qView.getId(),
                        ConstraintSet.TOP);
                //        8 + lastView.getMinimumHeight() + lastView.preCalculateMinimumHeight());

                //add a constraint
//                constraints.connect(
//                        root.getId(),
//                        ConstraintSet.BOTTOM,
//                        qView.getId(),
//                        ConstraintSet.TOP,
//                        80);
//                constraints.applyTo(root);
                root.setMinHeight(root.getMinHeight() + qView.getMinHeight() + margin + margin);

            }
            lastView = qView;
            /*
            DateTimeQuestionLayout lastView = (DateTimeQuestionLayout) root.getChildAt(root.getChildCount()-1);
            Button b = new Button(this.getApplicationContext());
            b.setId(View.generateViewId());
            root.addView(b);
            constraints.connect(
                    lastView.getId(),
                    ConstraintSet.BOTTOM,
                    b.getId(),
                    ConstraintSet.TOP,
                    8);
                    */
            //root.setConstraintSet(constraints);
        }
        constraints.applyTo(root);
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
