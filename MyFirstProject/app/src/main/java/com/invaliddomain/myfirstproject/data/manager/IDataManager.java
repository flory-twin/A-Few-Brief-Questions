package com.invaliddomain.myfirstproject.data.manager;

import android.support.annotation.Nullable;

import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;
import com.invaliddomain.myfirstproject.question.datetime.DayDate;

import java.util.ArrayList;

//For an asynchronous implementation.
//Containing service handles client events.
public interface IDataManager {
    //A non-semantic change.
    public void pushAllRecords() throws Exception;
    public void addToOrUpdateCache(InMemoryDataRecord recordToAddOrUpdate);
    public ArrayList<InMemoryDataRecord> getAllCachedRecords();
    @Nullable
    public InMemoryDataRecord getCachedRecord(DayDate fromThisDate) throws Exception;
    public void pullAllRecords() throws Exception;
}
