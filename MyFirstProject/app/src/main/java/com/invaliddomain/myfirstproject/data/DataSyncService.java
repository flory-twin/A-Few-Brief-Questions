package com.invaliddomain.myfirstproject.data;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.invaliddomain.myfirstproject.data.DataManager.IDataManager;
import com.invaliddomain.myfirstproject.data.DataManager.LocalFilesystemDataManager;
import com.invaliddomain.myfirstproject.data.intents.request.DataSyncPullIntent;
import com.invaliddomain.myfirstproject.data.intents.request.DataSyncPushIntent;

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
        DataSyncService getService() {
            return DataSyncService.this;
        }
    }

    private IDataManager dataStore;
    private final IBinder serviceBinding;


    /*
     * --------------------------------------------------------------------------------
     * Constructors.
     * --------------------------------------------------------------------------------
     */
    public DataSyncService()
    {
        super("DataSyncService");
        //dataStore = new
        this.serviceBinding = null;
    }

    /*
     * --------------------------------------------------------------------------------
     * Service lifecycle handlers.
     * --------------------------------------------------------------------------------
     */
    @Override
    public void onCreate()
    {
        this.serviceBinding = new DataSyncBinder();
        this.dataStore = new LocalFilesystemDataManager();
    }

    //onStartCommand
    //startService
    //onDestroy

    @Override
    public IBinder onBind(Intent intent) {
            return this.serviceBinding;
    }

    //Will be called by IntentService to take action on Intents pulled from the internal work queue
    @Override
    public void onHandleIntent(Intent intent)
    {
        if (intent.getClass().equals(DataSyncPushIntent.class))
        {
            dataStore
            dataStore.pushAllRecords();
        }
        else if (intent.getClass().equals(DataSyncPullIntent.class))
        {}
        //NO other actions possible.

        //send on "Send"; pull from Drive every time entered; "Refresh" button
        //Check first on local file.
        //CSV format
        //updateRecord()
        // -Pull data from fields, send over
        // -No such thing as required; implement with interface/
        // -Data record layout in CSV?
        //
        //pullRecord()
        //pushRecord()
        //Needs local copy in case offline.

        //interruptedException
    }

    //StopService handled by IntentService

    //When done with a pull, use this code to return a response.
    /**
     * LocalBroadcastManager.getInstance(this).sendBroadcast(new DataSyncPullCompleteIntent(...));
     */


}