package com.invaliddomain.myfirstproject;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.invaliddomain.myfirstproject.data.DataSyncService;
import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;
import com.invaliddomain.myfirstproject.data.QuestionsTemplate;
import com.invaliddomain.myfirstproject.data.listeners.PullCompleteListener;
import com.invaliddomain.myfirstproject.data.listeners.PushCompleteListener;
import com.invaliddomain.myfirstproject.questions.DateTimeQuestion;
import com.invaliddomain.myfirstproject.questions.DateTimeQuestionLayout;
import com.invaliddomain.myfirstproject.questions.base.Question;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    public static final String STARTSERVICE = "com.invaliddomain.myfirstproject.STARTSERVICE";

    private InMemoryDataRecord questions;
    private ArrayList<DateTimeQuestionLayout> dtQuestionViews;
    private DataSyncService syncService;
    private boolean syncServiceIsBound;
    private PushCompleteListener pushCompleteListener;

    /**
     * Defines callbacks for service binding, passed to bindService() and to unbind.
     */
    private ServiceConnection syncServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder serviceBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DataSyncService.DataSyncBinder binder = (DataSyncService.DataSyncBinder) serviceBinder;
            syncService = binder.getService();
            syncServiceIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            syncServiceIsBound = false;
        }
    };

    /*
     *
     * Begin event methods.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.setContentView(dtqv.getQuestionAnswerLayout());
        this.setContentView(R.layout.activity_main);

        //this.requestExternalFilesystemWritePermission();
        this.setUpService();

        this.dtQuestionViews = new ArrayList<DateTimeQuestionLayout>();
        this.setUpQuestions();
        this.addChildElements();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (syncServiceIsBound) {
            this.getApplicationContext().unbindService(syncServiceConnection);
        }
        syncServiceIsBound = false;
    }


    protected void addChildElements() {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // fill in any details dynamically here
        LinearLayout root = (LinearLayout) this.findViewById(R.id.RootLayout);
        ArrayList<Question> questions = this.questions.getQuestions();

        int margin = 10;
        DateTimeQuestionLayout lastView = null;
        for (Question q: questions) {
            if (q.getClass().getSimpleName().equals("DateTimeQuestion"))
            {
                DateTimeQuestionLayout qView = new DateTimeQuestionLayout(
                        this,
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


    private void setUpQuestions() {
        //Set a default record in case no record can be pulled.
        questions = new InMemoryDataRecord();
        syncService.addPullCompleteListener(new PullCompleteListener() {
            @Override
            public void onPullComplete(InMemoryDataRecord pulledRecord) {
                questions = pulledRecord;
            }
        });
        syncService.pull(); //This should spawn a callback with the record...

        for (Question q : questions.getQuestions()) {
            q.addListener(new Question.AnswerUpdateListener() {
                @Override
                public void onAnswerUpdated(){
                    try
                    {
                        syncService.push(questions);
                    } catch (Exception e)
                    {
                        //Eventually throw up onscreen?
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void setUpService()
    {
        // Initialize the service.
        syncService = new DataSyncService(this.getApplicationContext());
        Intent startIntent = new Intent(MainActivity.STARTSERVICE);
        startIntent.setClass(this.getApplicationContext(), DataSyncService.class);
        this.getApplicationContext().bindService(
                startIntent,
                this.syncServiceConnection,
                Context.BIND_AUTO_CREATE);
        syncServiceIsBound = true;
    }


    /**
     * This is an ugly design decision.  If writing to the local filesystem, we need write permissions that are activity-based.  Because they're activity-based, the call cannot be isolated to the filesystem writer.
     *
     */
    public void requestExternalFilesystemWritePermission(){
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        int hasWriteExternalStoragePermission =
                ContextCompat.checkSelfPermission(
                        this.getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    0
            );
        }
    }
}
