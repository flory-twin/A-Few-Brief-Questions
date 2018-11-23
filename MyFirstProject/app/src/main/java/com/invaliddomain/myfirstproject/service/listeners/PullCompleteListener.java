package com.invaliddomain.myfirstproject.service.listeners;

import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;

public interface PullCompleteListener {
    public void onPullComplete(InMemoryDataRecord pulledRecord);
}
