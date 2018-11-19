package com.invaliddomain.myfirstproject.data.intents.response;

import android.content.Context;
import android.content.Intent;

import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;

public class DataSyncPullCompleteIntent extends Intent {
    public static final String PULL_COMPLETE_RESPONSE = "com.invaliddomain.myfirstproject.data.PULL_COMPLETE_RESPONSE";

    private InMemoryDataRecord returnedRecord;

    public DataSyncPullCompleteIntent(String uri, Context context, Class class, InMemoryDataRecord pulledRecord){
        super(DataSyncPullCompleteIntent.PUSH, uri, context, class);
        this.returnedRecord = pulledRecord;
    }

    public InMemoryDataRecord getRecord()
    {
        return returnedRecord;
    }
}