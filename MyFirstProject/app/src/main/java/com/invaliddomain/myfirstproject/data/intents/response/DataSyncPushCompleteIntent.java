package com.invaliddomain.myfirstproject.data.intents.response;

import android.content.Context;
import android.content.Intent;

import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;

public class DataSyncPushCompleteIntent extends Intent {
    public static final String PUSH_COMPLETE_RESPONSE = "com.invaliddomain.myfirstproject.data.PUSH_COMPLETE_RESPONSE";

    public DataSyncPushCompleteIntent(String uri, Context context, Class class){
        super(DataSyncPushCompleteIntent.PUSH, uri, context, class);
    }
}