package com.invaliddomain.myfirstproject.data.intents.response;

import android.content.Context;
import android.content.Intent;

import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;

public class DataSyncPullErrorIntent extends Intent {
    public static final String PULL_ERROR_RESPONSE = "com.invaliddomain.myfirstproject.data.PULL_ERROR_RESPONSE";

    private Exception returnedException;

    public DataSyncPullErrorIntent(String uri, Context context, Class class, Exception e){
        super(DataSyncPullErrorIntent.PULL_ERROR_RESPONSE, uri, context, class);
        this.returnedException = e;
    }

    public Exception getException()
    {
        return returnedException;
    }
}