package com.invaliddomain.myfirstproject.data.intents.response;

import android.content.Context;
import android.content.Intent;

public class DataSyncPushErrorIntent extends Intent {
    public static final String PUSH_ERROR_RESPONSE = "com.invaliddomain.myfirstproject.data.PUSH_ERROR_RESPONSE";

    private Exception returnedException;

    public DataSyncPushErrorIntent(String uri, Context context, Class class, Exception e){
        super(DataSyncPushErrorIntent.PUSH_ERROR_RESPONSE, uri, context, class);
        this.returnedException = e;
    }

    public Exception getException()
    {
        return returnedException;
    }
}