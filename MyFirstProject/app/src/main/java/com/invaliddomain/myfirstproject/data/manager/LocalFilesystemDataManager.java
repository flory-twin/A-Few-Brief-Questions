package com.invaliddomain.myfirstproject.data.manager;

import android.app.Activity;
import android.os.Environment;
import com.invaliddomain.myfirstproject.data.InMemoryDataRecordList;
import com.invaliddomain.myfirstproject.data.InMemoryDataRecord;
import com.invaliddomain.myfirstproject.question.datetime.DayDate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class LocalFilesystemDataManager implements IDataManager {
    private static String filename;
    // /storage/emulated/0/Documents in emulated device.
    private static File directory;
    static {
        filename = "RedLightData.csv";
        directory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
    }
    //The complete Java.nio is not included in v.25--just 26 and up.
    // So, in with the old, out with the new!
    private File dataFile;
    private InMemoryDataRecordList records;
    //Necessary for checking our write permissions.
    private Activity activity;

    public LocalFilesystemDataManager()
    {
        records = new InMemoryDataRecordList();
        dataFile = new File(directory, filename);
    }


    //send on "Send"; pull from Drive every time entered; "Refresh" button
    //Check first on local file.
    //CSV format
    //updateRecord()
    // -Pull data from fields, send over
    // -No such thing as required; implement with interface/
    // -Data record layout in CSV?
    //
    //pullRecord()
    //pushRecord()
    //Needs local copy in case offline.

    //interruptedException

    @Override
    public InMemoryDataRecord getCachedRecord(DayDate fromThisDate) throws Exception{
        if (records.contains(fromThisDate))
        {
            return records.get(fromThisDate);
        }
        else
        {
            throw new Exception("The record for " + fromThisDate.toString() + " is not in the cache.");
        }
    }
    //public void pushAllRecords(InMemoryDataRecord record);
    //public
    //
    @Override
    public ArrayList<InMemoryDataRecord> getAllCachedRecords()
    {
        return records.getRecords();
    }

    @Override
    public void addToOrUpdateCache(InMemoryDataRecord recordToAddOrUpdate)
    {
        //If addition fails because a record is already present...
        if (!records.add(recordToAddOrUpdate))
        {}
        //...update.
        else
        {
            records.remove(recordToAddOrUpdate.getRecordDate());
            records.add(recordToAddOrUpdate);
        }
    }

    @Override
    public void pullAllRecords() throws Exception
    {
        checkFileReadableAndThrow();
        //If permissions check out, then pull the file contents, deserialize, and write to memory.
        //FileReader, FileWriter
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(dataFile));
        while ((line = br.readLine()) != null)
        {
            records.add(new InMemoryDataRecord(line));
        }


        //...........................

    }

    @Override
    public void pushAllRecords() throws Exception{
        ArrayList<String> allRecordsAsCsv = new ArrayList<String>();

        //If it does not already exist, create the file to which to write.  Then prepare to write.
        prepareFileForWritingOrThrow();
        BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile));

        for (InMemoryDataRecord dr: this.records.getRecords()) {
            bw.write(dr.serialize());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    /*
     * --------------------------------------------------------------------------------
     * String methods.
     * --------------------------------------------------------------------------------
     */

    private Boolean doesRecordExistInCache(DayDate recordDate)
    {
        return records.contains(recordDate);
    }

    /*
     * --------------------------------------------------------------------------------
     * Granular error conditions.
     * --------------------------------------------------------------------------------
     */

    private boolean checkExternalStorageReadableAndThrow() throws Exception{
        String state = Environment.getExternalStorageState();
        if (!(Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))) {
            throw new Exception(
                    "The drive media could not be mounted normally or as read-only.");
        }
        //else
        return true;
    }

    private boolean checkExternalStorageWritableAndThrow() throws Exception{
        String state = Environment.getExternalStorageState();
        if (!(Environment.MEDIA_MOUNTED.equals(state))) {
            throw new Exception(
                    "The drive media could not be mounted.");
        }
        //else
        return true;
    }

    private boolean checkDirectoryExistsAndThrow() throws Exception
    {
        if (! directory.exists()) {
            throw new Exception(
                    "Directory " +
                    directory.getCanonicalPath() +
                    " does not exist.");
        }
        //else
        return true;
    }
    private boolean checkFileExistsAndThrow() throws Exception{
        if (!dataFile.exists())
        {
            throw new Exception(
                    "File " +
                    dataFile.getCanonicalPath() +
                    " does not exist.");
        }
        //else
        return true;
    }


    private boolean checkFileReadableAndThrow() throws Exception
    {
        if (!dataFile.canRead())
        {
            throw new Exception(
                    "File " +
                            dataFile.getCanonicalPath() +
                            " cannot be read due to permissions issues.");
        }
        //else
        return true;
    }

    private boolean checkFileWritableAndThrow() throws Exception
    {
        if (!dataFile.canWrite())
        {
            throw new Exception(
                    "File " +
                            dataFile.getCanonicalPath() +
                            " cannot be written due to permissions issues.");
        }
        //else
        return true;
    }

    /*
     * --------------------------------------------------------------------------------
     * Methods built on error conditions.
     * --------------------------------------------------------------------------------
     */
    private boolean prepareFileForWritingOrThrow() throws Exception
    {
        if (checkExternalStorageReadableAndThrow() &&
                checkExternalStorageWritableAndThrow())
        {
            try{
                checkDirectoryExistsAndThrow();
            }
            catch (Exception e)
            {
                directory.mkdirs();
            }

            try{
                checkFileExistsAndThrow();
            }
            catch (Exception e)
            {
                dataFile.createNewFile();
            }

            try
            {
                checkFileWritableAndThrow();
            }
            catch (Exception e)
            {
                dataFile.setWritable(true);
            }
        }

        //If we're here, there are no remaining errors.
        return true;
    }
}