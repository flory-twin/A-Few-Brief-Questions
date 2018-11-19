package com.invaliddomain.myfirstproject.data.intents.request;

import android.content.Context;
import android.content.Intent;

public class DataSyncPullIntent extends Intent {
    /*
     * Per https://developer.android.com/reference/android/content/Intent:
     *   The primary pieces of information in an intent are:
     *   action -- The general action to be performed, such as ACTION_VIEW, ACTION_EDIT, ACTION_MAIN, etc.
     *   data -- The data to operate on, such as a person record in the contacts database, expressed as a Uri.
     */
    public static final String PULL = "com.invaliddomain.myfirstproject.data.PULL";

    public DataSyncPullIntent(String uri, Context context, Class class){
            super(DataSyncPullIntent.PULL, uri, context, class);
    }
}