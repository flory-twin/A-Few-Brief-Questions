package com.invaliddomain.myfirstproject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.provider.SyncStateContract;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.invaliddomain.myfirstproject.data.DataSyncService;
import com.invaliddomain.myfirstproject.data.DataSyncServiceReceiver;
import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;
import com.invaliddomain.myfirstproject.data.intents.request.DataSyncPushIntent;
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

public class MainActivity extends AppCompatActivity{

    public static final String message = "This is a message.";

    private InMemoryDataRecord questionRecords;
    private ArrayList<DateTimeQuestionLayout> dtQuestionViews;
    private DataSyncService sync;
    private boolean syncServiceIsBound;
    private PushCompleteListener pushCompleteListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.setContentView(dtqv.getQuestionAnswerLayout());
        this.setContentView(R.layout.activity_main);

        this.setUpService();

        this.dtQuestionViews = new ArrayList<DateTimeQuestionLayout>();
        this.setUpQuestions();
        this.addChildElements();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (syncServiceIsBound) {
            unbindService(sync);
        }
        syncServiceIsBound = false;
    }


    protected void addChildElements() {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // fill in any details dynamically here
        LinearLayout root = (LinearLayout) this.findViewById(R.id.RootLayout);
        ArrayList<Question> questions = questionRecords.getQuestions();

        int margin = 10;
        DateTimeQuestionLayout lastView = null;
        for (Question q: questions) {
            q.addListener(new Question.AnswerUpdateListener() {
                @Override
                public void onAnswerUpdated() {
                    Intent i= new Intent(DataSyncPushIntent.PUSH, this.getApplicationContext(), DataSyncService.class);
                }
            });
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

    private void bindAndPush(DataSyncPushIntent pi) {
        if (!this.syncServiceIsBound) {
            bindService(pi, this.sync, Context.BIND_AUTO_CREATE);
        }
        this.sync.push(pi.getRecord());

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
        pushCompleteListener = new PushCompleteListener() {
            @Override
            public void onPushComplete() {
                //No action needed.
            }
        };

        /*
        IntentFilter pullCompleteFilter = new IntentFilter(
                DataSyncPullCompleteIntent.PULL_COMPLETE_RESPONSE);
        IntentFilter pullErrorFilter = new IntentFilter(
                DataSyncPullErrorIntent.PULL_ERROR_RESPONSE);
        IntentFilter pushCompleteFilter = new IntentFilter(
                DataSyncPushCompleteIntent.PUSH_COMPLETE_RESPONSE);
        IntentFilter pushErrorFilter = new IntentFilter(
                DataSyncPushErrorIntent.PUSH_ERROR_RESPONSE);

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
        */
        // Initialize the service.
        sync = new DataSyncService();
        syncServiceIsBound = false;
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder serviceBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DataSyncService.DataSyncBinder binder = (DataSyncService.DataSyncBinder) serviceBinder;
            sync = binder.getService();
            syncServiceIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            syncServiceIsBound = false;
        }
    };
}
