package com.invaliddomain.myfirstproject.data.intents.response;

import android.content.Context;
import android.content.Intent;

import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;

public class DataSyncPullCompleteIntent extends Intent {
    public static final String PULL_COMPLETE_RESPONSE = "com.invaliddomain.myfirstproject.data.PULL_COMPLETE_RESPONSE";

    private InMemoryDataRecord returnedRecord;

    public DataSyncPullCompleteIntent(Context context, Class clazz, InMemoryDataRecord pulledRecord){
        super(DataSyncPullCompleteIntent.PULL_COMPLETE_RESPONSE);
        this.setClass(context, clazz);
        this.returnedRecord = pulledRecord;
    }

    public InMemoryDataRecord getRecord()
    {
        return returnedRecord;
    }
}