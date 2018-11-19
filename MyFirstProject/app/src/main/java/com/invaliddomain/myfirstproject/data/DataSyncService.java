package com.invaliddomain.myfirstproject.data;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.invaliddomain.myfirstproject.MainActivity;
import com.invaliddomain.myfirstproject.data.DataManager.IDataManager;
import com.invaliddomain.myfirstproject.data.DataManager.LocalFilesystemDataManager;
import com.invaliddomain.myfirstproject.data.intents.request.DataSyncPullIntent;
import com.invaliddomain.myfirstproject.data.intents.request.DataSyncPushIntent;
import com.invaliddomain.myfirstproject.data.intents.response.DataSyncPullCompleteIntent;
import com.invaliddomain.myfirstproject.data.intents.response.DataSyncPullErrorIntent;
import com.invaliddomain.myfirstproject.data.intents.response.DataSyncPushCompleteIntent;
import com.invaliddomain.myfirstproject.data.intents.response.DataSyncPushErrorIntent;

//IntentService?

//Google Drive calculated client secret:  Client Secret
//  I8RFbBTN49NUxaKP2HIIHmxb
//GD User ID:  68788569212-qtip78ignucmdq7huoso1m425rlfk4hn.apps.googleusercontent.com
public class DataSyncService extends IntentService {
    //IntentService automatically creates a separate thread for us.

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

    private IDataManager dataStore;
    private final IBinder serviceBinding = new DataSyncBinder();


    /*
     * --------------------------------------------------------------------------------
     * Constructors.
     * --------------------------------------------------------------------------------
     */
    public DataSyncService()
    {
        super("DataSyncService");
        //dataStore = new
    }

    /*
     * --------------------------------------------------------------------------------
     * Service lifecycle handlers.
     * --------------------------------------------------------------------------------
     */
    @Override
    public void onCreate()
    {
        this.dataStore = new LocalFilesystemDataManager();
    }

    //onStartCommand
    //startService
    //onDestroy

    @Override
    public IBinder onBind(Intent intent) {
            return this.serviceBinding;
    }

    public void push(InMemoryDataRecord recordToPush) throws Exception
    {
            this.dataStore.addToOrUpdateCache(recordToPush);
            this.dataStore.pushAllRecords();
    }
    //Will be called by IntentService to take action on Intents pulled from the internal work queue
    @Override
    public void onHandleIntent(Intent intent)
    {
        if (intent.getClass().equals(DataSyncPushIntent.class))
        {
            dataStore.addToOrUpdateCache(((DataSyncPushIntent) intent).getRecord());
            try {
                dataStore.pushAllRecords();
                //If we're still executing, we completed successfully.
                DataSyncPushCompleteIntent pushComplete = new DataSyncPushCompleteIntent(this.getApplicationContext(), MainActivity.class);
                this.sendResponseStatus(pushComplete);

            } catch (Exception e)
            {
                DataSyncPushErrorIntent pushError = new DataSyncPushErrorIntent(this.getApplicationContext(), MainActivity.class, e);
                this.sendResponseStatus(pushError);
            }
        }
        else if (intent.getClass().equals(DataSyncPullIntent.class))
        {
            try {
                dataStore.pullAllRecords(); //Have to pull complete file at once.
                InMemoryDataRecord pulledRecord = dataStore.getCachedRecord(((DataSyncPullIntent) intent).getWhenToPullFrom());
                //If we're still executing, we completed successfully.
                DataSyncPullCompleteIntent pullComplete = new DataSyncPullCompleteIntent(this.getApplicationContext(), MainActivity.class, pulledRecord);
                this.sendResponseStatus(pullComplete);

            } catch (Exception e)
            {
                DataSyncPullErrorIntent pullError = new DataSyncPullErrorIntent(this.getApplicationContext(), MainActivity.class, e);
                this.sendResponseStatus(pullError);
            }

        }
        //NO other actions possible.
    }

    //StopService handled by IntentService

    public void sendResponseStatus (Intent intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


}