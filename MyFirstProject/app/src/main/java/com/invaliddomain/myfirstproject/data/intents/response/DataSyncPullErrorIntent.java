package com.invaliddomain.myfirstproject.data.intents.response;

import android.content.Context;
import android.content.Intent;

public class DataSyncPullErrorIntent extends Intent {
    public static final String PULL_ERROR_RESPONSE = "com.invaliddomain.myfirstproject.data.PULL_ERROR_RESPONSE";

    private Exception returnedException;

    public DataSyncPullErrorIntent(Context context, Class clazz, Exception e){
        super(DataSyncPullErrorIntent.PULL_ERROR_RESPONSE);
        this.setClass(context, clazz);
        this.returnedException = e;
    }

    public Exception getException()
    {
        return returnedException;
    }
}