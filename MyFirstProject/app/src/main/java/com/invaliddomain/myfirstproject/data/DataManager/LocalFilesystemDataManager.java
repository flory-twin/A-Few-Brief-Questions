package com.invaliddomain.myfirstproject.data.DataManager;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;
import com.invaliddomain.myfirstproject.data.QuestionsTemplate;
import com.invaliddomain.myfirstproject.questions.DateTimeQuestion;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LocalFilesystemDataManager implements IDataManager {
    private static String filename = "RedLightData.csv";
    //The complete Java.nio is not included in v.25--just 26 and up.
    // So, in with the old, out with the new!
    private File dataFile;
    private HashMap<DayDate, InMemoryDataRecord> records;

    public LocalFilesystemDataManager()
    {
        records = new HashMap<Date, InMemoryDataRecord>();
        dataFile = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), filename);
        this.deserialize("2018-11-15 20:52:00");
        int d = 0;
    }

    @Override
    public InMemoryDataRecord getCachedRecord(DayDate fromThisDate) throws Exception{
        if (records.containsKey(fromThisDate))
        {
            return records.get(fromThisDate);
        }
        else
        {
            throw new Exception("The record for " + );
        }
    }
    //public void pushAllRecords(InMemoryDataRecord record);
    //public
    //
    @Override
    public ArrayList<InMemoryDataRecord> getAllCachedRecords()
    {
        ArrayList<InMemoryDataRecord> cachedRecords = new ArrayList<InMemoryDataRecord>();
        for (Object key: this.records.keySet())
        {
            cachedRecords.add(records.get(key));
        }
        return cachedRecords;
    }

    @Override
    public void addToOrUpdateCache(InMemoryDataRecord recordToAddOrUpdate)
    {
        //If no record already exists for this date, add it.
        if (!this.records.containsKey(recordToAddOrUpdate.get))
        //Otherwise, update.
    }

    @Override
    public void pullAllRecords() throws Exception
    {
        this.checkFileWritableAndThrow();
        //If permissions check out, then pull the file contents, deserialize, and write to memory.
        //FileReader, FileWriter
        BufferedReader br = new BufferedReader(new FileReader(dataFile));
        br.readLine();

    }

    @Override
    public void pushAllRecords() throws Exception{
        //Create CSV line to print
        //If it does not already exist, create the file to which to write.
        this.getPublicFile();
        //Write the data:
        //First, see if there's already a record date-stamped for today.
        //If so, write to that column.  If not, create a column.
    }

    private InMemoryDataRecord deserialize(String record)
    {
        String[] fields = record.split(",");
        //Assume that the data from the record is in the order given by the standard question template.
        //Assume that this is not a header row.
        //Set up the record using the first timed question known to contain today's date.
        //temporary override
        InMemoryDataRecord testRecord = new InMemoryDataRecord();
        //Testing.  Will this change be persistent?
        try {
            Thread.sleep(5000);
        } catch (Exception e)
        {

        }
        ((DateTimeQuestion) testRecord.getQuestions().get(0)).setAnswerToNow();
        int c = 0;
        return testRecord;
    }
    private Boolean doesRecordExistInCache(Date recordDate)
    {
        return false;
    }

    private void checkFileAndThrow() throws Exception, FileNotFoundException
    {
        if (!isExternalStorageWritable())
        {
            throw new Exception(
                    "The shared drive area " +
                            Environment.DIRECTORY_DOCUMENTS +
                            " is not available.");
        }
        else if (!dataFile.exists())
        {
            //Try and create the file first.  If that fails, throw an exception.
            try {
                dataFile.createNewFile();
            }
            catch (Exception e) {
                throw new java.io.FileNotFoundException(
                        "File " +
                                dataFile.getCanonicalPath() +
                                " does not exist and could not be created.  Creation exception was " +
                                e.getMessage());
            }
        }
        else if (!dataFile.isFile())
        {
            throw new Exception(
                    dataFile.getCanonicalPath() +
                            " is not a file.");
        }
    }

    private void checkFileWritableAndThrow() throws Exception, FileNotFoundException
    {
        checkFileAndThrow();
        if (!dataFile.canWrite())
        {
            throw new Exception(
                    "File " +
                            dataFile.getCanonicalPath() +
                            " cannot be written due to permissions issues.");
        }
    }

    private void writeNewRecord()
    {

    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void getPublicFile() {
    }
}