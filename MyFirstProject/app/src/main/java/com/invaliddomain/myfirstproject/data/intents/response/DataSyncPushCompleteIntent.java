package com.invaliddomain.myfirstproject.data.intents.response;

import android.content.Context;
import android.content.Intent;

public class DataSyncPushCompleteIntent extends Intent {
    public static final String PUSH_COMPLETE_RESPONSE = "com.invaliddomain.myfirstproject.data.PUSH_COMPLETE_RESPONSE";

    public DataSyncPushCompleteIntent(Context context, Class clazz){
        super(DataSyncPushCompleteIntent.PUSH_COMPLETE_RESPONSE);
        this.setClass(context, clazz);
    }
}