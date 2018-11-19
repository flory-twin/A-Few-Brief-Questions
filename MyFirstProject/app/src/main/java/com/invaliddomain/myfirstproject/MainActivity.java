package com.invaliddomain.myfirstproject;

import android.content.Context;
import android.content.IntentFilter;
import android.provider.SyncStateContract;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.invaliddomain.myfirstproject.data.DataSyncServiceReceiver;
import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;
import com.invaliddomain.myfirstproject.data.intents.response.DataSyncPullCompleteIntent;
import com.invaliddomain.myfirstproject.data.intents.response.DataSyncPullErrorIntent;
import com.invaliddomain.myfirstproject.data.intents.response.DataSyncPushCompleteIntent;
import com.invaliddomain.myfirstproject.data.intents.response.DataSyncPushErrorIntent;
import com.invaliddomain.myfirstproject.data.listeners.PullCompleteListener;
import com.invaliddomain.myfirstproject.data.listeners.PullErrorListener;
import com.invaliddomain.myfirstproject.data.listeners.PushCompleteListener;
import com.invaliddomain.myfirstproject.data.listeners.PushErrorListener;
import com.invaliddomain.myfirstproject.questions.DateTimeQuestion;
import com.invaliddomain.myfirstproject.questions.DateTimeQuestionLayout;
import com.invaliddomain.myfirstproject.questions.base.Question;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PullCompleteListener, PushCompleteListener, PullErrorListener, PushErrorListener {

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
        ArrayList<Question> questions = questionRecords.getQuestions();

        int margin = 10;
        DateTimeQuestionLayout lastView = null;
        for (Question q: questions) {
            if (q.getClass().getSimpleName().equals("DateTimeQuestion"))
            {
                DateTimeQuestionLayout qView = new DateTimeQuestionLayout(
                        this.getApplicationContext(),
                        (DateTimeQuestion) q);
                this.dtQuestionViews.add(qView);
                root.addView(qView, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
        root.invalidate();
    }

            /*
    // use this to start and trigger a service
Intent i= new Intent(DataSyncIntent.PULL, "", this.getApplicationContext(), DataSyncService.class);
// potentially add data to the intent
i.putExtra("KEY1", "Value to be used by the service");
context.startService(i);
// Starts the JobIntentService
private static final int RSS_JOB_ID = 1000;
RSSPullService.enqueueWork(getContext(), RSSPullService.class, RSS_JOB_ID, mServiceIntent);

     */


        //Use Intent to communicate start and destroy.


    private void setUpQuestions()
    {
        questionRecords = new InMemoryDataRecord();

    }

    private void setUpService()
    {
        IntentFilter pullCompleteFilter = new IntentFilter((
                DataSyncPullCompleteIntent.PULL_COMPLETE_RESPONSE);
        IntentFilter pullErrorFilter = new IntentFilter((
                DataSyncPullErrorIntent.PULL_ERROR_RESPONSE);
        IntentFilter pushCompleteFilter = new IntentFilter((
                DataSyncPushCompleteIntent.PUSH_COMPLETE_RESPONSE);
        IntentFilter pushErrorFilter = new IntentFilter((
                DataSyncPushErrorIntent.PUSH_ERROR_RESPONSE);
        r
        DataSyncServiceReceiver receiver =
                new DataSyncServiceReceiver(
                        new PullCompleteListener() {
                            @Override
                            public void onPullComplete(InMemoryDataRecord pulledRecord) {

                            }
                        },
                        new PullErrorListener() {
                            @Override
                            public void onPullError(Exception e) {
                                return null;
                            }
                        },
                        new PushCompleteListener() {
                            @Override
                            public void onPushComplete() {

                            }
                        },
                        new PushErrorListener() {
                            @Override
                            public Exception onPushError(Exception e) {
                                return null;
                            }
                        }
                );

        // Registers the receiver and its intent filters
        LocalBroadcastManager broadcastReceiver = LocalBroadcastManager.getInstance(this);
        broadcastReceiver.registerReceiver(
                receiver,
                pullCompleteFilter);
        broadcastReceiver.registerReceiver(
                receiver,
                pullErrorFilter);
        broadcastReceiver.registerReceiver(
                receiver,
                pushCompleteFilter);
        broadcastReceiver.registerReceiver(
                receiver,
                pushErrorFilter);
    }


}
