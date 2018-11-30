package com.invaliddomain.myfirstproject.test.data.manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import android.os.Environment;

@RunWith(MockitoJUnitRunner.class)

public class LocalFilesystemDataManagerTest {

    @Test
    public void testGetCachedRecord(){}

    //No test for getAllCachedRecords()--"too simple to fail".
    //However, it may be wise to do an initialization sequence test.

    @Test
    public void testAddToOrUpdateCache(){}

    @Test
    public void testPullAllRecords(){}

    @Test
    public void testPushAllRecords(){}

    //No test for doesRecordExistInCache()--"too simple to fail".

    @Test
    public void testCheckExternalStorageReadableAndThrow(){}

    @Test
    public void testCheckExternalStorageWriteableAndThrow(){

    }
}