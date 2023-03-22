package com.gabys.ps_tema1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

import android.database.sqlite.SQLiteDatabase;

import com.gabys.ps_tema1.Model.DB.DatabaseHelper;
import com.gabys.ps_tema1.Model.Persistence.UserPersistence;
import com.gabys.ps_tema1.Model.User;

import java.util.ArrayList;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TestUserPersistence {
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
    public void TestAddUser() {

        // db
        db = databaseHelper.getWritableDatabase();

        // Arrange
        UserPersistence UserPersistence = new UserPersistence(activity);
        UserPersistence.deleteTable();
        UserPersistence.createUserTable();

        User User = new User("john", "pass", 1);
        // Act
        long rowId = UserPersistence.addUser(User);

        // Assert
        assertNotEquals(-1, rowId);

        db.close();
    }

    @Test
    public void TestUpdateUser() {

        // db
        db = databaseHelper.getWritableDatabase();

        // Arrange
        UserPersistence userPersistence = new UserPersistence(activity);
        userPersistence.deleteTable();
        userPersistence.createUserTable();

        User user = new User("john", "1234", 1);
        // Act
        long rowId = userPersistence.addUser(user);
        user.setId((int) rowId);

        user.setUsername("user 2");
        user.setPassword("pass 2");
        userPersistence.updateUser(user);

        User user2 = userPersistence.getUser(user.getId());

        assertEquals(user, user2);

        db.close();
    }

    @Test
    public void TestDeleteUser() {

        // db
        db = databaseHelper.getWritableDatabase();

        // Arrange
        UserPersistence userPersistence = new UserPersistence(activity);
        userPersistence.deleteTable();
        userPersistence.createUserTable();

        User user = new User("john", "1234", 1);
        // Act
        long rowId = userPersistence.addUser(user);
        user.setId((int) rowId);

        int deletedRows = userPersistence.deleteUser(user.getId());
        int deletedRows2 = userPersistence.deleteUser(3);

        assertEquals(1, deletedRows);
        assertNotEquals(1, deletedRows2);

        db.close();
    }

    @Test
    public void TestListProperties() {

        // db
        db = databaseHelper.getWritableDatabase();

        // Arrange
        UserPersistence UserPersistence = new UserPersistence(activity);
        UserPersistence.deleteTable();
        UserPersistence.createUserTable();

        // Arrange
        User User1 = new User("john", "pass", 1);
        User User2 = new User("mary", "1234", 2);
        User User3 = new User("nigel", "abcd", 1);

        UserPersistence.addUser(User1);
        UserPersistence.addUser(User2);
        UserPersistence.addUser(User3);

        // Act
        ArrayList<User> results = UserPersistence.getAuthUsers();

        // Assert
        assertFalse(results.isEmpty());
        assertEquals(3, results.size());
        assertEquals(User1, results.get(0));
        assertEquals(User2, results.get(1));
        assertEquals(User3, results.get(2));


        db.close();
    }
}
