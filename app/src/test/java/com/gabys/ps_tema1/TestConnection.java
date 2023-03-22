package com.gabys.ps_tema1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gabys.ps_tema1.Model.DB.DatabaseHelper;
import com.gabys.ps_tema1.Model.Persistence.PropertyPersistence;
import com.gabys.ps_tema1.Model.Persistence.UserPersistence;

import java.io.File;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TestConnection {

    private TestActivity activity;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    @Before
    public void setUp() throws Exception
    {
        activity = Robolectric.buildActivity(TestActivity.class).setup().get();
        databaseHelper = new DatabaseHelper(activity);
    }

    @Test
    public void TestOpenConnection() {

        // db
        db = databaseHelper.getWritableDatabase();

        assertNotNull(db);

        db.close();
    }

    @Test
    public void TestCloseConnection() {

        // db
        db = databaseHelper.getWritableDatabase();
        db.close();

        assertFalse(db.isOpen());
    }
}
