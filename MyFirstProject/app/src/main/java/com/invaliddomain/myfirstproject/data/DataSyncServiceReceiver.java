package com.invaliddomain.myfirstproject.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.invaliddomain.myfirstproject.data.intents.response.DataSyncPullCompleteIntent;
import com.invaliddomain.myfirstproject.data.intents.response.DataSyncPullErrorIntent;
import com.invaliddomain.myfirstproject.data.intents.response.DataSyncPushCompleteIntent;
import com.invaliddomain.myfirstproject.data.intents.response.DataSyncPushErrorIntent;
import com.invaliddomain.myfirstproject.data.listeners.PullCompleteListener;
import com.invaliddomain.myfirstproject.data.listeners.PullErrorListener;
import com.invaliddomain.myfirstproject.data.listeners.PushCompleteListener;
import com.invaliddomain.myfirstproject.data.listeners.PushErrorListener;

import java.util.ArrayList;

public class DataSyncServiceReceiver extends BroadcastReceiver {
    private ArrayList<PushCompleteListener> pushCompletionListeners;
    private ArrayList<PullCompleteListener> pullCompletionListeners;
    private ArrayList<PushErrorListener> pushErrorListeners;
    private ArrayList<PullErrorListener> pullErrorListeners;

    private InMemoryDataRecord lastRecordReceived;
    private Exception lastExceptionReceived;

    /**
     * Specifically constructed for use with a single client class.
     * @param pullCompletionListener
     * @param pullErrorListener
     * @param pushCompletionListener
     * @param pushErrorListener
     */
    public DataSyncResponseReceiver(
            PullCompleteListener pullCompletionListener,
            PullErrorListener pullErrorListener,
            PushCompleteListener pushCompletionListener,
            PushErrorListener pushErrorListener)
    {
        super();
        this.addPullCompleteListener(pullCompletionListener);
        this.addPullErrorListener(pullErrorListener);
        this.addPushCompleteListener(pushCompletionListener);
        this.addPushErrorListener(pushErrorListener);
    }

    /**
     * The important bit of this class.  Per Google documentation, "Called when the BroadcastReceiver gets an Intent it's registered to receive".
     * @param context
     * @param responseIntent
     */
    @Override
    public void onReceive(Context context, Intent responseIntent) {
        if (responseIntent.getClass().equals(DataSyncPullCompleteIntent.class))
        {
            this.notifyPullCompletionListeners(((DataSyncPullCompleteIntent) responseIntent).getRecord());
        }
        else if (responseIntent.getClass().equals(DataSyncPullErrorIntent.class))
        {
            this.notifyPullErrorListeners(((DataSyncPullErrorIntent) responseIntent).getException(););
        }
        else if (responseIntent.getClass().equals(DataSyncPushCompleteIntent.class))
        {
            this.notifyPushCompletionListeners();
        }
        else if (responseIntent.getClass().equals(DataSyncPushErrorIntent.class))
        {
            this.notifyPushErrorListeners(((DataSyncPushErrorIntent) responseIntent).getException(););
        }
    }

    /*
     * --------------------------------------------------------------------------------
     * Listener boilerplates.
     * --------------------------------------------------------------------------------
     */
    public void addPushCompleteListener(PushCompleteListener listener)
    {
        pushCompletionListeners.add(listener);
    }
    public void addPullCompleteListener(PullCompleteListener listener)
    {
        pullCompletionListeners.add(listener);
    }
    public void addPushErrorListener(PushErrorListener listener)
    {
        pushErrorListeners.add(listener);
    }
    public void addPullErrorListener(PullErrorListener listener)
    {
        pullErrorListeners.add(listener);
    }
    private void notifyPushCompletionListeners()
    {
        for (PushCompleteListener listener: pushCompletionListeners)
        {
            listener.onPushComplete();
        }
    }
    private void notifyPullCompletionListeners(InMemoryDataRecord pulledRecord)
    {
        for (PullCompleteListener listener: pullCompletionListeners)
        {
            listener.onPullComplete();
        }
    }
    private void notifyPushErrorListeners(Exception e)
    {
        for (PushErrorListener listener: pushErrorListeners)
        {
            listener.onPushError();
        }
    }
    private void notifyPullErrorListeners(Exception e)
    {
        for (PullErrorListener listener: pullErrorListeners)
        {
            listener.onPullError();
        }
    }
}
