package com.invaliddomain.myfirstproject.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.invaliddomain.myfirstproject.question.datetime.DayDate;
import com.invaliddomain.myfirstproject.data.manager.IDataManager;
import com.invaliddomain.myfirstproject.data.manager.LocalFilesystemDataManager;
import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;
import com.invaliddomain.myfirstproject.service.listeners.PullCompleteListener;
import com.invaliddomain.myfirstproject.service.listeners.PullErrorListener;
import com.invaliddomain.myfirstproject.service.listeners.PushCompleteListener;
import com.invaliddomain.myfirstproject.service.listeners.PushErrorListener;

import java.util.ArrayList;
import java.util.Date;

//IntentService?

//Google Drive calculated client secret:  Client Secret
//  I8RFbBTN49NUxaKP2HIIHmxb
//GD User ID:  68788569212-qtip78ignucmdq7huoso1m425rlfk4hn.apps.googleusercontent.com
public class DataSyncService extends IntentService {
    //IntentService automatically creates a separate thread for us.

    private ArrayList<PushCompleteListener> pushCompletionListeners;
    private ArrayList<PullCompleteListener> pullCompletionListeners;
    private ArrayList<PushErrorListener> pushErrorListeners;
    private ArrayList<PullErrorListener> pullErrorListeners;
    private IDataManager dataStore;
    private final IBinder serviceBinding = new DataSyncBinder();
    /**
     * Sample code from the Android notes on Service.
     *
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class DataSyncBinder extends Binder {
        public DataSyncService getService() {
            return DataSyncService.this;
        }
    }




    /*
     * --------------------------------------------------------------------------------
     * Constructors.
     * --------------------------------------------------------------------------------
     */
    public DataSyncService(Context context)
    {
        super("DataSyncService");
        this.dataStore = new LocalFilesystemDataManager();

        this.pushCompletionListeners = new ArrayList<PushCompleteListener>();
        this.pullCompletionListeners = new ArrayList<PullCompleteListener>();
        this.pushErrorListeners = new ArrayList<PushErrorListener>();
        this.pullErrorListeners = new ArrayList<PullErrorListener>();
    }

    /*
     * --------------------------------------------------------------------------------
     * Service lifecycle handlers.
     * --------------------------------------------------------------------------------
     */

    //onStartCommand
    //startService

    @Override
    public IBinder onBind(Intent intent) {
        return this.serviceBinding;
    }

    public void push(InMemoryDataRecord recordToPush)
    {
        try {
            this.dataStore.addToOrUpdateCache(recordToPush);
            this.dataStore.pushAllRecords();
            this.notifyPushCompletionListeners();
        } catch (Exception e)
        {
            this.notifyPushErrorListeners(e);
        }
    }

    /**
     * Attempts to find a record for today's date.  If found, this record is used to populate the local cache.
     * @return Either the cached record for today's date, or a new record.
     */
    @Nullable
    public void pull()
    {
        try
        {
            this.dataStore.pullAllRecords();
            InMemoryDataRecord cachedRecord = dataStore.getCachedRecord(new DayDate(new Date()));
            if (cachedRecord != null)
            {
                this.notifyPullCompletionListeners(cachedRecord);
            }
            else {
                this.notifyPullCompletionListeners(
                        dataStore.getCachedRecord(
                                new DayDate(
                                        new Date())));
            }
        }
        catch (Exception e)
        {
            this.notifyPullErrorListeners(e);
        }
    }

    //Will be called by IntentService to take action on Intents pulled from the internal work queue
    @Override
    public void onHandleIntent(Intent intent)
    {
        //Effect not important--most of the work is done via callbacks to the service object.
    }

    //StopService handled by IntentService

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
            listener.onPullComplete(pulledRecord);
        }
    }
    private void notifyPushErrorListeners(Exception e)
    {
        for (PushErrorListener listener: pushErrorListeners)
        {
            listener.onPushError(e);
        }
    }
    private void notifyPullErrorListeners(Exception e)
    {
        for (PullErrorListener listener: pullErrorListeners)
        {
            listener.onPullError(e);
        }
    }
}