package com.invaliddomain.myfirstproject.data.intents.request;

import android.content.Context;
import android.content.Intent;

import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;

public class DataSyncPushIntent extends Intent {
    public static final String PUSH = "com.invaliddomain.myfirstproject.data.PUSH";

    private InMemoryDataRecord recordToPush;

    public DataSyncPushIntent(String uri, Context context, Class class, InMemoryDataRecord recordToPush){
        super(DataSyncPushIntent.PUSH, uri, context, class);
        this.recordToPush = recordToPush;
    }

    public InMemoryDataRecord getRecord()
    {
        return recordToPush;
    }
}