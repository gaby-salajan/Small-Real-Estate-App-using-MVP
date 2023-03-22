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
import com.gabys.ps_tema1.Model.Persistence.PropertyPersistence;
import com.gabys.ps_tema1.Model.Property;

import java.util.ArrayList;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TestPropertiesPersistence {
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
    public void TestAddProperty() {

        // db
        db = databaseHelper.getWritableDatabase();

        // Arrange
        PropertyPersistence propertyPersistence = new PropertyPersistence(activity);
        propertyPersistence.deleteTable();
        propertyPersistence.createPropertyTable();

        Property property = new Property("titlu anunt", "locatie", 1, "tip", 100f, true);
        // Act
        long rowId = propertyPersistence.addProperty(property);

        // Assert
        assertNotEquals(-1, rowId);

        db.close();
    }

    @Test
    public void TestUpdateProperty() {

        // db
        db = databaseHelper.getWritableDatabase();

        // Arrange
        PropertyPersistence propertyPersistence = new PropertyPersistence(activity);
        propertyPersistence.deleteTable();
        propertyPersistence.createPropertyTable();

        Property property = new Property("titlu anunt", "locatie", 1, "tip", 100f, true);
        // Act
        long rowId = propertyPersistence.addProperty(property);
        property.setId((int) rowId);

        property.setTitle("titlu 2");
        property.setLocation("locatie 2");
        propertyPersistence.updateProperty(property);

        Property property2 = propertyPersistence.getProperty(property.getId());

        assertEquals(property, property2);

        db.close();
    }

    @Test
    public void TestDeleteProperty() {

        // db
        db = databaseHelper.getWritableDatabase();

        // Arrange
        PropertyPersistence propertyPersistence = new PropertyPersistence(activity);
        propertyPersistence.deleteTable();
        propertyPersistence.createPropertyTable();

        Property property = new Property("titlu anunt", "locatie", 1, "tip", 100f, true);
        // Act
        long rowId = propertyPersistence.addProperty(property);
        property.setId((int) rowId);

        int deletedRows = propertyPersistence.deleteProperty(property.getId());
        int deletedRows2 = propertyPersistence.deleteProperty(3);

        assertEquals(1, deletedRows);
        assertNotEquals(1, deletedRows2);

        db.close();
    }

    @Test
    public void TestListProperties() {

        // db
        db = databaseHelper.getWritableDatabase();

        // Arrange
        PropertyPersistence propertyPersistence = new PropertyPersistence(activity);
        propertyPersistence.deleteTable();
        propertyPersistence.createPropertyTable();

        // Arrange
        Property property1 = new Property("Apartament cu 2 camere", "Cluj",2,"Apartament",400f, true);
        Property property2 = new Property("Casa cu etaj","Baciu",7,"Casa/Vila",1200f,true);
        Property property3 = new Property("Apartament cu 3 camere","Floresti",3,"Apartament",600f,true);

        propertyPersistence.addProperty(property1);
        propertyPersistence.addProperty(property2);
        propertyPersistence.addProperty(property3);

        // Act
        ArrayList<Property> results = propertyPersistence.getProperties();

        // Assert
        assertFalse(results.isEmpty());
        assertEquals(3, results.size());
        assertEquals(property1, results.get(0));
        assertEquals(property2, results.get(1));
        assertEquals(property3, results.get(2));


        db.close();
    }
}
