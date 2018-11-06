package com.invaliddomain.myfirstproject.data;

//For an asynchronous implementation.
//Containing service handles client events.
public interface IDataManager {
    public void pushData(InMemoryDataRecord record);
    public InMemoryDataRecord pullData();
}
