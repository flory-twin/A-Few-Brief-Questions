package com.invaliddomain.myfirstproject.data;

import com.invaliddomain.myfirstproject.data.DataManager.DayDate;
import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;

import java.util.ArrayList;

/**
 * 1) Date acts as key.
 * 2) At all times, list is sorted by date.
 */
public class InMemoryDataRecordList  {
    private class InMemoryDataRecordListEntry
    {
        public DayDate recordDate;
        public InMemoryDataRecord record;
        public InMemoryDataRecordListEntry(DayDate dateKey, InMemoryDataRecord dateRecord)
        {
            recordDate = dateKey;
            record = dateRecord;
        }
    }
    private ArrayList<InMemoryDataRecordListEntry> recordList;

    /*
     * --------------------------------------------------------------------------------
     * Constructors.
     * --------------------------------------------------------------------------------
     */
    public InMemoryDataRecordList()
    {
        recordList = new ArrayList<InMemoryDataRecordListEntry>(10);
    }

    public InMemoryDataRecordList(int initialCapacity)
    {
        recordList = new ArrayList<InMemoryDataRecordListEntry>(initialCapacity);
    }

    /*
     * --------------------------------------------------------------------------------
     * get()
     * --------------------------------------------------------------------------------
     */
    public InMemoryDataRecord get(DayDate dateToFetch)
    {
        InMemoryDataRecordListEntry le = getListEntry(dateToFetch);
        if (le == null)
        {
            return null;
        }
        else
        {
            return le.record;
        }
    }

    private InMemoryDataRecordListEntry getListEntry(DayDate dateToFetch)
    {
        for (InMemoryDataRecordListEntry le: recordList)
        {
            if (le.recordDate.toString().equals(dateToFetch.toString()))
            {
                return le;
            }
        }
        //else...
        return null;
    }

    /*
     * --------------------------------------------------------------------------------
     * add()
     * --------------------------------------------------------------------------------
     */
    public boolean add(InMemoryDataRecord recordToAdd)
    {
        InMemoryDataRecordListEntry newEntry = new InMemoryDataRecordListEntry(
                recordToAdd.getRecordDate(),
                recordToAdd
        );
        return add(newEntry);
    }
    private boolean add(InMemoryDataRecordListEntry entry)
    {
        //If no entry was found, add the new one in the correct position.
        if (!contains(entry.recordDate)) {
            //Use a binary search to break down the positions.
            if (recordList.size() == 0) {
                recordList.add(0, entry);
            }
            else
            {
                //Iterate forward until the next entry is larger.
                int largerEntryIndex = 0;
                for (int i = 1; i < recordList.size(); i++)
                {
                    int compareResult = entry.recordDate.compareTo(
                            recordList.get(i).recordDate);
                    if (compareResult == 1) {
                        //The next entry up is smaller.  No action.
                    } else if (compareResult == 0) {
                        //They're the same date.  Theoretically impossible due to add...but still, no action.
                    } else {
                        //The next entry up is larger.  Insert here!!
                        recordList.add(i, entry);
                        return true;
                    }
                }
            }
        }
        //else...
        return false;
    }

    /*
     * --------------------------------------------------------------------------------
     * remove()
     * --------------------------------------------------------------------------------
     */
    public boolean remove(DayDate recordDateToRemove)
    {
        if (!contains(recordDateToRemove))
        {
            return false;
        }
        else
        {
            return recordList.remove(getListEntry(recordDateToRemove));
        }
    }
    /*
     * --------------------------------------------------------------------------------
     * contains()
     * --------------------------------------------------------------------------------
     */
    public boolean contains(DayDate recordWithThisDate)
    {
        for (InMemoryDataRecordListEntry le: recordList)
        {
            if (le.recordDate.toString().equals(recordWithThisDate.toString()))
            {
                return true;
            }
        }
        //else
        return false;
    }

    /*
     * --------------------------------------------------------------------------------
     * Somewhat transparent records return...
     * --------------------------------------------------------------------------------
     */
    public ArrayList<InMemoryDataRecord> getRecords()
    {
        ArrayList<InMemoryDataRecord> underlyingRecords = new ArrayList<InMemoryDataRecord>();
        for (InMemoryDataRecordListEntry le: recordList)
        {
            underlyingRecords.add(le.record);
        }
        return underlyingRecords;
    }
    /*
     * --------------------------------------------------------------------------------
     * Currently unused.
     * --------------------------------------------------------------------------------
     */
    private InMemoryDataRecordListEntry findOldestEntry()
    {
        DayDate oldestDate = null;
        if (recordList.size() == 0)
        {
            return null;
        }
        else {
            oldestDate = recordList.get(0).recordDate;
            for (InMemoryDataRecordListEntry le : recordList) {
                int compareResult = oldestDate.compareTo(le.recordDate);
                if (compareResult == -1) {
                    //The oldest entry is still the least entry in progression; no action
                } else if (compareResult == 0) {
                    //They're the same date.  Theoretically impossible due to add...but still, no action.
                } else {
                    //The oldest entry is larger than the one we're iterating over.  That means we need to update "oldest".
                    oldestDate = le.recordDate;
                }
            }
        }
        return getListEntry(oldestDate);
    }
}
