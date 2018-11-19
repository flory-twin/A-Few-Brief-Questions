package com.invaliddomain.myfirstproject.data.DataManager;

import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;

import java.util.ArrayList;
import java.util.Date;

//For an asynchronous implementation.
//Containing service handles client events.
public interface IDataManager {
    //A non-semantic change.
    public void pushAllRecords() throws Exception;
    public void addToOrUpdateCache(InMemoryDataRecord recordToAddOrUpdate);
    public ArrayList<InMemoryDataRecord> getAllCachedRecords();
    public InMemoryDataRecord getCachedRecord(DayDate fromThisDate) throws Exception;
    public void pullAllRecords() throws Exception;
}
