package com.gabys.ps_tema1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gabys.ps_tema1.Model.DB.DatabaseHelper;
import com.gabys.ps_tema1.Model.Persistence.PropertyPersistence;
import com.gabys.ps_tema1.Model.Persistence.UserPersistence;

import java.io.File;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TestDatabaseCreation {

    private TestActivity activity;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    @Before
    public void setUp() throws Exception
    {
        activity = Robolectric.buildActivity(TestActivity.class).setup().get();

        databaseHelper = new DatabaseHelper(activity);
        db = databaseHelper.getWritableDatabase();
    }

    @Test
    public void TestDBCreation()
    {

        // Arrange
        //SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.gabys.ps_tema1.test/databases/agency.db", null);

        // Assert
        assertFalse(doesTableExist(db, "Properties"));
        assertFalse(doesTableExist(db, "Users"));

        // Act
        UserPersistence userPersistence = new UserPersistence(activity);
        PropertyPersistence propertyPersistence = new PropertyPersistence(activity);

        userPersistence.createUserTable();
        propertyPersistence.createPropertyTable();

        // Assert
        assertTrue(doesTableExist(db, "Properties"));
        assertTrue(doesTableExist(db, "Users"));

        //SQLiteDatabase.deleteDatabase(new File("/data/data/com.gabys.ps_tema1.test/databases/agency.db"));

        db.close();
    }

    public boolean doesTableExist(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}
