package com.invaliddomain.myfirstproject.data.intents.response;

import android.content.Context;
import android.content.Intent;

public class DataSyncPushErrorIntent extends Intent {
    public static final String PUSH_ERROR_RESPONSE = "com.invaliddomain.myfirstproject.data.PUSH_ERROR_RESPONSE";

    private Exception returnedException;

    public DataSyncPushErrorIntent(Context context, Class clazz, Exception e){
        super(DataSyncPushErrorIntent.PUSH_ERROR_RESPONSE);
        this.setClass(context, clazz);
        this.returnedException = e;
    }

    public Exception getException()
    {
        return returnedException;
    }
}